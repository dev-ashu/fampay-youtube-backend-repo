package com.fampay.test.thirdparty.youtube.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String kind;
    private String etag;
    private ItemId id;
    private Snippet snippet;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ItemId {
        private String kind;
        private String videoId;
    }
}
