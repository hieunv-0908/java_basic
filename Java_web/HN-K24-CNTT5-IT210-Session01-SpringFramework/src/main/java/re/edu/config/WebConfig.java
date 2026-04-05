package re.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import re.edu.utils.InputMethod;

@Configuration // đây là lớp cấu hình
@EnableWebMvc  // sử dụng các cấu hình mặc định của MVC
@ComponentScan(basePackages = {"re.edu"})
public class WebConfig { //Cấu hình ứng dụng
    // Bean là thành phần cốt lõi của ứng dụng
    // Tuân thủ nguyên IOC : các đối tượng được tạo và quản lý trong Spring Container
    // C1 : sử dụng @Bean trong lớp cấu hính
//    // đánh dấu trên phương thức tạo bean
//    @Bean
//    public InputMethod inputMethod(){
//        return new InputMethod();
//    }

    // C2: sử dụng 4 chú thích : @Component, @Controller, @Service, @Repository

    // DI : lấy ra đối tượng bean trong container


    // cấu hình bean giao diện
    @Bean
    public ViewResolver  viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
