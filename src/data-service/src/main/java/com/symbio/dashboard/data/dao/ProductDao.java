package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.UiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName - ProductDao
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/11 16:05
 * @Version 1.0
 */

@Repository
public class ProductDao {

    private StringBuilder sql = new StringBuilder("select db_field from ui_info where page = 'product'");
    private StringBuilder sql2 = new StringBuilder("from product where 1=1");
    private StringBuilder sqlOrder = new StringBuilder(" order by p.id");

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UiInfoRep uiInfoRep;


    public List<Product> getProductList() {
        Query query;
        List<Product> products = null;
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("select ");

            List<UiInfo> uiInfoList = uiInfoRep.getUiInfoListByPageName("product");


            query = entityManager.createNativeQuery(sql.toString());
            products = query.getResultList();
            List<String> listFields = new ArrayList<>();

            for (int i = 0; i < products.size(); i++) {
                sb.append(products.get(i)).append(",");
                listFields.add(products.get(i).toString());
            }
            System.out.println(listFields);


            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(sql2);


            query = entityManager.createNativeQuery(sb.toString());
            products = query.getResultList();

            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }


    private List<Product> getProductFullList() {
        Query query;
        List<Product> products = null;

        try {

            query = entityManager.createNativeQuery(sql.toString(), UiInfo.class);

            products = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

//    private List<Product> getProductListByPage(GetProductListUpload getProductListUpload) {
//        Query query;
//        List<Product> products = null;
//        Integer index = null;
//        Integer size = null;
//
//        try {
//
//            index = getProductListUpload.getPageIndex();
//            size = getProductListUpload.getPageSize();
//            sql.append("limit ");
//            sql.append((index - 1) * size);
//            sql.append(", ");
//            sql.append(size);
//            sql.append(sqlOrder);
//
//            query = entityManager.createNativeQuery(sql.toString(), Product.class);
//
//            products = query.getResultList();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return products;
//    }

    private List<Product> getProductWithUiInfo() {
        Query query;
        List<Product> products = null;
        StringBuilder sql = new StringBuilder("select id, name, owner, manager, qa_lead, dev_lead from product p where 1=1 ");

        try {

        } catch (Exception e) {
            e.printStackTrace();

        }

        return products;
    }

    private List<Product> getProductWithUiInfoByPage() {
        Query query;
        List<Product> products = null;

        try {

        } catch (Exception e) {
            e.printStackTrace();

        }

        return products;
    }

}
