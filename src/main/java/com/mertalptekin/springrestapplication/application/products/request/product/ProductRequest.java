package com.mertalptekin.springrestapplication.application.products.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

// Filtrelenecek olan String veri -> ürün ismine göre filtreleme
// Sıralanacak olan String veri -> ürün fiyatına göre sıralama, stok değerine göre sıralama
// 1000 kayıt var 1 sayfada 100 kayıt almak istiyorum 10 sayfa
// page_size=100&page_number=10
public record ProductRequest(@JsonProperty("page_size") Integer pageSize,
                             @JsonProperty("page_number") Integer pageNumber,
                             @JsonProperty("search_key") String searchKey, @JsonProperty("sort_key") String sortKey) {
}
