package com.fampay.test.service;

import com.fampay.test.ApplicationProperties;
import com.fampay.test.dao.entities.VideoDetailsEntity;
import com.fampay.test.dao.repository.VideoDetailsRepository;
import com.fampay.test.thirdparty.youtube.data.dto.response.YoutubeDataSearchApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class YoutubeDataService {

    //DAO object
    @Autowired
    VideoDetailsRepository videoDetailsRepository;

    //It is used to call api's
    @Autowired
    RestTemplate restTemplate;

    //properties file
    @Autowired
    ApplicationProperties applicationProperties;

    //It is used for publishedAfter query param
    private String prevVideoPublishedTime;

    //It is used to avoid duplicates
    //since even if there is no records present
    // after publishedAfter datetime it then
    //also fetches one record which is same as publishedAfter video
    //Basically it fetches the records which are >= publishedAfter
    //and not > publishedAfter
    private String prevVideoId;

    //apiKeyIndex is index of list specified in
    //google.apikeys property of application.properties
    private int apiKeyIndex;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void fixedDelaySch() throws ParseException {

        if (apiKeyIndex == applicationProperties.getApiKeys().size()) {
            System.out.println("All API keys are exhausted, add new API key and restart the application");
            return;
        }

        String searchURL = applicationProperties.getYoutubeDataApiUrl() + "/search?part={part}&maxResults={maxResults}&order={order}&q={q}&key={key}";

        //Creating new map from youtube data query params specified in application.properties
        //and adding api key to the map using apiKeyIndex;
        Map<String, String> queryParams = new HashMap<>(applicationProperties.getYoutubeDataQueryParams());
        queryParams.put("key", applicationProperties.getApiKeys().get(apiKeyIndex));

        if (prevVideoPublishedTime != null) {
            searchURL = searchURL + "&publishedAfter={publishedAfter}";
            queryParams.put("publishedAfter", prevVideoPublishedTime);
        }

        try {
            //calling youtube data api with query params
            ResponseEntity<YoutubeDataSearchApiResponse> apiResponse = restTemplate.getForEntity(searchURL, YoutubeDataSearchApiResponse.class, queryParams);

            if (apiResponse.getStatusCode() == HttpStatus.OK) {
                YoutubeDataSearchApiResponse youtubeDataSearchApiResponse = apiResponse.getBody();

                for (int i = 0; i < youtubeDataSearchApiResponse.getItems().length; i++) {
                    if (prevVideoId != null && prevVideoId.equals(youtubeDataSearchApiResponse.getItems()[i].getId().getVideoId())) {
                        break;
                    } else {
                        VideoDetailsEntity videoDetailsEntity = new VideoDetailsEntity();
                        videoDetailsEntity.setDescription(youtubeDataSearchApiResponse.getItems()[i].getSnippet().getDescription());
                        videoDetailsEntity.setTitle(youtubeDataSearchApiResponse.getItems()[i].getSnippet().getTitle());
                        videoDetailsEntity.setThumbnailUrl(youtubeDataSearchApiResponse.getItems()[i].getSnippet().getThumbnails().getDefaultQuality().getUrl());

                        SimpleDateFormat format = new SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss'Z'");
                        format.setTimeZone(TimeZone.getTimeZone("UTC"));


                        Date publishTime = format.parse(youtubeDataSearchApiResponse.getItems()[i].getSnippet().getPublishTime());
                        videoDetailsEntity.setPublishDatetime(publishTime);

                        videoDetailsRepository.save(videoDetailsEntity);
                    }
                }

                prevVideoId = youtubeDataSearchApiResponse.getItems()[0].getId().getVideoId();
                prevVideoPublishedTime = youtubeDataSearchApiResponse.getItems()[0].getSnippet().getPublishTime();

            }
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.FORBIDDEN) {
                exception.getMessage().contains("quotaExceeded");
                apiKeyIndex++;
                System.out.println("Current API key quota exhausted, next API key will be used in next interval");
            }
            System.out.println("Exception: " + exception.getMessage());
        }
    }
}



