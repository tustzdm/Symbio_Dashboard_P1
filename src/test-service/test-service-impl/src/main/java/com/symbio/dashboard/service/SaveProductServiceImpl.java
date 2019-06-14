package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveProductUpload;
import com.symbio.dashboard.ec.test.SaveProductErrorCode;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaveProductServiceImpl implements SaveProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Result saveProduct(SaveProductUpload saveProductUpload) {
        return saveProductResult(saveProductUpload);
    }

    /**
     * 此方法用于add或者edit 一个product项目
     * @param saveProductUpload 上送的product信息
     * @return 返回一个创建好的product项目
     */
    private Result saveProductResult(SaveProductUpload saveProductUpload) {
        Result result = new Result();
        String token = saveProductUpload.getToken();
        //暂时这样确定createUser
        Integer createUser = Integer.valueOf(token);

        String locale = saveProductUpload.getLocale();
        String name = saveProductUpload.getName();
        Integer productId = saveProductUpload.getProductId();
        Integer owner = saveProductUpload.getOwner();
        Integer manager = saveProductUpload.getManager();
        Integer qaleader = saveProductUpload.getQaleader();
        Integer devleader = saveProductUpload.getDevleader();
        String description = saveProductUpload.getDescription();

        int flag = 0;
        try {
            result = createProduct(productId, name, locale, owner, manager, qaleader, devleader, description, createUser);
            if (!"0".equals(result.getEc())) {
                return result;
            }
            Product product = (Product) result.getCd();
            productRepository.saveAndFlush(product);
            if (productId == null) {
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
            result.setEc(SaveProductErrorCode.SP0001.toString());
            result.setEm("没有对数据库进行操作或者操作异常");
        }

        return result;
    }


    /**
     * 此方法用于更新product的一个实体类
     *
     * @param name product的名字
     * @param locale 语种
     * @param owner 创建者
     * @param manager 管理者
     * @param qaleader qa leader
     * @param devleader develop leader
     * @param description 描述
     * @param createUser 创建者的id
     *
     * @return 返回一个创建出来的product实体类
     */
    private Result createProduct(Integer productId,String name, String locale, Integer owner,
                                  Integer manager, Integer qaleader,
                                  Integer devleader, String description,Integer createUser) {
        Result result = new Result();

        List<String> allName = productRepository.getAllName();
        for (String s : allName) {
            if (name.equals(s)) {
                result.setEc(SaveProductErrorCode.SP0002.toString());
                result.setEm("已有product的名字，请重新输入");
                return result;
            }
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date time = new Date();
        try {
            String nowtime = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(nowtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Product product ;
        if (productId == null) {
            product = new Product();
            product.setCreate_user(createUser);
            product.setCreate_time(date);
        } else {
            product = productRepository.getById(productId);
        }

        product.setId(productId);
        product.setName(name);
        product.setLocale(locale);
        product.setOwner(owner);
        product.setManager(manager);
        product.setQa_lead(qaleader);
        product.setDev_lead(devleader);
        product.setDescription(description);
        product.setUpdate_time(date);

//        try {
//            String nowtime = simpleDateFormat.format(date);
//            Date time = simpleDateFormat.parse(nowtime);
//            product.setUpdate_time(time);
//            if (productId == null) {
//                product.setCreate_time(time);
//            } else {
//                Date create_timeById = productRepository.getCreate_timeById(productId);
//                product.setCreate_time(create_timeById);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        result.setCdAndRightEcAndEm(product);


        return result;
    }




}
