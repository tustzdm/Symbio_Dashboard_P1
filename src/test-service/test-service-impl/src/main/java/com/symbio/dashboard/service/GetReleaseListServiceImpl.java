package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetReleaseListUpload;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetReleaseListServiceImpl implements GetReleaseListService {


    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public Result getReleaseList(GetReleaseListUpload getReleaseListUpload) {
        return getReleaseListResult(getReleaseListUpload);
    }

    private Result getReleaseListResult(GetReleaseListUpload getReleaseListUpload) {
        Result result = new Result();
        String token = getReleaseListUpload.getToken();
        String locale = getReleaseListUpload.getLocale();
        Integer productId = getReleaseListUpload.getProductId();
        Integer pageIndex = getReleaseListUpload.getPageIndex();
        Integer pageSize = getReleaseListUpload.getPageSize();

        createReleaseList(pageIndex, pageSize, productId);




        return result;
    }

    private Result createReleaseList(Integer pageIndex,Integer pageSize,Integer productId) {
        Result result = new Result();

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Release> byId = releaseRepository.findByProductId(productId, pageable);

        for (Release release : byId) {
            System.out.println(release.toString());
        }
        System.out.println(byId.getSize());


        return result;
    }
}
