package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.util.EntityUtils;
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
@SuppressWarnings("unchecked")
@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UiInfoRep uiInfoRep;
    @Autowired
    private ProductRep productRep;


    public List<Product> getProductList() {
        Query query;
        StringBuilder sb = new StringBuilder();

        List<Object[]> products;
        List<Product> productList = new ArrayList<>();
        List<String> dbFields = new ArrayList<>();

        try {
            sb.append("select ");

            List<UiInfo> uiInfoList = uiInfoRep.getUiInfoListByPageName("product");
            if (uiInfoList != null && uiInfoList.size() > 0) {
                for (int i = 0; i < uiInfoList.size(); i++) {
                    dbFields.add(uiInfoList.get(i).getDbField());
                }
                for (String s : dbFields) {
                    sb.append(s).append(",");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(" from product where 1=1");
            }

            query = entityManager.createNativeQuery(sb.toString());
            products = query.getResultList();

            productList = EntityUtils.castEntity(products, Product.class, new Product(), dbFields);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }


}
