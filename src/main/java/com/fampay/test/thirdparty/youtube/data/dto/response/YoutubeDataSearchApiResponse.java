package com.fampay.test.thirdparty.youtube.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class YoutubeDataSearchApiResponse {
    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private Item[] items;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class PageInfo {
        private long totalResults;
        private int resultsPerPage;
    }
}
