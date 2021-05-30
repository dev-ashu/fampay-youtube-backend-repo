package com.fampay.test.ui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoDetails {
    private String title;
    private String description;
    private String thumbnailUrl;
    private String publishDatetime;
}
