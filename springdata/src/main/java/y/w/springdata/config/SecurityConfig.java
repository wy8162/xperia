package y.w.springdata.config;//package y.w.springdata.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * SecurityConfig
// */
//@Configuration
//@PropertySource("classpath:defaultUsers.properties")
//public class SecurityConfig extends WebSecurityConfigurerAdapter
//{
//    @Value("${default.user}")
//    private String defaultUser;
//
//    @Value("${default.password}")
//    private String defaultPassword;
//
//    @Value("${default.role}")
//    private String defaultRole;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser(defaultUser).password(passwordEncoder().encode(defaultPassword)).roles(defaultRole);
//    }
//
//    @Override protected void configure(HttpSecurity http) throws Exception
//    {
//        http.authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//}
