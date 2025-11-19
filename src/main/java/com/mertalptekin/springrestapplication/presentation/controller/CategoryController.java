package com.mertalptekin.springrestapplication.presentation.controller;

import com.mertalptekin.springrestapplication.application.categories.dto.CategoryDetailDto;
import com.mertalptekin.springrestapplication.application.categories.dto.SavedCategoryDto;
import com.mertalptekin.springrestapplication.domain.entity.Category;
import com.mertalptekin.springrestapplication.domain.entity.Product;
import com.mertalptekin.springrestapplication.infra.repository.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryController(ICategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDetailDto>> getCategories() {

        return ResponseEntity.ok(categoryRepository.findAll().stream().map(item -> modelMapper.map(item, CategoryDetailDto.class)).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDetailDto> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category  -> modelMapper.map(category, CategoryDetailDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<SavedCategoryDto> createCategory(@RequestBody Category category) {


        Random rd = new Random();
        rd.nextDouble(0,100);
        List<Product> products = new ArrayList<>();

        for(int i=0; i<10; i++) {
            Product p = new Product();
            p.setName("Product " + rd.nextInt(1,1000));
            p.setPrice(BigDecimal.valueOf(rd.nextDouble(0,100)));
            p.setStock(rd.nextInt(0,100));
            p.setCategory(category);
            products.add(p);
        }

        // Sorun: Response olarak entity döndürünce aşağıdaki sorunla karşılaşılıyor
        // Jackson tarafından serialize edilirken, Category içinde Product'lar var
        // Product'ların içinde tekrar Category var
        // Sonsuz döngü oluşuyor -> StackOverflowError
        // Çözüm: DTO kullanmak,  "error": "Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from `StreamWriteConstraints.getMaxNestingDepth()`)"
        //}

        // CASCADE PERSIST ile Category ile birlikte Product'lar da kaydedilir
        category.setProducts(products);
        // Veri tabanına kaydet
        categoryRepository.save(category);

       SavedCategoryDto response =  modelMapper.map(category, SavedCategoryDto.class);

        return ResponseEntity.ok(response);
    }

}
