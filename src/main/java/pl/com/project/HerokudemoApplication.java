package pl.com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@SpringBootApplication
public class HerokudemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerokudemoApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
					.and()
					.withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
					.and()
					.withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
		}

		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			http
					.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
			http
					.csrf().disable()
					.authorizeRequests()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/anonymous*").anonymous()
					.antMatchers("/login*").permitAll()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login.html")
					.loginProcessingUrl("/perform_login")
					.defaultSuccessUrl("/hello.html", true)
					//.failureUrl("/login.html?error=true")
					//.failureHandler(authenticationFailureHandler())
					.and()
					.logout()
					.logoutUrl("/perform_logout")
					.deleteCookies("JSESSIONID");
					//.logoutSuccessHandler(logoutSuccessHandler());
		}


		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}

	@Configuration
	@ImportResource({ "classpath:webSecurityConfig.xml" })
	public class SecSecurityConfig {
		public SecSecurityConfig() {
			super();
		}
	}
	@Configuration
	public class MyConfiguration {

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurerAdapter() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
				}

			};
		}
	}

	abstract public class SpringApplicationInitializer
			extends AbstractAnnotationConfigDispatcherServletInitializer {

		protected Class<?>[] getRootConfigClasses() {
			return new Class[] {WebSecurityConfig.class};
		}
	}


}
