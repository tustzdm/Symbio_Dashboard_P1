package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.GetProductListMessage;
import com.symbio.dashboard.dto.message.ProductMessageData;
import com.symbio.dashboard.dto.upload.GetProductListUpload;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.navigation.dto.download.ProductMessage;
import com.symbio.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetProductListServiceImpl implements GetProductListService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Result getProductList(GetProductListUpload getProductListUpload) {
        return getProductListResult(getProductListUpload);
    }

    private Result getProductListResult(GetProductListUpload getProductListUpload) {
        Result result ;
        String token = getProductListUpload.getToken();


        String locale = getProductListUpload.getLocale();
        Integer pageIndex = getProductListUpload.getPageIndex();
        Integer pageSize = getProductListUpload.getPageSize();

        //------
        result = createProductList(pageIndex, pageSize);
        if (!"0".equals(result.getEc())) {
            return result;
        }
        List<ProductMessageData> list = (List<ProductMessageData>) result.getCd();

        GetProductListMessage getProductListMessage = new GetProductListMessage();
        getProductListMessage.setRole(token);
        getProductListMessage.setLocale(locale);
        getProductListMessage.setPageIndex(pageIndex);
        getProductListMessage.setPageSize(pageSize);
        getProductListMessage.setTotalRecord(productRepository.count());
        getProductListMessage.setData(list);

        result.setCdAndRightEcAndEm(getProductListMessage);
        return result;
    }


    private Result createProductList(Integer pageIndex,Integer pageSize) {
        Result result = new Result();
        List<ProductMessageData> list = new LinkedList<>();
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<Product> all = productRepository.findAll(pageable);


        for (Product p : all) {
            ProductMessageData productMessageData = new ProductMessageData();
            productMessageData.setId(p.getId());
            productMessageData.setName(p.getName());
            productMessageData.setOwner(p.getOwner().toString());
            productMessageData.setManager(p.getManager().toString());
            productMessageData.setQaleader(p.getQaLead().toString());
            productMessageData.setDevleader(p.getDevLead().toString());
            productMessageData.setDescription(p.getDescription());

            list.add(productMessageData);
        }

        result.setCdAndRightEcAndEm(list);

        return result;
    }
}
