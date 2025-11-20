package com.mertalptekin.springrestapplication.application.products.handler;

import com.mertalptekin.springrestapplication.application.products.request.product.*;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductCreateResponse;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductDetailResponse;
import com.mertalptekin.springrestapplication.application.products.response.product.ProductResponse;
import com.mertalptekin.springrestapplication.domain.entity.Category;
import com.mertalptekin.springrestapplication.domain.entity.Product;
import com.mertalptekin.springrestapplication.domain.service.IProductService;
import com.mertalptekin.springrestapplication.infra.repository.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

// 1.Yöntem: Service Sınıfı Oluşturma
// Caller Service -> Application Service -> Domain Service -> Repository
@Service
@Slf4j
public class ProductApplicationService implements IProductApplicationService{

    private final IProductService productService;
    private final ModelMapper modelMapper;
    private final ICategoryRepository categoryRepository;

    public ProductApplicationService(IProductService productService, ModelMapper modelMapper, ICategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductCreateResponse create(ProductCreateRequest request) {

        log.debug("Creating product {}", request);
        Product entity = modelMapper.map(request, Product.class);

        Category category = categoryRepository.findById(request.categoryId()).orElseThrow();

        entity.setCategory(category);
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