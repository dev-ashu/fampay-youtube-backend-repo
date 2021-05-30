package com.fampay.test.ui.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardResponseDto {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<VideoDetails> data;
}
