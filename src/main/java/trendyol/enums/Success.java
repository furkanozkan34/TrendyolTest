package trendyol.enums;


public enum Success {
    CAMPAIGN_CREATED("Kampanya oluşturma başarılı."),
    CAMPAIGN_UPDATED("Kampanya güncellendi."),
    CAMPAIGN_DELETED("Kampanya silindi.");

    Success(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }


    }
