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
    @Configuration // This configuration is for the rest service (/rest/*...).
    public static class RestSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/rest/**")
                    .formLogin().loginPage("/rest/login")

                    // If the user have been authenticated successful it will show JSON object
                    // { "authenticated" : true } .
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            Authentication authentication)
                                throws IOException, ServletException {

                            JSONObject succLogin = new JSONObject();
                            succLogin.put("authenticated", true);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(succLogin.toString());
                        }
                    })

                    // If the user haven't been authenticated successful it will show JSON object
                    // { "authenticated" : false } .
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            AuthenticationException e)
                                throws IOException, ServletException {

                            JSONObject succLogin = new JSONObject();
                            succLogin.put("authenticated", false);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(succLogin.toString());
                        }
                    })

                    .and().authorizeRequests()
                    // /rest/login and /rest/register urls can be viewed only by
                    // not authenticated users.
                    .antMatchers("/rest/login").anonymous()
                    .antMatchers("/rest/register").anonymous()

                    // /rest/authentication url can be viewed by all the users.
                    .antMatchers("/rest/authentication").permitAll()

                    // Other urls are accessed if the authenticated user have role USER.
                    .anyRequest().hasRole("USER")

                    // That is the url that is logging out the authenticated user
                    // by deleting his/her cookie for authentication from the server.
                    .and().logout().logoutUrl("/rest/logout")
                        .logoutSuccessUrl("/rest/authentication")

                    // Disabling the csrf.
                    .and().csrf().disable();
        }
    }

    // This configuration is for the browser.
    @Configuration
    public static class WebSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    // Only Users with ROLE_USER can access below urls.
                    .antMatchers("/create-post", "/posts/edit/**",
                            "/posts", "/account",
                            "/account/**", "/logout").access("hasRole('ROLE_USER')")

                    // Only users that are not authenticated can access /login and /register urls.
                    .antMatchers("/login", "/register").access("isAnonymous()")

                    // Login page url is set to /login.
                    .and().formLogin().loginPage("/login")
                    .and().logout();
        }
    }

}
