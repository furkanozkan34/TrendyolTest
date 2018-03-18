package trendyol.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import trendyol.base.BaseResponse;
import trendyol.documents.Campaign;
import trendyol.documents.Product;
import trendyol.services.productService.ProductService;

@RestController
@RequestMapping("trendyol")
@Api(tags = "Ürün İşlemleri",
        description = "Bu controller üzerinden ürün ekleme ve tüm ürünleri görüntüleme işlemleri yapılmaktadır. ")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @ApiOperation(tags = "Ürün İşlemleri", value = "Ürün Oluştur", notes = "Verilen parametreler ürünler buradan oluşturulmaktadır",
            response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.POST işlemleri başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")}
    )
    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }


    @ApiOperation(tags = "Ürün İşlemleri",value = "Tüm ürünleri listele", notes = "Sistemdeki tüm ürünler buradan listelenmektedir",
            response = Product.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.GET işlemi başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = Campaign.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @RequestMapping(value = "/product/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }
}
