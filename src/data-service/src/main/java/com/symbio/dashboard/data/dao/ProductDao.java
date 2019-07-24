package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserRep userRep;

    public List<Object> getFormatProductList() {

        return null;
    }

    public List<Product> getProductList() {
        Query query;
        StringBuilder sb = new StringBuilder();

        List<Object[]> products;
        List<Product> productList = new ArrayList<>();
        List<String> dbFields = new ArrayList<>();

        List<String> columns = new ArrayList<>();
        List<Integer> productIds = new ArrayList<>();

        List<Map<String, Object>> usersInfo;

        try {
            sb.append("select ");
            dbFields.add("id");

            List<UiInfo> uiInfoList = uiInfoRep.getUiInfoListByPageName("product");

            if (uiInfoList != null && uiInfoList.size() > 0) {
                for (int i = 0; i < uiInfoList.size(); i++) {
                    dbFields.add(uiInfoList.get(i).getDbField());
                    if (uiInfoList.get(i).getType().equalsIgnoreCase("user")) {
                        columns.add(uiInfoList.get(i).getDbField());
                    }
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

            productList = EntityUtils.castModel(products, Product.class, String.join(",", dbFields));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public List<Map<String, Object>> getProductUsers(List<Integer> productIds, List<String> columns) {

        List<Map<String, Object>> users = new ArrayList<>();

        try {

            for (int i = 0; i < productIds.size(); i++) {
                for (int j = 0; j < columns.size(); j++) {
                    users.add(getUserInfo(productIds.get(i), columns.get(j)));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        return users;
    }

    private Map<String, Object> getUserInfo(Integer productId, String column) {
        Query query;
        StringBuilder sb = new StringBuilder();
        List<User> users;
        Map<String, Object> userInfo = new HashMap<>();

        try {
            sb.append("select * from user u join product p on u.id=p.");
            sb.append(column);
            sb.append(" where p.id=");
            sb.append(productId);

            query = entityManager.createNativeQuery(sb.toString(), User.class);
            users = query.getResultList();

            if (users == null || users.isEmpty()) {
                userInfo.put("id", "");
                userInfo.put("name", "");
                userInfo.put("email", "");
            } else {
                User user = users.get(0);
                userInfo.put("id", user.getId());
                userInfo.put("name", user.getFullName());
                userInfo.put("email", user.getEmail());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    /**
     * Test Method for castModel()
     * @return
     */
    public List<Product> getProductListBySql() {
        List<Product> listProduct = new ArrayList<>();
        try {
            String sql = "SELECT qa_lead, owner,id,name FROM product LIMIT 0,3";

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            System.out.println(listResult);

            for(Object o : listResult){
                System.out.println(o);
            }

            Product objProdcut = new Product();

            listProduct = EntityUtils.castModel(listResult, Product.class, "qa_lead, owner,id,name");
            System.out.println(objProdcut);
            System.out.println(listProduct);

        } catch (Exception e) {
            e.printStackTrace();

            listProduct = null;
        }

        return listProduct;
    }

}
