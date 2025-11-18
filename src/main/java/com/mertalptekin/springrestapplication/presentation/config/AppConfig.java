package com.mertalptekin.springrestapplication.presentation.config;

import com.mertalptekin.springrestapplication.sample.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Uygulama içerisinde config değerleri okumak ve aynı zamanda uygulama için gerekli olan bean tanımlarını yapmak için kullanılır.


    @Bean(name = "messageBean")
    String message(ModelMapper mapper) {
        mapper.map(UserDto.class, System.out);
        return "Hello Spring Bean";
    }

    @Bean(name = "messageBean1")
    String message1(){
        return "Hello Spring Bean 1";
    }

    // Spring Context'e tanımlanan nesnlere Bean diyoruz
    // Manuel tanımlama için Bean anatasyonu kullanır.
    // 3rd party kütüphaneleri de Bean olarak tanımlamak için kullandık. Bazen böyle ihtiyaçlarımız oluyor çünkü Model mapper servisini bir çok yerde kullanmamız gerekiyor. Bu tarz durumlar için Bean tanımlaması yapıyoruz.
    // 2. kısım model Mapper kullanımı
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Record sınıfları için gerekli konfigürasyon
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }

}
