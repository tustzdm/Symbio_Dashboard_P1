package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dao.ProductDao;
import com.symbio.dashboard.dto.message.GetProductListMessage;
import com.symbio.dashboard.dto.message.ProductMessageData;
import com.symbio.dashboard.dto.upload.GetProductListUpload;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.repository.ProductRepository;
import com.symbio.dashboard.ui.dto.upload.UiInfoUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetProductListServiceImpl implements GetProductListService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

//    @Override
//    public Result getProductList(GetProductListUpload getProductListUpload) {
////        return getProductListResult(getProductListUpload);
//        return getProductAllList(getProductListUpload);
//    }

    @Override
    public Result getProductList(GetProductListUpload getProductListUpload) {
        return getProductAllList(getProductListUpload);
//        if (uiInfoUpload == null) {
//            if (getProductListUpload.getPageIndex() != null)
//            else
//                return getProductListByPage(getProductListUpload);
//        } else {
//            if (getProductListUpload.getPageIndex() != null)
//                return getProductWithUiInfo(getProductListUpload, uiInfoUpload);
//            else
//                return getProductWithUiInfoByPage(getProductListUpload, uiInfoUpload);
//        }
    }

    private Result getProductListByPage(GetProductListUpload getProductListUpload) {
        Result result;
        GetProductListMessage getProductListMessage = new GetProductListMessage();
        try {
            String token = getProductListUpload.getToken();
            String locale = getProductListUpload.getLocale();
            Integer pageIndex = getProductListUpload.getPageIndex();
            Integer pageSize = getProductListUpload.getPageSize();

            result = createProductList(pageIndex, pageSize);
            if (result.hasError()) {
                return result;
            }

            List<ProductMessageData> list = (List<ProductMessageData>) result.getCd();

            getProductListMessage.setRole(token);
            getProductListMessage.setLocale(locale);
            getProductListMessage.setPageIndex(pageIndex);
            getProductListMessage.setPageSize(pageSize);
            getProductListMessage.setTotalRecord(productRepository.count());
            getProductListMessage.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(getProductListMessage);
    }

    private Result createProductList(Integer pageIndex, Integer pageSize) {
        List<ProductMessageData> list = new LinkedList<>();

        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(list);
    }

    private Result getProductAllList(GetProductListUpload getProductListUpload) {
        GetProductListMessage getProductListMessage = new GetProductListMessage();

        try {
            String token = getProductListUpload.getToken();
            String locale = getProductListUpload.getLocale();

            List<ProductMessageData> list = new ArrayList<>();
            List<Product> all = productDao.getProductList(getProductListUpload);

            for (Product o : all) {
                ProductMessageData productMessageData = new ProductMessageData();
                productMessageData.setId(o.getId());
                productMessageData.setName(o.getName());
                productMessageData.setOwner(o.getOwner().toString());
                productMessageData.setManager(o.getManager().toString());
                productMessageData.setQaleader(o.getQaLead().toString());
                productMessageData.setDevleader(o.getDevLead().toString());
                productMessageData.setDescription(o.getDescription());

                list.add(productMessageData);
            }

            getProductListMessage.setRole(token);
            getProductListMessage.setLocale(locale);
            getProductListMessage.setPageIndex(getProductListUpload.getPageIndex());
            getProductListMessage.setPageSize(getProductListUpload.getPageSize());
            getProductListMessage.setTotalRecord(productRepository.count());
            getProductListMessage.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(getProductListMessage);
    }

    private Result getProductWithUiInfo(GetProductListUpload getProductListUpload, UiInfoUpload uiInfoUpload) {

        return null;
    }

    private Result getProductWithUiInfoByPage(GetProductListUpload getProductListUpload, UiInfoUpload uiInfoUpload) {

        return null;
    }
}
