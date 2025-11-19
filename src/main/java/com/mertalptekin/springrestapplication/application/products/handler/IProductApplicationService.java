package com.mertalptekin.springrestapplication.application.products.handler;

import com.mertalptekin.springrestapplication.application.products.request.product.*;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductResponse;

import java.util.List;

public interface IProductApplicationService  {

    ProductCreateResponse create(ProductCreateRequest request);
    void update(ProductUpdateRequest request);
    void delete(ProductDeleteRequest request);
    ProductDetailResponse detail(ProductDetailRequest request);
    List<ProductResponse> list(ProductRequest request);
    void stockOut(ProductStockOutRequest request);
}

