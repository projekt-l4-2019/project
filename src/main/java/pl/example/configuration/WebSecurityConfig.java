package pl.example.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.example.models.UserrEntity;
import pl.example.repository.UserRepository;
import pl.example.service.UserService;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static org.eclipse.jdt.internal.compiler.problem.ProblemSeverities.Optional;

@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final long MAX_AGE_SECS = 3600;

    UserService userService = new UserService();

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://projekt-l4-2019.github.io","https://rhubarb-cobbler-84890.herokuapp.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/profileedit**", "/addnotice**", "/contact**", "/addopinion**", "/login**")
                .authenticated()
                .antMatchers("/index**")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("https://projekt-l4-2019.github.io/index.html").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository){
        return map -> {
            String email = (String)map.get("email");
            UserrEntity userr = userRepository.findByEmail(email);
            if(userr == null){
                userr = new UserrEntity();
                userr.setName((String)map.get("given_name"));
                userr.setSurname((String)map.get("family_name"));
                userr.setEmail((String)map.get("email"));
                userr.setAvatar((String)map.get("picture"));
                userRepository.save(userr);
            }
            userService.setCurrentUserId(userr.getIdUser());
            return userr;
        };
    }
}