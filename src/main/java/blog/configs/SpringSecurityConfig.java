package blog.configs;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{

    @Autowired
    UserDetailsService userDetails;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Order(1)
    @Configuration
    public static class RestSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/rest/**")
                    .formLogin().loginPage("/rest/login")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            JSONObject succLogin = new JSONObject();
                            succLogin.put("authenticated", true);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(succLogin.toString());
                        }
                    })
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                            JSONObject succLogin = new JSONObject();
                            succLogin.put("authenticated", false);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(succLogin.toString());
                        }
                    })

                    .and().authorizeRequests()
                    .antMatchers("/rest/login").anonymous()
                    .antMatchers("/rest/register").anonymous()
                    .antMatchers("/rest/authentication").permitAll()
                    .anyRequest().hasRole("USER")
                    .and().logout().logoutUrl("/rest/logout")
                        .logoutSuccessUrl("/rest/authentication")
                    .and().csrf().disable();
        }
    }

    @Configuration
    public static class WebSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/create-post", "/posts/edit/**",
                            "/posts", "/account",
                            "/account/**", "/logout").access("hasRole('ROLE_USER')")

                    .antMatchers("/login", "/register").access("isAnonymous()")

                    .and().formLogin().loginPage("/login")
                    .and().logout();
        }
    }

}
