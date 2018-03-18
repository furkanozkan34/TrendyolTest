package trendyol.services.categoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trendyol.base.ServiceException;
import trendyol.documents.Category;
import trendyol.enums.Exception;
import trendyol.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category requestCategory) {
        log.debug("Saving Category id : " + requestCategory.getId());
        Optional<Category> optionalCategory = categoryRepository.findById(requestCategory.getId());
        optionalCategory.ifPresent(u -> {
            throw new ServiceException(Exception.CATEGORY_ALLREADY_EXIST.getType());
        });
        Category category = new Category();
        category.setId(requestCategory.getId());
        category.setName(requestCategory.getName());
        log.debug("Category created");
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void checkCategoryExist(Long categoryId) {
        log.debug("Checking category : " +  categoryId);
        if (categoryId == null)
            throw new ServiceException(Exception.CATEGORY_ID_CANNOT_BE_NULL.getType());
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ServiceException(Exception.CATEGORY_NOT_FOUND.getType()));
    }
}
