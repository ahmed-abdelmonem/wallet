package com.aabdelmonem.wallet.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aabdelmonem.wallet.util.WalletRequestInterceptor;


@Configuration
public class WalletMVCConfig implements WebMvcConfigurer {
 
    @Autowired
    private WalletRequestInterceptor walletRequestInterceptor;
 
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(walletRequestInterceptor).addPathPatterns("/**");
    }

}