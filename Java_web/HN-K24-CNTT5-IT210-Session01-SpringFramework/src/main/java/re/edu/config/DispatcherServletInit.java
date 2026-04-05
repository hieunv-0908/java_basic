package re.edu.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer { // nơi khởi tạo ứng dụng

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // đọc cấu hình gốc
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // cấu hình về web : WebConfig
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
