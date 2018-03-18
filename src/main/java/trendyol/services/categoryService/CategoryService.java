package trendyol.services.categoryService;


import trendyol.documents.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    List<Category> getAll();

    void checkCategoryExist(Long categoryId);

}
