package com.fampay.test.controller.impl;

import com.fampay.test.controller.VideoDetailsController;
import com.fampay.test.service.VideoDetailsService;
import com.fampay.test.ui.dto.request.DashboardRequestDto;
import com.fampay.test.ui.dto.response.DashboardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class VideoDetailsControllerImpl implements VideoDetailsController {

    @Autowired
    VideoDetailsService videoDetailsService;

    @Override
    public DashboardResponseDto getVideoDetails(@RequestBody DashboardRequestDto dashboardRequestDto) {
        return videoDetailsService.getVideoDetails(dashboardRequestDto);
    }
}
