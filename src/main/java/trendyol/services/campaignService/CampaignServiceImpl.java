package trendyol.services.campaignService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trendyol.base.BaseResponse;
import trendyol.base.ServiceException;
import trendyol.documents.Campaign;
import trendyol.dto.DiscountRequest;
import trendyol.dto.DiscountResponse;
import trendyol.enums.Exception;
import trendyol.enums.Success;
import trendyol.repositories.CampaignRepository;
import trendyol.services.categoryService.CategoryService;
import trendyol.services.productService.ProductService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository, CategoryService categoryService, ProductService productService) {
        this.campaignRepository = campaignRepository;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public BaseResponse save(Campaign requestCampaign) {
        log.debug("Save Request  campaign id : " +  requestCampaign.getId());
        checkCampaignIdExist(requestCampaign);
        campaignRepository.save(checkDiscountTypeParameters(requestCampaign, checkCampaignTypeParameters(requestCampaign)));
        log.debug("Campaign successfuly created: ");
        BaseResponse response = new BaseResponse();
        response.setMessage(Success.CAMPAIGN_CREATED.getType());
        return response;
    }

    @Override
    public BaseResponse edit(Campaign requestCampaign) {
        log.debug("Edit Request  campaign id : " +  requestCampaign.getId());
        campaignRepository.save(checkDiscountTypeParameters(requestCampaign, checkCampaignTypeParameters(requestCampaign)));
        log.debug("Campaign successfuly editted: ");
        BaseResponse response = new BaseResponse();
        response.setMessage(Success.CAMPAIGN_UPDATED.getType());
        return response;
    }

    @Override
    public Campaign getById(Long campaignId) {
        log.debug("Check request campaign id : " +  campaignId);
        if (campaignId == null)
            throw new ServiceException(Exception.CAMPAIGN_ID_CANNOT_BE_NULL.getType());
        else {
            return campaignRepository.findById(campaignId)
                    .orElseThrow(() -> new ServiceException(Exception.CAMPAIGN_NOT_FOUND.getType()));

        }
    }

    @Override
    public List<Campaign> getAll() {
        return campaignRepository.findAll();
    }

    @Override
    public BaseResponse delete(Long campaignId) {
        log.debug("Delete request campaign id : " +  campaignId);
        campaignRepository.delete(getById(campaignId));
        log.debug("Campaign successfuly deleted: ");
        BaseResponse response = new BaseResponse();
        response.setMessage(Success.CAMPAIGN_DELETED.getType());
        return response;
    }

    private List<DiscountResponse> mapper(List<DiscountRequest> requestList) {
        List<DiscountResponse> responseList = new ArrayList<>();
        for (DiscountRequest request : requestList) {
            DiscountResponse response = new DiscountResponse();
            response.setPrice(request.getPrice());
            response.setCategoryId(request.getCategoryId());
            response.setProductId(request.getProductId());
            response.setDiscountedPrice(request.getPrice());
            responseList.add(response);
        }
        return responseList;

    }

    private List<Campaign> getCampaignListByType(Boolean type) {
        log.debug("Campaign type parameter : " + type);
        return campaignRepository.findByCampaignType(type);
    }

    private Boolean checkMoreThanOneCategory(Campaign campaign, List<DiscountRequest> list) {
        int count = 0;
        for (DiscountRequest discountRequest : list) {
            if (campaign.getCategoryId().equals(discountRequest.getCategoryId())) {
                count++;
            }
        }
        if (count > 1)
            return true;
        else
            return false;

    }

    private int findBiggestPriceIndex(Campaign campaign, List<DiscountRequest> requestList) {
        int index = -1;
        BigDecimal firstPrice = BigDecimal.ZERO;
        for (int i = 0; i < requestList.size(); i++) {
            if (campaign.getCategoryId().equals(requestList.get(i).getCategoryId())) {
                if (requestList.get(i).getPrice().compareTo(firstPrice) == 1) {
                    firstPrice = requestList.get(i).getPrice();
                    index = i;
                }
            }
        }
        log.debug("Biggest price index : " + index);
        return index;
    }

    private List<DiscountResponse> updateWithIndex(int index, Campaign campaign, List<DiscountResponse> requestList) {
        log.debug("Update index : " + index);
        requestList.get(index).setDiscountedPrice(calculate(campaign.getDiscountType(),
                campaign.getDiscountRate(),
                campaign.getDiscountPrice(),
                requestList.get(index).getPrice(),
                campaign.getMaxDiscountPrice()));
        return requestList;

    }

    private List<DiscountResponse> updateCampaignList(Boolean type, Campaign campaign, List<DiscountResponse> requestList) {
        log.debug("Type is : " + type );
        for (DiscountResponse response : requestList) {
            if(type)/*kategori*/{
                if (response.getCategoryId().equals(campaign.getCategoryId())) {
                    response.setDiscountedPrice((calculate(campaign.getDiscountType(),
                            campaign.getDiscountRate(),
                            campaign.getDiscountPrice(),
                            response.getPrice(),
                            campaign.getMaxDiscountPrice())));
                }
            }
            else {
                if (response.getProductId().equals(campaign.getProductId())) {
                    response.setDiscountedPrice((calculate(campaign.getDiscountType(),
                            campaign.getDiscountRate(),
                            campaign.getDiscountPrice(),
                            response.getPrice(),
                            campaign.getMaxDiscountPrice())));
                }
            }


        }
        log.debug("Campaign list updated");
        return requestList;

    }
/*
    private List<DiscountResponse> updateProductCampaign(Campaign campaign, List<DiscountResponse> requestList) {
        for (DiscountResponse response : requestList) {
            if (response.getProductId().equals(campaign.getProductId())) {
                response.setDiscountedPrice((calculate(campaign.getDiscountType(),
                        campaign.getDiscountRate(),
                        campaign.getDiscountPrice(),
                        response.getPrice(),
                        campaign.getMaxDiscountPrice())));
            }
        }
        return requestList;

    }*/


    @Override
    public List<DiscountResponse> calculateTotalDiscount(List<DiscountRequest> requestList) {
        List<Campaign> categoryCampaigns = getCampaignListByType(true); /*Kategori kampanyaları*/
        List<Campaign> productCampaigns = getCampaignListByType(false); /*Ürün kampanyaları*/
        if ((categoryCampaigns.isEmpty() || categoryCampaigns.size() == 0) && (productCampaigns.isEmpty() || productCampaigns.size() == 0)) {
            return mapper(requestList);
        } else {
            List<DiscountResponse> responseList = mapper(requestList);
            if (!categoryCampaigns.isEmpty() || categoryCampaigns.size() > 0) {

                for (Campaign categoryCampaign : categoryCampaigns) {

                    if (checkMoreThanOneCategory(categoryCampaign, requestList)) {
                        int index = findBiggestPriceIndex(categoryCampaign, requestList);
                        responseList = updateWithIndex(index, categoryCampaign, responseList);
                    } else {
                        responseList = updateCampaignList(true,categoryCampaign, responseList); /* true = kategori */
                    }
                }
            }

            if (!productCampaigns.isEmpty() || productCampaigns.size() > 0) {
                for (Campaign productCampaign : productCampaigns) {
                    updateCampaignList(false,productCampaign, responseList);  /*false = product*/
                }
            }
            return responseList;
        }
    }

    private BigDecimal calculate(Boolean type, BigDecimal rate, BigDecimal discountPrice, BigDecimal productPrice, BigDecimal maxDiscount) {
        log.debug("Type:" + type);
        log.debug("Rate:" + rate);
        log.debug("DiscountPrice:" + discountPrice);
        log.debug("ProductPrice:" + productPrice);
        log.debug("MaxDiscount:" + maxDiscount);


        if (type) {/*oran*/
            if (productPrice.divide(new BigDecimal(100)).multiply(rate).compareTo(maxDiscount) == 1)
                return productPrice.subtract(maxDiscount).round(new MathContext(4, RoundingMode.HALF_UP));
            else
                return productPrice.subtract(productPrice.divide(new BigDecimal(100)).multiply(rate)).round(new MathContext(4, RoundingMode.HALF_UP));
        } else if (!type)/*tutar*/
            return productPrice.subtract(discountPrice).round(new MathContext(4, RoundingMode.HALF_UP));
        else
            return productPrice;

    }


    private void checkCampaignIdExist(Campaign requestCampaign) {
        log.debug("Checking campaign id : " + requestCampaign.getId());
        if (requestCampaign.getId() == null)
            throw new ServiceException(Exception.CAMPAIGN_ID_CANNOT_BE_NULL.getType());

        Optional<Campaign> optionalCampaign = campaignRepository.findById(requestCampaign.getId());
        optionalCampaign.ifPresent(u -> {
            throw new ServiceException(Exception.CAMPAIGN_ALLREADY_EXIST.getType());
        });
    }

    private void checkCampaignId(Campaign requestCampaign, Campaign newCampaign) {
        log.debug("Checking campaign id : " + requestCampaign.getId());
        if (requestCampaign.getId() == null)
            throw new ServiceException(Exception.CAMPAIGN_ID_CANNOT_BE_NULL.getType());
        else
            newCampaign.setId(requestCampaign.getId());
    }

    private void checkCampaignName(Campaign requestCampaign, Campaign newCampaign) {
        log.debug("Checking campaign id : " + requestCampaign.getId());
        if (requestCampaign.getName() == null || requestCampaign.getName().equals(""))
            throw new ServiceException(Exception.CAMPAIGN_NAME_CANNOT_BE_NULL.getType());
        else
            newCampaign.setName(requestCampaign.getName());
    }

    private void checkCategoryCampaignExist(Long categoryId) {
        log.debug("Checking categori id : " + categoryId);
        Optional<Campaign> optionalCampaign = campaignRepository.findByCategoryId(categoryId);
        optionalCampaign.ifPresent(u -> {
            throw new ServiceException(Exception.CAMPAIGN_CATEGORY_ALLREADY_EXIST.getType());
        });
    }

    private void checkProductCampaignExist(Long productId) {
        log.debug("Checking product id : " + productId);
        Optional<Campaign> optionalCampaign = campaignRepository.findByProductId(productId);
        optionalCampaign.ifPresent(u -> {
            throw new ServiceException(Exception.CAMPAIGN_PRODUCT_ALLREADY_EXIST.getType());
        });
    }

    private Campaign checkCampaignTypeParameters(Campaign requestCampaign) {
        Campaign newCampaign = new Campaign();
        checkCampaignId(requestCampaign, newCampaign);
        checkCampaignName(requestCampaign, newCampaign);

        if (requestCampaign.getCampaignType() != null && requestCampaign.getCampaignType()) { /*Kategori Kampanyası*/
            categoryService.checkCategoryExist(requestCampaign.getCategoryId());
            checkCategoryCampaignExist(requestCampaign.getCategoryId());
            newCampaign.setCampaignType(true);
            newCampaign.setCategoryId(requestCampaign.getCategoryId());

        } else if (requestCampaign.getCampaignType() != null && !requestCampaign.getCampaignType()) { /*Ürün Kampanyası*/
            productService.checkProductExist(requestCampaign.getProductId());
            checkProductCampaignExist(requestCampaign.getProductId());
            newCampaign.setCampaignType(false);
            newCampaign.setProductId(requestCampaign.getProductId());
        } else {
            throw new ServiceException(Exception.CAMPAIGN_TYPE_CANNOT_BE_NULL.getType());
        }

        return newCampaign;
    }

    private Campaign checkDiscountTypeParameters(Campaign requestCampaign, Campaign newCampaign) {
        if (requestCampaign.getDiscountType() != null && requestCampaign.getDiscountType()) { /*Oran*/
            newCampaign.setDiscountType(true);
            newCampaign.setDiscountPrice(null);
            if (requestCampaign.getDiscountRate() != null)
                newCampaign.setDiscountRate(requestCampaign.getDiscountRate());
            else
                throw new ServiceException(Exception.CAMPAIGN_DISCOUNT_RATE_CANNOT_BE_NULL.getType());

            if (requestCampaign.getMaxDiscountPrice() != null) {
                BigDecimal hundert = new BigDecimal(100);
                if (requestCampaign.getMaxDiscountPrice().compareTo(hundert) == 1) {
                    newCampaign.setMaxDiscountPrice(hundert);
                } else
                    newCampaign.setMaxDiscountPrice(requestCampaign.getMaxDiscountPrice());
            } else
                throw new ServiceException(Exception.CAMPAIGN_MAX_DISCOUNT_PRICE_CANNOT_BE_NULL.getType());

        } else if (requestCampaign.getDiscountType() != null && !requestCampaign.getDiscountType()) { /*Tutar*/
            newCampaign.setDiscountType(false);
            newCampaign.setDiscountRate(null);
            if (requestCampaign.getDiscountPrice() != null)
                newCampaign.setDiscountPrice(requestCampaign.getDiscountPrice());
            else
                throw new ServiceException(Exception.CAMPAIGN_DISCOUNT_PRICE_CANNOT_BE_NULL.getType());
        } else {
            throw new ServiceException(Exception.CAMPAIGN_DISCOUNT_TYPE_CANNOT_BE_NULL.getType());
        }

        return newCampaign;
    }


}
