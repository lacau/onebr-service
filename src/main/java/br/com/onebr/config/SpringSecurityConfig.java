package br.com.onebr.config;

import br.com.onebr.security.AuthenticationEntryPointImpl;
import br.com.onebr.security.AuthenticationRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_PATHS = {
        "/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    };

    @Autowired
    private AuthenticationRequestFilter authenticationRequestFilter;

    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/user/password-recover").permitAll()
            .antMatchers(HttpMethod.GET, "/view-data**").permitAll()
            .antMatchers(HttpMethod.GET, "/publication**").permitAll()
            .antMatchers(HttpMethod.POST, "/publication").permitAll()
            .antMatchers(HttpMethod.GET, "/team**").permitAll()
            .antMatchers(HttpMethod.GET, "/contributor**").permitAll()
            .antMatchers(HttpMethod.GET, "/map/coordinates**").permitAll()
            .antMatchers(HttpMethod.GET, "/bacteria**").permitAll()
            .antMatchers(HttpMethod.GET, "/bacteria/**").permitAll()
            .antMatchers(HttpMethod.GET, "/bacteria/filter/**").permitAll()
            .antMatchers(HttpMethod.POST, "/mail").permitAll()
            .antMatchers(HttpMethod.GET, "/image/**").permitAll()
            .antMatchers(SWAGGER_PATHS).permitAll()
            .anyRequest().authenticated();

        // Custom JWT based authentication
        //httpSecurity.rememberMe().alwaysRemember(true).tokenValiditySeconds(TokenUtil.JWT_TOKEN_EXPIRATION);
        httpSecurity.addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
