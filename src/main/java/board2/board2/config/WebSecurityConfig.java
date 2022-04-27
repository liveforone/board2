package board2.board2.config;

import board2.board2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {  //인증을 무시할 경로들을 설정
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        antMatchers -> 경로 및 권환 설정
        permitAll -> 누구나 접근 가능
        hasRole -> 특정 권한이 있는 사람만 접근 가능
        authenticated -> 권한이 있으면 무조건 접근 가능
        anyRequest -> antMatchers에서 설정하지 않은 나머지 경로
        loginPage -> 로그인 페이지 링크 설정
        defaultSuccessUrl -> 로그인 성공 후 리다이렉트 경로
        logoutSuccessUrl -> 로그 아웃 성공 후 리다이렉트 경로
        invalidateHttpSession -> 로그 아웃 후 세션 전체 삭제
         */
        http.authorizeRequests()
                .antMatchers("/login", "/signup", "user").permitAll() //누구나 접근 허용
                .antMatchers("/").hasRole("USER")  //USER, ANMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN")  //ADMIN 만 접근 가능
                .anyRequest().authenticated()  //나머지 요청들은 권한의 종류에 상관없이 권한이 있어야 접근 가능
                .and()
                .formLogin()
                .loginPage("/login")  //로그인 페이지 경로
                .defaultSuccessUrl("/")  //로그인 성공 후 리다이렉트 경로
                .and()
                .logout()
                .logoutSuccessUrl("/login")   //로그아웃 성공시 리다이렉트 경로
                .invalidateHttpSession(true);  //세션 날리기
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}