package com.mertalptekin.springrestapplication.application.handler;

import com.mertalptekin.springrestapplication.application.request.product.*;
import com.mertalptekin.springrestapplication.application.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.response.product.ProductResponse;
import com.mertalptekin.springrestapplication.domain.entity.Product;
import com.mertalptekin.springrestapplication.domain.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// 1.Yöntem: Service Sınıfı Oluşturma
// Caller Service -> Application Service -> Domain Service -> Repository
@Service
@Slf4j
public class ProductApplicationService implements IProductApplicationService{

    private final IProductService productService;
    private final ModelMapper modelMapper;

    public ProductApplicationService(IProductService productService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    public ProductCreateResponse create(ProductCreateRequest request) {

        log.debug("Creating product {}", request);
        Product entity = modelMapper.map(request, Product.class);
        this.productService.addProduct(entity);
        return this.modelMapper.map(entity, ProductCreateResponse.class);
    }

    @Override
    public void update(ProductUpdateRequest request) {

        Product entity = this.modelMapper.map(request, Product.class);
        this.productService.updateProduct(entity);
        log.debug("Updating product {}", request);
    }

    @Override
    public void delete(ProductDeleteRequest request) {
        this.productService.deleteProduct(request.id());
        log.debug("Deleting product {}", request);
    }

    @Override
    public ProductDetailResponse detail(ProductDetailRequest request) {

        Product entity = productService.getProductById(request.id());
        log.debug("Retrieving product {}", request);

        return  modelMapper.map(entity, ProductDetailResponse.class);
    }

    @Override
    public List<ProductResponse> list(ProductRequest request) {
        log.debug("Retrieving all products");
        return productService.
                getAllProducts(request.pageSize(),request.pageNumber(),request.searchKey(),request.sortKey()).stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .toList();
    }

    @Override
    public void stockOut(ProductStockOutRequest request) {


        Product entity = productService.getProductById(request.id());
        entity.setStock(request.quantity());
        productService.updateProduct(entity);

       log.debug("Stock out {}", request);
    }
}