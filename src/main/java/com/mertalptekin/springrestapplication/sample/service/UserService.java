package com.mertalptekin.springrestapplication.sample.service;


import com.mertalptekin.springrestapplication.application.users.UserDto;
import com.mertalptekin.springrestapplication.domain.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

// Auto Bean tanımı -> @Service anotasyonu ile bu sınıfın bir servis bileşeni olduğunu belirtiyoruz. Spring bu sınıfı otomatik olarak tarar ve bir Bean olarak konteynıra ekler.
// @Repository anotasyonu ise veri erişim katmanını belirtmek için kullanılır. Ancak burada servis katmanı için @Service anotasyonunu kullanıyoruz.
// @Component da kullanılabilir ancak @Service, @Repository ve @Controller gibi daha spesifik anotasyonlar, kodun amacını daha net ifade eder.

// streotype.Service anotasyonu, bu sınıfın bir servis bileşeni olduğunu belirtir ve Spring'in bu sınıfı otomatik olarak tarayıp bir Bean olarak konteynıra eklemesini sağlar.
@Service
public class UserService {

    // Local field
    private final ModelMapper modelMapper;

    // Bir sınıfa ait dependecy başka bir sınıf içinde kullanırkende DI dependency Injection yapacağız.
    public UserService(ModelMapper modelMapper) { //   ModelMapper modelMapper Spirng Context'ten enjekte ediliyor.ve Modelmaper instance getirir.
        this.modelMapper = modelMapper;
    }


    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }


}
