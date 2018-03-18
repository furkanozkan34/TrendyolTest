package trendyol.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trendyol.documents.Campaign;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends MongoRepository<Campaign, Long> {

    Optional<Campaign> findByCategoryId(Long categoryId);

    Optional<Campaign> findByProductId(Long productId);

    List<Campaign> findByCampaignType(Boolean type);

}
