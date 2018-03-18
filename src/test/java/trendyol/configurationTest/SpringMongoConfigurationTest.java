package trendyol.configurationTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trendyol.config.SpringMongoConfiguration;
import trendyol.documents.Product;
import trendyol.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
public class SpringMongoConfigurationTest {

    private final ProductRepository productRepository;

    @Autowired
    public SpringMongoConfigurationTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    static final int CAT_ID = 50;

    @Before
    public void init() {
        productRepository.deleteAll();
        for (int i = 0; i < CAT_ID; i++) {

            Integer categoryId = (int) (Math.random() * 100);
            Product product = new Product();
            product.setId(2L);
            product.setName("Hp notebook i7 500GB Harddisk");
            product.setCategoryId(categoryId.longValue());
            product.setPrice(new BigDecimal(1000));
            productRepository.save(product);
        }
    }

    @Test
    public void test() {
        List<Product> list = productRepository.findAll();
        assertEquals(CAT_ID, list.size());
    }
}
