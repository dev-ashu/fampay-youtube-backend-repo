package com.fampay.test.controller;

import com.fampay.test.ui.dto.request.DashboardRequestDto;
import com.fampay.test.ui.dto.response.DashboardResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/youtube/video")
public interface VideoDetailsController {

    @PostMapping("/details")
    DashboardResponseDto getVideoDetails(
            @RequestBody DashboardRequestDto ajaxJqueryDatatableRequestDto);

}
