package com.xboot.cache.config;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xboot.cache")
@ConditionalOnProperty(prefix = "xboot.cache", name = "enable", havingValue = "true")
public class CacheProperties {

    private String type;

    private String address;

    private Boolean enable = false;
    
    private String prefix;
    
    
}