package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		/*
		 * return new WebSecurityCustomizer() {
		 * 
		 * @Override public void customize(WebSecurity web) { web.httpFirewall(new
		 * DefaultHttpFirewall()); } };
		 */

		return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> {
			csrf.disable();
		}).formLogin((formLogin) -> {
			formLogin.loginPage("/user/login") // 우리 페이지로 이동
					.loginProcessingUrl("/user/auth").usernameParameter("email").passwordParameter("password")
					.defaultSuccessUrl("/").failureUrl("/user/login?result=fail")
					.failureHandler(new AuthenticationFailureHandler() {

						@Override
						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
								AuthenticationException exception) throws IOException, ServletException {
							request.setAttribute("email", request.getParameter("email"));
							request.setAttribute("result", "fail");

							request.getRequestDispatcher("/user/login").forward(request, response);
						}

					});
		}).logout((logout) -> {
			logout.logoutUrl("/user/logout").logoutSuccessUrl("/");
		}).authorizeHttpRequests((authorizeRequests) -> {
			/* ACL */
			authorizeRequests.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null))
					// .authenticated() // 사용자냐
					.hasAnyRole("ADMIN")

					.requestMatchers(new RegexRequestMatcher("^/user/update$", null)).hasAnyRole("ADMIN", "USER") // role이
																													// admin
																													// 이냐
																													// user
																													// 이냐

					.requestMatchers(new RegexRequestMatcher("^/board/?(write|modify|delete|reply)$", null))
					.hasAnyRole("ADMIN", "USER")

					.anyRequest().permitAll();
		}).exceptionHandling(exceptionHandling -> {
//			exceptionHandling.accessDeniedPage("/WEB-INF/views/errors/403.jsp");
			exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {

				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {

					response.sendRedirect(request.getContextPath());
				}

			});
		});

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setPasswordEncoder(passwordEncoder);
		authenticationProvider.setUserDetailsService(userDetailsService);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4); /* 4 ~ 31 */
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new UserDetailsServiceImpl(userRepository);
	}
}
