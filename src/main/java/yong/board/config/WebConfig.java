package yong.board.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yong.board.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")     //모두검사
                .excludePathPatterns("/", "/members/add", "/login.do", "/login.do","/userJoin","/registerUser","/emailCode" ,"/logout",
                        "/css/**", "/js/**", "/image/**", "/img/**", "/scss/**","/vendor/**" ,"/error");     //이건제외
    }

}
