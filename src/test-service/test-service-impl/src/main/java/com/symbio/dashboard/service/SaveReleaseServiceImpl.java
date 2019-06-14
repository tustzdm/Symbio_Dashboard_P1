package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveReleaseUpload;
import com.symbio.dashboard.ec.test.SaveReleaseErrorCode;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.repository.ProductRepository;
import com.symbio.dashboard.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaveReleaseServiceImpl implements  SaveReleaseService{


    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Result saveRelease(SaveReleaseUpload saveReleaseUpload) {
        return saveReleaseResult(saveReleaseUpload);
    }


    private Result saveReleaseResult(SaveReleaseUpload saveReleaseUpload) {
        Result result;

        String token = saveReleaseUpload.getToken();
        //获得一些信息
        Integer createUser = Integer.valueOf(token);

        String locale = saveReleaseUpload.getLocale();
        Integer productId = saveReleaseUpload.getProductId();
        Integer releaseId = saveReleaseUpload.getReleaseId();
        String name = saveReleaseUpload.getName();
        Integer status = saveReleaseUpload.getStatus();
        String start = saveReleaseUpload.getStart();
        String end = saveReleaseUpload.getEnd();
        //存入remark
        String description = saveReleaseUpload.getDescription();

        result = createRelease(productId, releaseId, name, status, start, createUser, end, description);
        if (!"0".equals(result.getEc())) {
            return result;
        }
        Release release = (Release) result.getCd();

        int flag = 0;
        try {
            releaseRepository.saveAndFlush(release);
            if (releaseId == null) {
                flag++;
            }
            flag++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        if (flag == 1) {
            result.setCdAndRightEcAndEm("edit");
        } else if (flag == 2) {
            result.setCdAndRightEcAndEm("add");
        } else {
            result.setEc(SaveReleaseErrorCode.SR0002.toString());
            result.setEm("没有做对的操作");
        }
        return result;
    }



    private Result createRelease(Integer productId,Integer releaseId,String name,
                                  Integer status,String start,Integer createUser,
                                  String end,String description) {

        Result result = new Result();
        List<String> allName = releaseRepository.getAllName();
        for (String s : allName) {
            if (name.equals(s)) {
                result.setEc(SaveReleaseErrorCode.SR0003.toString());
                result.setEm("已有release的名字，请重新填写");
                return result;
            }
        }

        List<Integer> allId = productRepository.getAllId();
//        allId.forEach(id-> System.out.println(id));


        boolean existentProductId = false;
        for (int i : allId) {
            if (i == productId) {
                existentProductId = true;
            }
        }
        if (!existentProductId) {
            result.setEc(SaveReleaseErrorCode.SR0001.toString());
            result.setEm("无此product的id");
        }

        Release release = new Release();
        release.setId(releaseId);

        //做product id 的查证
        release.setProduct_id(productId);
        release.setName(name);
        release.setStatus(status);
        release.setCreate_user(createUser);

        Date date = new Date();
        SimpleDateFormat systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat releaseTime = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String nowtime = systemTime.format(date);
            Date time = systemTime.parse(nowtime);
            release.setUpdate_time(time);
            if (releaseId == null) {
                release.setCreate_time(time);
            } else {
                Date createTime = releaseRepository.getCreate_timeById(releaseId);
                release.setCreate_time(createTime);
            }

            if (start != null) {
                Date startTime = releaseTime.parse(start);
                release.setStart_time(startTime);
            }

            if (end != null) {
                Date endTime = releaseTime.parse(end);
                release.setEnd_time(endTime);
            }

            release.setRemark(description);

            result.setCdAndRightEcAndEm(release);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
