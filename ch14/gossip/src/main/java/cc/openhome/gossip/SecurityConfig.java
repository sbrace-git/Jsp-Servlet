package cc.openhome.gossip;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/member", "/new_message", "/del_message", "/logout").hasRole("MEMBER")
            .anyRequest().permitAll()
            .and()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/login")
                    .failureUrl("/?error")
                    .defaultSuccessUrl("/member")
            .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/?logout");
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select name, password, enable from t_account where name=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select name, role from t_account_role where name=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PolicyFactory htmlPolicy() {
        return new HtmlPolicyBuilder()
                    .allowElements("a", "b", "i", "del", "pre", "code")
                    .allowUrlProtocols("http", "https")
                    .allowAttributes("href").onElements("a")
                    .requireRelNofollowOnLinks()
                    .toFactory();
    }
}