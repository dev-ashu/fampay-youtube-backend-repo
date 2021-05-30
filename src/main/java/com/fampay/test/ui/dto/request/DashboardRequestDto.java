package com.fampay.test.ui.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardRequestDto {
    private Integer draw;
    private List<Column> columns = new ArrayList<>();
    private List<Order> order = new ArrayList<>();
    private Integer start;
    private Integer length;
    private Search search;
}
