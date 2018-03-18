package trendyol.services.campaignService;


import trendyol.base.BaseResponse;
import trendyol.documents.Campaign;
import trendyol.dto.DiscountRequest;
import trendyol.dto.DiscountResponse;

import java.util.List;

public interface CampaignService {

    BaseResponse save(Campaign requestCampaign);

    BaseResponse edit(Campaign requestCampaign);

    Campaign getById(Long campaignId);

    List<Campaign> getAll();

    BaseResponse delete(Long campaignId);

    List<DiscountResponse> calculateTotalDiscount(List<DiscountRequest> requestList);
}
