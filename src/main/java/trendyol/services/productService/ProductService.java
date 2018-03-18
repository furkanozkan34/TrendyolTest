package trendyol.services.productService;


import trendyol.documents.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> getAll();

    void checkProductExist(Long productId);
}
