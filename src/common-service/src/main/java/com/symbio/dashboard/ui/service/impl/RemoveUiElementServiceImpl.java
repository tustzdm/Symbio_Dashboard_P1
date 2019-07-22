package com.symbio.dashboard.ui.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.repository.UiInfoRepository;
import com.symbio.dashboard.ui.service.RemoveUiElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName - RemoveUiElementServiceImpl
 * @Author - Danny
 * @Description - 删除页面元素实现类
 * @Date - 2019/7/9 16:50
 * @Version 1.0
 */

@Service
public class RemoveUiElementServiceImpl implements RemoveUiElementService {

    @Autowired
    private UiInfoRepository uiInfoRepository;

    @Override
    public Result removeUiElement(Integer id) {
        return removeUiElementResult(id);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 根据id删除相应元素
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     */
    private Result removeUiElementResult(Integer id) {

        try {
            uiInfoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            if (match(e.getMessage())) {
                return new Result("100010", "Id does not exist");
            }
        }
        return new Result("Element removed");
    }

    private boolean match(String text) {
        Pattern pattern = Pattern.compile("(No|entity|id|exists)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
