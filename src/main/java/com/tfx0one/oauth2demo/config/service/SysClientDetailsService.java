package com.tfx0one.oauth2demo.config.service;

import com.tfx0one.oauth2demo.config.SecurityConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;


/**
 * 2fx0one
 * 2019-09-24 10:21
 **/
public class SysClientDetailsService extends JdbcClientDetailsService {

    public SysClientDetailsService(DataSource dataSource) {
        super(dataSource);
        this.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        this.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
    }

    @Override
    @Cacheable(value = "CLIENT_DETAILS_KEY", key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}
