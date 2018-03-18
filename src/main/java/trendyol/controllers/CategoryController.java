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
import trendyol.documents.Category;
import trendyol.documents.Product;
import trendyol.services.categoryService.CategoryService;

@RestController
@RequestMapping("trendyol")
@Api(tags = "Kategori İşlemleri",
        description = "Bu controller üzerinden kategori ekleme ve tüm kategorileri görüntüleme işlemleri yapılmaktadır. ")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @ApiOperation(tags = "Kategori İşlemleri", value = "Kategori Oluştur", notes = "Verilen parametreler doğrultusunda kategoriler buradan oluşturulmaktadır",
            response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.POST işlemleri başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")}
    )
    @RequestMapping(value = "/category/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.save(category));
    }



    @ApiOperation(tags = "Kategori İşlemleri", value = "Tüm kategorileri listele", notes = "Sistemdeki tüm kategoriler buradan listelenmektedir",
            response = Category.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yapılan işlem başarılı bir şekilde işlenmiştir.GET işlemi başarılı bir şekilde sonlandığında bu kodun dönmesi beklenmektedir.",
                    response = Campaign.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Kullanıcının gönderdiği verilerde uyumsuz veya geçersiz bir tipte veri barındırdığını belirtmektedir."),
            @ApiResponse(code = 404, message = "Kullanıcının aradığı kaynak sunucu üzerinde bulunmadığını belirtmektedir."),
            @ApiResponse(code = 500, message = "Sistemsel hata yakalandığında bu kodun dönmesi beklenmektedir")})
    @RequestMapping(value = "/category/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }


}
