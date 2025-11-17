package com.mertalptekin.springrestapplication.controller;


import com.mertalptekin.springrestapplication.application.handler.IProductApplicationService;
import com.mertalptekin.springrestapplication.application.request.product.ProductDetailRequest;
import com.mertalptekin.springrestapplication.application.request.product.ProductRequest;
import com.mertalptekin.springrestapplication.application.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductApplicationService productApplicationService;
    public ProductController(IProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
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
        return ResponseEntity.ok(response);
    }




    // POST -> Create New Product

    // PUT/{id} -> Update Product By Id

    // DELETE/{id} -> Delete Product By Id

    // PATCH/{id} -> Partial Update Product By Id


}
