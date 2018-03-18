package trendyol.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trendyol.documents.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
}
