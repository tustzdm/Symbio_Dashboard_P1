package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.dto.ProductDTO;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import com.symbio.dashboard.util.StringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

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

    private static Logger logger = LoggerFactory.getLogger(ProductDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UiInfoRep uiInfoRep;
    @Autowired
    private ProductRep productRep;
    @Autowired
    private UserRep userRep;

    @Autowired
    private SysListSettingRep sysListSettingRep;

    public List<Object> getFormatProductList() {

        return null;
    }

    @Data
    public class Progress {
      private int total;
      private int done;
      private String progress;

      public Progress(int done, int total){
        this.done = done;
        this.total = total;
        if(total > 0) {
          this.progress = String.format("%d%%", Integer.valueOf(done * 100 / total));
        } else {
          this.progress = "";
        }
      }
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

    public List<Map<String, Object>> mergeStaticticsData(List<Map<String, Object>> entityMap) {
      List<Map<String, Object>> retMap = entityMap;

      Progress progress;
      Random random = new Random();
      for(Map item: retMap){
        int total = random.nextInt(500);
        int done = random.nextInt(500);
        if(done > total) done = total;
        progress = new Progress(done, total);
        item.put("progress" , progress);
      }
      return retMap;
    }

  public Result getProductMapInfoByField(String strFields, Integer pageIndex, Integer pageSize) {
    Result retResult = null;

    try {
      ProductDTO retProduct = new ProductDTO();
      List<Map<String, Object>> listProduct = new ArrayList<Map<String, Object>>();

      String sql = String.format("SELECT %s FROM product WHERE display = 1 ORDER by id", strFields);// LIMIT 0,3";
      if(pageIndex != null && pageSize != null) {
        sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
      }

      List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
      listProduct = EntityUtils.castMap(listResult, Product.class, strFields);

      int nCount = productRep.getCount();
      retProduct.setTotalRecord(nCount);

      List<Map<String, Object>> listProdInfo = mergeStaticticsData(listProduct);
      retProduct.setData(listProdInfo);
      retResult = new Result(retProduct);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Exception happened while invoking ProductDao.getProductInfoByField()", e);
      retResult = new Result("000002", e.getMessage());
    }

    return retResult;
  }

  /**
   * 得到dbFiled信息
   * @param listSetting
   * @return
   */
  private List<String> getQueryFields(List<SysListSetting> listSetting) {
    List<String> dbFields = new ArrayList<>();

    boolean bFindId = false;

    for(SysListSetting item : listSetting){
      if(!StringUtil.isEmpty(item.getField())) {
        dbFields.add(item.getField());
        if(!bFindId && item.getField().contains("id")) {
          bFindId = true;
        }
      }
    }

    if(!bFindId) dbFields.add(0, "id");

    return dbFields;
  }

  public Result getProductList2(String locale, Integer pageIndex, Integer pageSize) {
    logger.trace("ProductDao.getProductList2() Enter.");
    logger.trace(String.format("Args: locale = %s, pageIndex = %d, pageSize = %d", locale, pageIndex, pageSize));

    ProductDTO retProdDTO = new ProductDTO(locale, pageIndex, pageSize);
    Result retResult = new Result(retProdDTO);

    List<SysListSetting> listSetting = sysListSettingRep.getEntityInfo(SystemListSetting.Product.toString());
    if (CommonUtil.isEmpty(listSetting)) {
      return retResult;
    }

    List<String> listFields = getQueryFields(listSetting);
    if(CommonUtil.isEmpty(listFields)) {
      logger.debug("There is no field to query");
      return retResult;
    }

    Result retProductResult = getProductMapInfoByField(String.join(",", listFields), pageIndex, pageSize);
    if(retProductResult.hasError()) {
      retResult = retProductResult;
    } else {
      ProductDTO retProduct = (ProductDTO)retProductResult.getCd();
      retProdDTO.setTotalRecord(retProduct.getTotalRecord());
      retProdDTO.setData(retProduct.getData());
      retResult = new Result(retProdDTO);
    }

    logger.trace("ProductDao.getProductList2() Exit");
    return retResult;
  }

}
