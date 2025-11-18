package com.mertalptekin.springrestapplication.presentation.controller;


import com.mertalptekin.springrestapplication.application.handler.IProductApplicationService;
import com.mertalptekin.springrestapplication.application.request.product.*;
import com.mertalptekin.springrestapplication.application.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductResponse;
import com.mertalptekin.springrestapplication.infra.repository.IProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductApplicationService productApplicationService;
    private final ModelMapper modelMapper;

    public ProductController(IProductApplicationService productApplicationService, IProductRepository productRepository, ModelMapper modelMapper) {
        this.productApplicationService = productApplicationService;
        this.modelMapper = modelMapper;
    }


    // GET -> List All Of Products
//    @GetMapping  Yanlış kullanım
//    public List<ProductResponse> index() {
//        return Arrays.asList(
//                new ProductResponse(1, "Product 1", BigDecimal.valueOf(100.0),20),
//                new ProductResponse(2, "Product 2", BigDecimal.valueOf(200.0),20)
//        );
//    }


    // api/v1/products?search=&page=1&size=100&sort=name asc
    // @RequestParam -> Query Parametrelerini yakalamak için kullanılır
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "100") Integer size,
            @RequestParam(required = false, defaultValue = "name asc") String sort) {
        List<ProductResponse> products = productApplicationService.list(new ProductRequest(size,page,search,sort));
        return ResponseEntity.ok(products);
    }

    // GET/{id} -> Get Product By Id

    // api/v1/products/1
    @GetMapping("{id}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable("id") Integer id) {
        ProductDetailResponse response = productApplicationService.detail(new ProductDetailRequest(id));

        if(response == null){
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        return ResponseEntity.ok(response);
    }

    // veriyi json formatında post ederken @RequestBody kullanılır
    // POST -> Create New Product
    @PostMapping
    public ResponseEntity<ProductCreateResponse> createProduct(@Valid @RequestBody ProductCreateRequest productRequest) {

        ProductCreateResponse response = productApplicationService.create(productRequest);
        // api/v1/products/12 -> yeni oluşturulan kaynağın URI'si ve Detail endpoint'ine yönlendirme
        // 201 Created
        return ResponseEntity.created(URI.create("/api/v1/products/" + response.getId())).body(response);
    }

    // PUT/{id} -> Update Product By Id
    // Update işlemleride ise standar 204 No Content dönmektir.
    // @PathVariable("id") Integer id -> güncellenecek olan resource idsi
    @PutMapping("{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {



        ProductDetailResponse response = productApplicationService.detail(new ProductDetailRequest(id));

        if(response == null){
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        if(!id.equals(productUpdateRequest.id())){
            return ResponseEntity.badRequest().body("Given ProductId do not match"); // 400 Bad Request
        }

        productApplicationService.update(productUpdateRequest);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // DELETE/{id} -> Delete Product By Id
    // Entity Not Found durumunda 404 Not Found dönülür.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {

        ProductDetailResponse response = productApplicationService.detail(new ProductDetailRequest(id));

        if(response == null){
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        if(!id.equals(response.getId())){
            return ResponseEntity.badRequest().body("Given ProductId do not match"); // 400 Bad Request
        }

        productApplicationService.delete(new ProductDeleteRequest(id));
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // api/v1/products/1/stockOut
    // PATCH/{id} -> Partial Update Product By Id
    // 204 status code döneriz.
    // PUT tüm nesneyi günceller PUT tekil bir işlemdir. Ama Patch bir nesnenin sadece bir veya birkaç alanını güncelleriz.
    // Birden fazla use case için patch endpointleri oluşturabiliriz.
    @PatchMapping("{id}/stockOut")
    public ResponseEntity<String> stockOutProduct(@PathVariable("id") Integer id, @RequestBody ProductStockOutRequest stockOutRequest) {
        ProductDetailResponse response = productApplicationService.detail(new ProductDetailRequest(id));

        if(response == null){
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        if(!id.equals(stockOutRequest.id())){
            return ResponseEntity.badRequest().body("Given ProductId do not match"); // 400 Bad Request
        }

        productApplicationService.stockOut(stockOutRequest);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // api/v1/products/1/discountPrice -> PATCH DISCOUNT PRICE
    // api/v1/products/1/stockIn  -> PATCH STOCK IN
    // api/v1/products -> POST CREATE
    // api/v1/products/1 -> GET DETAIL
    // api/v1/products/1 -> PUT UPDATE
    // api/v1/products/1 -> DELETE

    // Nested Route tanımı yaparken sorulması gereken soru ? ProductFeedback kendi başına bir anlam ifade ediyor mu ?

    // Nested Route Kullanımı -> Product Controller tüm Product Nesneleri bir aggregate root görevi görür.
    // api/v1/products/1/feedbacks -> GET Product Reviews
    // api/v1/products/1/feedbacks -> POST Create Product Review
    // api/v1/products/1/feedbacks/1 -> DELETE Product Feedback, 1. Numaralı ürüne ait 1. Numaralı feedback silinsin

    // Eğer nested route tanımı yapmayıp yeni bir controller açarsak ProductFeedbackController oluşturulacak
    // Bu kullnımıda doğru bulmuyoruz.
    // api/v1/product-feedbacks/1 -> DELETE Product Reviews


    // API da endpoint yanlışları
    // api/v1/products/discountPrice/1 -> yanlış
    // api/v1/product-discountPrice/1 -> yanlış
    // api/v1/products/create POST
    // api/v1/products/delete DELETE

}
