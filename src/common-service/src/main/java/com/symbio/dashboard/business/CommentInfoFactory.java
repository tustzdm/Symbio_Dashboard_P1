package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.CommentInfo;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.DateUtil;

import java.util.*;

public class CommentInfoFactory {


    public static CommentInfo updateScreenShotComment(CommentInfo data, Integer fkId, String content, User userInfo) {

        data.setCommentType(EnumDef.COMMENT_INFO_TYPE.SCREEN_SHOT.getCode());
        data.setFkId(fkId);
        data.setUpdateUserId(userInfo.getId());
        data.setUpdateUserName(userInfo.getFullName());
        data.setContent(content);

        return data;
    }

    public static CommentInfo createNewScreenShotComment(Integer fkId, String content, User userInfo) {
        return createNewComment(EnumDef.COMMENT_INFO_TYPE.SCREEN_SHOT.getCode(), fkId, content, userInfo);
    }

    public static CommentInfo createNewComment(Integer commentType, Integer fkId, String content, User userInfo) {
        CommentInfo commentInfo = new CommentInfo();

        commentInfo.setCommentType(commentType);
        commentInfo.setFkId(fkId);
        commentInfo.setContent(content);
        commentInfo.setUpdateUserId(userInfo.getId());
        commentInfo.setUpdateUserName(userInfo.getFullName());

        commentInfo.setValidation(1);
        commentInfo.setCreateTime(new Date());
        return commentInfo;
    }

    public static Map<String, Object> renderCommentData(CommentInfo data) {
        Map<String, Object> map = new HashMap<>();

        if (!CommonUtil.isEmpty(data.getId())) {
            map.put("id", data.getId());
        }

        if (!CommonUtil.isEmpty(data.getFkId()) && data.getCommentType() == EnumDef.COMMENT_INFO_TYPE.SCREEN_SHOT.getCode()) {
            String key = CommonUtil.getCamelField(EnumDef.COMMENT_INFO_TYPE.SCREEN_SHOT.getValue() + "_id");
            map.put(key, data.getFkId());
        }

        map.put("userId", data.getUpdateUserId());
        map.put("userName", data.getUpdateUserName());
        map.put("comment", data.getContent());
        map.put("time", DateUtil.formatToYYYYMMDDMMHHSS(data.getUpdateTime()));

        return map;
    }

    public static List<Map<String, Object>> renderCommentListData(List<CommentInfo> listComment) {
        List<Map<String, Object>> retList = new ArrayList<>();
        for (CommentInfo data : listComment) {
            retList.add(renderCommentData(data));
        }
        return retList;
    }

}
