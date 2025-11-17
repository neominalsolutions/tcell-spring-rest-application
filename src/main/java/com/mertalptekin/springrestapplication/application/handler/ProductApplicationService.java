package com.mertalptekin.springrestapplication.application.handler;

import com.mertalptekin.springrestapplication.application.request.product.*;
import com.mertalptekin.springrestapplication.application.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// 1.Yöntem: Service Sınıfı Oluşturma

@Service
public class ProductApplicationService implements IProductApplicationService{
    @Override
    public ProductCreateResponse create(ProductCreateRequest request) {
        System.out.println("Product created");
        return new ProductCreateResponse(1,"Product A");
    }

    @Override
    public void update(ProductUpdateRequest request) {
        System.out.println("Product Updated");
    }

    @Override
    public void delete(ProductDeleteRequest request) {
        System.out.println("Product Deleted");
    }

    @Override
    public ProductDetailResponse detail(ProductDetailRequest request) {
        return new ProductDetailResponse(1,"Sample Product", BigDecimal.valueOf(99.99), 10);
    }

    @Override
    public List<ProductResponse> list(ProductRequest request) {
        return List.of(new ProductResponse(1,"Sample Product", BigDecimal.valueOf(99.99), 10), new ProductResponse(2,"Sample Product", BigDecimal.valueOf(99.99), 10), new ProductResponse(3,"Sample Product", BigDecimal.valueOf(99.99), 10));
    }

    @Override
    public void stockOut(ProductStockOutRequest request) {
        System.out.println("Product Stock Out");
    }
}