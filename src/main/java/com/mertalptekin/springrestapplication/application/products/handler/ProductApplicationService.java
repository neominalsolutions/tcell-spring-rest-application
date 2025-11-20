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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

// 1.Yöntem: Service Sınıfı Oluşturma
// Caller Service -> Application Service -> Domain Service -> Repository
@Service
@Slf4j
public class ProductApplicationService implements IProductApplicationService{

    private final IProductService productService;
    private final ModelMapper modelMapper;

    public ProductApplicationService(IProductService productService, ModelMapper modelMapper, ICategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    public ProductCreateResponse create(ProductCreateRequest request) {

        log.debug("Creating product {}", request);

        // 2.Yöntem: Spring BeanUtils ile mapleme, Javanın kendi mapping kavramı -> aşağıdaki senerayoda modelmapper ile entity state hatası aldık. Bu durumlarda karşılaşmamak için update ve save işlemlerinde BeanUtils kullanılabilir.
        // okuma işlemlerinde modelmapper kullanılabilir. daha kolaylık sağlar ve entity state mapleme hatası alınmaz.
        Product entity = new Product();
        BeanUtils.copyProperties(request,entity);


        // TODO: Model Mapper ile mapleme çalışmıyor. Nedenini araştır. ?
//        Product entity = modelMapper.map(request, Product.class);

//        Product entity1 = new Product();
//        entity1.setName(request.name());
//        entity1.setPrice(request.price());
//        entity1.setStock(request.stock());
//        entity1.setCategoryId(request.categoryId());

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