package gg.stove.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import feign.RequestInterceptor;

public class NaverSearchHeaderConfiguration {

    @Value("${feign.naver-search.client-id}")
    private String CLIENT_ID;

    @Value("${feign.naver-search.client-secret}")
    private String CLIENT_SECRET;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
            .header("X-Naver-Client-Id", CLIENT_ID)
            .header("X-Naver-Client-Secret", CLIENT_SECRET);
    }
}
