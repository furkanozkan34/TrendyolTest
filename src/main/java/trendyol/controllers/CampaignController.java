package trendyol.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trendyol.base.BaseResponse;
import trendyol.documents.Campaign;
import trendyol.dto.DiscountRequest;
import trendyol.dto.DiscountResponse;
import trendyol.services.campaignService.CampaignService;

import java.util.List;


@RestController
@RequestMapping("trendyol")
@Api(tags = "Kampanya İşlemleri",
        description = "Bu controller üzerinden kampanya görüntüleme, oluşturma ,güncelleme ve silme işlemleri yapılabilir. "+
                "Kampanyalara göre indirim hesaplama servisi de bu controller üzerinden çağırılmaktadır.")
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @ApiOperation(tags = "Kampanya İşlemleri", value = "Kampanya Oluştur", notes = "Verilen parametreler doğrultusunda kategori veya ürün kampanyası buradan oluşturulmaktadır",
             response = BaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.POST işlemleri başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")}
    )
    @RequestMapping(value = "/campaign/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.save(campaign));
    }


    @ApiOperation(tags = "Kampanya İşlemleri",value = "Kampanya Güncelle", notes = "Verilen parametreler doğrultusunda kampanya buradan güncellenmektedir",
            response = BaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.POST işlemleri başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @RequestMapping(value = "/campaign/edit", method = RequestMethod.PUT)
    public ResponseEntity<?> edit(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.edit(campaign));
    }

    @ApiOperation(tags = "Kampanya İşlemleri",value = "İd ye göre kampanya getirme", notes = "Verilen id parametresi doğrultusunda kampanya bilgileri listelenmektedir",
            response = Campaign.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.GET işlemi başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = Campaign.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignId", value = "Görüntülenmek istenen kampanya id si", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/campaign/get/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long campaignId) {
        return ResponseEntity.ok(campaignService.getById(campaignId));
    }

    @ApiOperation(tags = "Kampanya İşlemleri", value = "Tüm kampanyaları listele", notes = "Sistemdeki tüm kampanyalar buradan listelenmektedir",
            response = Campaign.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.GET işlemi başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = Campaign.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @RequestMapping(value = "/campaign/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(campaignService.getAll());
    }


    @ApiOperation(tags = "Kampanya İşlemleri", value = "İd ye göre kampanya silme", notes = "Verilen id parametresi doğrultusunda kampanyayı silmektedir",
            response = BaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.GET işlemi başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignId", value = "Silinmek istenen kampanya id si", required = true, dataType = "Long", paramType = "path")})
    @RequestMapping(value = "/campaign/delete/{campaignId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long campaignId) {
        return ResponseEntity.ok(campaignService.delete(campaignId));
    }

    @ApiOperation(tags = "Kampanya İşlemleri", value = "İndirim hesapla", notes = "Sepetteki ürünleri kampanya süzgecinden geçirerek fiyatlarını günceller." +
            "Tanımlı bir kampanya yok ise ürün fiyatında bir değişiklik olmayacaktır", response = DiscountResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.POST işlemleri başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = DiscountResponse.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @RequestMapping(value = "/campaign/calculate", method = RequestMethod.POST)
    public ResponseEntity<?> calculate(@RequestBody List<DiscountRequest> requestList) {
        return ResponseEntity.ok(campaignService.calculateTotalDiscount(requestList));
    }
}
