package com.symbio.dashboard.dao.impl;

import com.symbio.dashboard.dao.ProductDao;
import com.symbio.dashboard.dto.upload.GetProductListUpload;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.UiInfo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @ClassName - ProductDaoImpl
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/11 16:05
 * @Version 1.0
 */

@Service
public class ProductDaoImpl implements ProductDao {

    //    private StringBuilder sql = new StringBuilder("select * from product p where 1=1 ");
    private StringBuilder sql = new StringBuilder("select db_field from ui_info where `page` = 'product'");
    private StringBuilder sqlOrder = new StringBuilder(" order by p.id");

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getProductList(GetProductListUpload getProductListUpload) {
        return getProductFullList();
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

    private List<Product> getProductListByPage(GetProductListUpload getProductListUpload) {
        Query query;
        List<Product> products = null;
        Integer index = null;
        Integer size = null;

        try {

            index = getProductListUpload.getPageIndex();
            size = getProductListUpload.getPageSize();
            sql.append("limit ");
            sql.append((index - 1) * size);
            sql.append(", ");
            sql.append(size);
            sql.append(sqlOrder);

            query = entityManager.createNativeQuery(sql.toString(), Product.class);

            products = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

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
