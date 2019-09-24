package com.tfx0one.oauth2demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2fx0one
 * 2019-09-16 10:44
 **/
@RestController
public class ResourceController {
    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/product/{id}")
    public String getA(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "a : " + id;
    }

    @GetMapping("/order/{id}")
    @PreAuthorize("@permissionService.hasPermission('sys_add')")
    public String getB(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "b: " + id;
    }
}
