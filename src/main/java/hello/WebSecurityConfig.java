package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter   {
    // curl http://user:password@localhost:8080/dummy
    // http://localhost:8080/greeting form login
    // default URL http://localhost:8080/logout
    // default http://localhost:8080/login shows login form
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user").password(passwordEncoder().encode("password")).roles("USER");
    }
    
    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
        httpSec
        .formLogin()
        .and()
        .authorizeRequests().antMatchers("/greeting**").authenticated()
        .and()
        .authorizeRequests().antMatchers("/**").permitAll();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}

