package com.fampay.test.thirdparty.youtube.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Thumbnails {

    //using JSON property here to map api response
    // since default is a keyword in java
    // we cannot put variable name as default
    @JsonProperty("default")
    private ThumbnailDetails defaultQuality;
    @JsonProperty("medium")
    private ThumbnailDetails mediumQuality;
    @JsonProperty("high")
    private ThumbnailDetails highQuality;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ThumbnailDetails {
        private String url;
        private int width;
        private int height;
    }
}
