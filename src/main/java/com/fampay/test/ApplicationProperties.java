package com.fampay.test;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Component
public class ApplicationProperties {
    @Value("#{${google.apikeys}}")
    private List<String> apiKeys;

    @Value("${youtube.data.api}")
    private String youtubeDataApiUrl;

    @Value("#{${youtube.data.api.query.params}}")
    private Map<String, String> youtubeDataQueryParams;
}
