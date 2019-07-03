package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.GetProductInfoMessage;
import com.symbio.dashboard.dto.message.MessageUiInfo;
import com.symbio.dashboard.dto.message.ProductMessageData;
import com.symbio.dashboard.dto.upload.GetProductInfoUpload;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.navigation.dto.download.ProductData;
import com.symbio.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetProductInfoServiceImpl implements GetProductInfoService {


    @Autowired
    private ProductRepository productRepository;
    @Override
    public Result getProductInfo(GetProductInfoUpload getProductInfoUpload) {
        return getProductInfoResult(getProductInfoUpload);
    }

    private Result getProductInfoResult(GetProductInfoUpload getProductInfoUpload) {
        Result result ;
        String token = getProductInfoUpload.getToken();
        String locale = getProductInfoUpload.getLocale();
        Integer uiInfo = getProductInfoUpload.getUiInfo();
        Integer productId = getProductInfoUpload.getProductId();
        result = createProductInfoData(productId);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        List<ProductMessageData> list = (List<ProductMessageData>) result.getCd();
        GetProductInfoMessage getProductInfoMessage = new GetProductInfoMessage();
        if (uiInfo == 1) {
            result = createProductUiInfo();
            if (!"0".equals(result.getEc())) {
                return result;
            }
            MessageUiInfo messageUiInfo = (MessageUiInfo) result.getCd();
            getProductInfoMessage.setUiInfo(messageUiInfo);
        }
        getProductInfoMessage.setData(list);
        getProductInfoMessage.setLocale(locale);
        getProductInfoMessage.setRole(token);


        result.setCdAndRightEcAndEm(getProductInfoMessage);



        return result;
    }

    /**
     * 根据productId获得产品的内容
     * @param productId
     * @return
     */
    private Result createProductInfoData(Integer productId) {
        Result result = new Result();
        List<ProductMessageData> list = new LinkedList<>();

        Product byId = productRepository.getById(productId);
        if (byId != null) {

            ProductMessageData productMessageData = new ProductMessageData();
            productMessageData.setId(byId.getId());
            productMessageData.setName(byId.getName());
            productMessageData.setDescription(byId.getDescription());
            productMessageData.setDevleader(byId.getDevLead().toString());
            productMessageData.setQaleader(byId.getQaLead().toString());
            productMessageData.setManager(byId.getManager().toString());
            productMessageData.setOwner(byId.getOwner().toString());
            list.add(productMessageData);
        }
        result.setCdAndRightEcAndEm(list);



        return result;
    }

    private Result createProductUiInfo() {
        Result result = new Result();
        MessageUiInfo messageUiInfo = new MessageUiInfo();
        result.setCdAndRightEcAndEm(messageUiInfo);

        return result;
    }
}
