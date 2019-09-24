package com.tfx0one.oauth2demo.config;

import com.tfx0one.oauth2demo.config.service.SysClientDetailsService;
import com.tfx0one.oauth2demo.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 配置授权服务器
 * 2fx0one
 * 2019-09-16 10:46
 **/


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private DataSource dataSource;

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        // 配置数据源（注意，我使用的是 HikariCP 连接池），以上注解是指定数据源，否则会有冲突
//        return DataSourceBuilder.create().build();
//    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        SysClientDetailsService clientDetailsService = new SysClientDetailsService(dataSource);
        clients.withClientDetails(clientDetailsService);
//        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
//        clients.inMemory().withClient("client_1")
////                .resourceIds("order")
//                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("select")
////                .authorities("oauth2")
//                .secret(finalSecret)
//                .and()
//                .withClient("client_2")
////                .resourceIds("order")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("select")
////                .authorities("oauth2")
//                .accessTokenValiditySeconds(60) //过期时间
//                .secret(finalSecret);
    }

    //endpoint config
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
        ;
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix("demo:oauth:");
        return tokenStore;

    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients() //允许表单验证
                .tokenKeyAccess("permitAll()");
    }

}
