package re.edu.config.bai1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "re.edu") // Sửa lại đúng package chứa Controller
public class WebConfig {

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//
//        resolver.setPrefix("/views/");
//        resolver.setSuffix(".jsp");
//
//        return resolver;
//    }
}
