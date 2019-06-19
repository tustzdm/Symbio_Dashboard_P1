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
        List<String> allName;

        List<Integer> allId = productRepository.getAllId();

        boolean existentProductId = false;
        for (int i : allId) {
            if (i == productId) {
                existentProductId = true;
            }
        }
        if (!existentProductId) {
            result.setEc(SaveReleaseErrorCode.SR0001.toString());
            result.setEm("无此product的id");
            return result;
        }

        Release release ;

        Date date = new Date();
        SimpleDateFormat systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String nowTime = systemTime.format(date);
            Date time = systemTime.parse(nowTime);
            if (releaseId == null) {
                allName = releaseRepository.getAllName(0);
                for (String s : allName) {
                    if (name.equals(s)) {
                        result.setEc(SaveReleaseErrorCode.SR0003.toString());
                        result.setEm("已有release的名字，请重新填写");
                        return result;
                    }
                }
                release = new Release();
                release.setCreateUser(createUser);
                release.setCreateTime(time);
            } else {
                allName = releaseRepository.getAllName(releaseId);
                for (String s : allName) {
                    if (name.equals(s)) {
                        result.setEc(SaveReleaseErrorCode.SR0003.toString());
                        result.setEm("已有release的名字，请重新填写");
                        return result;
                    }
                }
                release = releaseRepository.getById(releaseId);
//                Date createTime = releaseRepository.getCreate_timeById(releaseId);
//                release.setCreate_time(createTime);
            }

            release.setId(releaseId);
            //做product id 的查证
            release.setProductId(productId);
            release.setName(name);
            release.setStatus(status);
            release.setUpdateTime(time);

            if (start != null) {
                Date startTime = systemTime.parse(start);
                release.setStartTime(startTime);
            }

            if (end != null) {
                Date endTime = systemTime.parse(end);
                release.setEndTime(endTime);
            }

            release.setRemark(description);
            result.setCdAndRightEcAndEm(release);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
