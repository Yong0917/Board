package yong.board;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import yong.board.service.CustomOAuth2UserService;

@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
   // private UserDetailsService memberService;

    MyAuthenticationProvider myAuthenticationProvider;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
//        http.csrf().disable().headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers( "/**","/css/**","/image/**","/img/**","/js/**","/scss/**","/vendor/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/")
			.usernameParameter("email")
			.passwordParameter("passwd")
                .defaultSuccessUrl("/main")
                .permitAll();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        .and()
            .oauth2Login()
            .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
            // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
            .userService(customOAuth2UserService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시




    }

//    @Override
//    public void configure(WebSecurity web) throws Exception
//    {
//        web.ignoring()
//                .antMatchers("/resources/**")
//                .antMatchers("/css/**")
//                .antMatchers("/vendor/**")
//                .antMatchers("/js/**")
//                .antMatchers("/favicon*/**")
//                .antMatchers("/img/**")
//                .antMatchers("/image/**");
//        ;/*test 시에는 추가해야함*/
//    }

/*    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }*/
/*    @Bean
    public AuthenticationSuccessHandler successHandler() {
        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        return successHandler;

    }*/


}
