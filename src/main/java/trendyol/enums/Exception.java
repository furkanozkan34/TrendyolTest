package trendyol.enums;


public enum Exception {
    CATEGORY_ALLREADY_EXIST("Bu id ye ait kategori daha önce eklenmiş"),
    CATEGORY_NOT_FOUND("Bu id ye ait kategori bulunamadı"),
    CATEGORY_ID_CANNOT_BE_NULL("Kategori id si boş olamaz"),
    PRODUCT_ALLREADY_EXIST("Bu id ye ait ürün daha önce eklenmiş"),
    PRODUCT_ID_CANNOT_BE_NULL("Ürün id si boş olamaz"),
    PRODUCT_NOT_FOUND("Bu id ye ait ürün bulunamadı"),
    CAMPAIGN_ALLREADY_EXIST("Bu id ye ait kampanya daha önce eklenmiş"),
    CAMPAIGN_CATEGORY_ALLREADY_EXIST("Bu kategoriye ait bir kampanya daha önce eklenmiş"),
    CAMPAIGN_PRODUCT_ALLREADY_EXIST("Bu ürüne ait bir kampanya daha önce eklenmiş"),
    CAMPAIGN_NOT_FOUND("Bu id ye ait kampanya bulunamadı"),
    CAMPAIGN_TYPE_CANNOT_BE_NULL("Kampanya tipi boş olamaz"),
    CAMPAIGN_ID_CANNOT_BE_NULL("Kampanya id si boş olamaz"),
    CAMPAIGN_NAME_CANNOT_BE_NULL("Kampanya adı boş olamaz"),
    CAMPAIGN_DISCOUNT_RATE_CANNOT_BE_NULL("İndirim oranı boş olamaz"),
    CAMPAIGN_DISCOUNT_PRICE_CANNOT_BE_NULL("İndirim tutarı boş olamaz"),
    CAMPAIGN_MAX_DISCOUNT_PRICE_CANNOT_BE_NULL("Oran tipindeki kampanyalarda maximum indirim boş olamaz"),
    CAMPAIGN_DISCOUNT_TYPE_CANNOT_BE_NULL("İndirim tipi boş olamaz"),
    DEFAULT("İşlem Başarısız.");

    Exception(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }


}
