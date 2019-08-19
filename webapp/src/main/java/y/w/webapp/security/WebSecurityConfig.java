package y.w.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfig
 *
 * @author ywang
 * @date 8/18/2019
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
           .authorizeRequests()
           .antMatchers("/employees").hasAuthority("USER")
           .and().formLogin()
           .and()
           .httpBasic();
    }

    /**
     * Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder
     * which required plain text passwords. In Spring Security 5, the default is
     * DelegatingPasswordEncoder, which required Password Storage Format.
     *
     * To use NoOpPasswordEncoder, add "{noop}<plain text password>" as below.
     *
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication()
//            .withUser("wyang").password("{noop}wyang").authorities("USER")
//            .and()
//            .withUser("admin").password("{noop}admin").authorities("USER");
//    }

    /**
     * Use the encoded password instead of plain text.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
            .withUser("wyang").password(passwordEncoder().encode("wyang")).authorities("USER")
            .and()
            .withUser("admin").password(passwordEncoder().encode("admin")).authorities("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
