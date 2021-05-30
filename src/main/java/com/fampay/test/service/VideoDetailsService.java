package com.fampay.test.service;

import com.fampay.test.dao.entities.VideoDetailsEntity;
import com.fampay.test.dao.repository.VideoDetailsRepository;
import com.fampay.test.ui.dto.request.DashboardRequestDto;
import com.fampay.test.ui.dto.response.DashboardResponseDto;
import com.fampay.test.ui.dto.response.VideoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoDetailsService {

    //DAO object
    @Autowired
    VideoDetailsRepository videoDetailsRepository;

    public DashboardResponseDto getVideoDetails(
            DashboardRequestDto dashboardRequestDto) {

        try {
            //calculates pageNo using start and length variable provided by dashboardRequestDto
            final int pageNo = getPageNo(dashboardRequestDto.getStart(),
                    dashboardRequestDto.getLength());

            final Pageable pageable = createPageable(pageNo, dashboardRequestDto.getLength(),
                    dashboardRequestDto.getColumns()
                            .get(dashboardRequestDto.getOrder().get(0).getColumn()).getData(),
                    dashboardRequestDto.getOrder().get(0).getDir());

            Page<VideoDetailsEntity> videoDetailsPage = getVideoDetailsFromDB(dashboardRequestDto, pageable);

            //if there is any data present in db against given query
            //then populate the response dto and return
            //otherwise return empty response.
            if (null != videoDetailsPage && videoDetailsPage.getTotalElements() > 0) {
                final List<VideoDetailsEntity> videoDetailsEntityList = videoDetailsPage.getContent();
                final List<VideoDetails> videoDetailsList = mapVideoDetailsEntityListToVideoDetailsList(videoDetailsEntityList);

                final int totalRecords = videoDetailsRepository.getTotalRecords();

                final DashboardResponseDto dashboardResponseDto = createResponse(dashboardRequestDto.getDraw(), videoDetailsPage.getTotalElements(), totalRecords, videoDetailsList);

                return dashboardResponseDto;
            } else {
                return new DashboardResponseDto(dashboardRequestDto.getDraw(), 0, 0, new ArrayList<>());
            }
        } catch (final Exception ex) {
            return new DashboardResponseDto(dashboardRequestDto.getDraw(), 0, 0, new ArrayList<>());
        }
    }

    private DashboardResponseDto createResponse(int draw, long totalElementsInPage, int totalRecords, List<VideoDetails> videoDetailsList) {
        final DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
        dashboardResponseDto.setDraw(draw);
        dashboardResponseDto.setRecordsFiltered(totalElementsInPage);
        dashboardResponseDto.setRecordsTotal(totalRecords);
        dashboardResponseDto.setData(videoDetailsList);
        return dashboardResponseDto;
    }

    //mapping database entity to UI dto
    private List<VideoDetails> mapVideoDetailsEntityListToVideoDetailsList(List<VideoDetailsEntity> videoDetailsEntityList) {
        final List<VideoDetails> videoDetailsList = new ArrayList<>();
        for (final VideoDetailsEntity videoDetailsEntity : videoDetailsEntityList) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String publishDateTime = format.format(videoDetailsEntity.getPublishDatetime());
            final VideoDetails videoDetails = new VideoDetails(videoDetailsEntity.getTitle(),
                    videoDetailsEntity.getDescription(),
                    videoDetailsEntity.getThumbnailUrl(),
                    publishDateTime);

            videoDetailsList.add(videoDetails);
        }
        return videoDetailsList;
    }

    private int getPageNo(int firstRowNo, int numberOfRowsInPage) {
        int pageNo = firstRowNo / numberOfRowsInPage;
        pageNo = pageNo + 1;
        return pageNo;
    }

    //Here we are creating Pageable which will be used
    // to fetch data from DB for a specific page
    //also it will be used to fetch sorted data
    private Pageable createPageable(int pageNo, int pageSize, String columnNameToSort, String orderDirection) {
        if (orderDirection.equals("asc")) {
            return PageRequest.of(pageNo, pageSize, Sort.by(columnNameToSort).ascending());
        } else {
            return PageRequest.of(pageNo, pageSize, Sort.by(columnNameToSort).descending());
        }
    }

    //This method will get video details from DB
    //using pageable and search value that user might have given on UI
    private Page<VideoDetailsEntity> getVideoDetailsFromDB(DashboardRequestDto dashboardRequestDto, Pageable pageable) {
        Page<VideoDetailsEntity> videoDetailsPage = null;
        if (dashboardRequestDto.getSearch() != null && dashboardRequestDto.getSearch().getValue() != null && !dashboardRequestDto.getSearch().getValue().isEmpty()) {
            videoDetailsPage = videoDetailsRepository.findByTitle(pageable, dashboardRequestDto.getSearch().getValue());
        } else {
            videoDetailsPage = videoDetailsRepository.findAll(pageable);
        }
        return videoDetailsPage;
    }
}
