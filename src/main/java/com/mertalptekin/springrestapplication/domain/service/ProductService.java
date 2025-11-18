package com.mertalptekin.springrestapplication.domain.service;


import com.mertalptekin.springrestapplication.domain.entity.Product;
import com.mertalptekin.springrestapplication.infra.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Crud bazlı bir domain servisi

@Service
@Slf4j
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Integer addProduct(Product product) {

        this.productRepository.save(product);
        log.info("Product added: {}", product);

        return product.getId();
    }

    @Override
    public void updateProduct(Product product) {
        this.productRepository.save(product);
        log.info("Product updated: {}", product);
    }

    @Override
    public void deleteProduct(Integer id) {

        this.productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        this.productRepository.deleteById(id);
        log.info("Product deleted with id: {}", id);
    }

    @Override
    public Product getProductById(Integer id) {

        Optional<Product> entity= this.productRepository.findById(id);
        if (entity.isPresent()) {
            log.info("Product found: {}", entity.get());
            return this.productRepository.findById(id).get();
        } else {
            log.warn("Product not found with id: {}", id);
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public List<Product> getAllProducts(Integer pageSize, Integer pageNumber, String search, String sort) {
        log.info("Getting all products");

        // name asc
        String sortField = sort.split(" ")[0];
        String sortDirection = sort.split(" ")[1];

        PageRequest pageRequest;

        if(sortDirection.equals("asc")){
            pageRequest = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortField).ascending());
        } else {
            pageRequest = PageRequest.of(pageNumber -1, pageSize, Sort.by(sortField).descending());
        }

        // Search işlemi ekleneceğinden dolayı ektra bir alan açılması lazım. Searchiçin findByName();
        Page<Product> productsPage = productRepository.findAllByNameContains(search,pageRequest);



        return productsPage.stream().toList();
    }
}
