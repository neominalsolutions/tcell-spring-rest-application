package com.mertalptekin.springrestapplication.application.handler;

import com.mertalptekin.springrestapplication.application.request.product.*;
import com.mertalptekin.springrestapplication.application.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductResponse;

import java.util.List;

public interface IProductApplicationService  {

    ProductCreateResponse create(ProductCreateRequest request);
    void update(ProductUpdateRequest request);
    void delete(ProductDeleteRequest request);
    ProductDetailResponse detail(ProductDetailRequest request);
    List<ProductResponse> list(ProductRequest request);
    void stockOut(ProductStockOutRequest request);
}

