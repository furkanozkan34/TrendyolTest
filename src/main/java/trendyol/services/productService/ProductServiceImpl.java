package trendyol.services.productService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trendyol.base.ServiceException;
import trendyol.documents.Product;
import trendyol.enums.Exception;
import trendyol.repositories.ProductRepository;
import trendyol.services.categoryService.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product save(Product requestProduct) {
        log.debug("Request Product's CategoryId : " + requestProduct.getId());
        categoryService.checkCategoryExist(requestProduct.getCategoryId());
        log.debug("Saving Product id : " + requestProduct.getId());
        Optional<Product> optionalProduct = productRepository.findById(requestProduct.getId());
        optionalProduct.ifPresent(u -> {
            throw new ServiceException(Exception.PRODUCT_ALLREADY_EXIST.getType());
        });
        Product product = new Product();
        product.setId(requestProduct.getId());
        product.setName(requestProduct.getName());
        product.setCategoryId(requestProduct.getId());
        product.setPrice(requestProduct.getPrice());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void checkProductExist(Long productId) {
        log.debug("Checking product : " +  productId);
        if (productId == null)
            throw new ServiceException(Exception.PRODUCT_ID_CANNOT_BE_NULL.getType());
        productRepository.findById(productId)
                .orElseThrow(() -> new ServiceException(Exception.PRODUCT_NOT_FOUND.getType()));
    }
}
