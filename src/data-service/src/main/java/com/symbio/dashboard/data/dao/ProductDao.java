package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
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
    private UserDao userDao;
    @Autowired
    private SysListSettingRep sysListSettingRep;

    public Map<String, Object> getFormatProductList() {
        Query query;
        StringBuilder sb = new StringBuilder();

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> columns = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<Object> data = new ArrayList<>();

        SysListSetting sysListSetting;

        try {
            sb.append("select s.* from sys_list_setting s where s.field = 'id' " +
                    "union " +
                    "select s.* from sys_list_setting s join ui_info u on s.field = u.db_field where s.name = 'product' and u.display = 1");
            query = entityManager.createNativeQuery(sb.toString(), SysListSetting.class);
            List<SysListSetting> resultList = query.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    sysListSetting = resultList.get(i);
                    //Get fields
                    fields.add(sysListSetting.getField());
                    //Get columns
                    Map<String, Object> columnMap = new HashMap<>();
                    columnMap.put("key", sysListSetting.getKey());
                    columnMap.put("label", sysListSetting.getLabel());
                    columnMap.put("type", sysListSetting.getType());
                    columnMap.put("align", sysListSetting.getAlign());
                    columnMap.put("field", sysListSetting.getField());
                    columnMap.put("formatter", sysListSetting.getFormatter());

                    columns.add(columnMap);
                }
                sb.delete(0, sb.length());
                if (fields != null && !fields.isEmpty()) {
                    sb.append("select ");
                    for (String s : fields) {
                        sb.append(s).append(",");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    sb.append(" from product where 1=1");
                    query = entityManager.createNativeQuery(sb.toString());
                    data = query.getResultList();
                }


            }
            resultMap.put("columns", columns);
            resultMap.put("fields", fields);
            resultMap.put("data", data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    public List<Product> getProductList() {
        Query query;
        StringBuilder sb = new StringBuilder();

        List<Object[]> products;
        List<Product> productList = new ArrayList<>();
        List<SysListSetting> sysList;
        List<String> dbFields = new ArrayList<>();

        try {
            sb.append("select ");

            sysList = sysListSettingRep.getDbFieldsInProduct();

            if (sysList != null && sysList.size() > 0) {

                dbFields.add("id");
                for (int i = 0; i < sysList.size(); i++) {
                    dbFields.add(sysList.get(i).getField());
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
     *
     * @return
     */
    public List<Product> getProductListBySql() {
        List<Product> listProduct = new ArrayList<>();
        try {
            String sql = "SELECT qa_lead, owner,id,name FROM product LIMIT 0,3";

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            System.out.println(listResult);

            for (Object o : listResult) {
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


        try {
            // ToDo: Product - Get actrual List statistics column info
            List<SysListSetting> listSetting = sysListSettingRep.getStatisticsInfo(SystemListSetting.Product.toString());
            if (CommonUtil.isEmpty(listSetting)) {
                return retMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Progress progress;
        Random random = new Random();
        for (Map item : retMap) {
            int total = random.nextInt(500);
            int done = random.nextInt(500);
            if (done > total) done = total;
            progress = new Progress(done, total);
            item.put("progress", progress);
        }
        return retMap;
    }

    private List<String> getFieldInfoByMap(List<Map<String, Object>> data) {
        List<String> listFields = new ArrayList<String>();

        if (!CommonUtil.isEmpty(data)) {
            Map<String, Object> map = data.get(0);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            System.out.println("keySet: " + map.keySet().toString());
        }

        return listFields;
    }

    private List<Map<String, Object>> setUserMapInfo(List<Map<String, Object>> data, List<String> listUserFields) {
        List<Map<String, Object>> retList = data;

        try {
            if (CommonUtil.isEmpty(listUserFields) || CommonUtil.isEmpty(data)) {
                return data;
            }

            Set<Integer> userIds = new TreeSet<>();

            // Check map key is match
            Map<String, Object> mapTest = data.get(0);
            CommonUtil.validateMapKey(mapTest, listUserFields);

            for (Map map : data) {
                for (String field : listUserFields) {
                    userIds.add((Integer) map.get(field));
                }
            }

            // Find User infos
            List<Integer> listUsers = new ArrayList<>(userIds);
            String inIds = CommonUtil.join(listUsers);
            List<User> listUserInfo = userDao.getUserByIds(inIds);
            System.out.println("ids == " + inIds);
            System.out.println("Find user length == " + listUserInfo.size());

            if (CommonUtil.isEmpty(listUserInfo)) {
                return data;
            }

            Map<Integer, User> mapUsers = new HashMap<>();
            for (User user : listUserInfo) {
                mapUsers.put(user.getId(), user);
            }

            // Get User Map
            retList = BusinessUtil.ReplaceUserShortInfo(data, listUserFields, mapUsers);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return retList;
    }

    /**
     * 得到Product List Map数据对象
     *
     * @param strFields
     * @param pageIndex
     * @param pageSize
     * @param listUserFields
     * @return
     */
    public Result getProductMapInfoByField(String strFields, Integer pageIndex, Integer pageSize, List<String> listUserFields) {
        Result retResult = null;

        try {
            CommonListDTO retProduct = new CommonListDTO();
            List<Map<String, Object>> listProduct = new ArrayList<Map<String, Object>>();

            String sql = String.format("SELECT %s FROM product WHERE display = 1 ORDER by id", strFields);
            if (pageIndex != null && pageSize != null) {
                sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
            }

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();

            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listProduct = EntityUtils.castMap(listResult, Product.class, strFields);
                List<Map<String, Object>> listProdInfo = mergeStaticticsData(listProduct);
                retProduct.setFields(CommonUtil.getListByMergeString(strFields, "progress"));
                retProduct.setDateType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    retProduct.setData(listProdInfo);
                } else {
                    listProdInfo = setUserMapInfo(listProdInfo, listUserFields);
                    retProduct.setData(listProdInfo);
                }
            } else if (dataType == ListDataType.JSONArray) {

            }

            int nCount = productRep.getCount();
            retProduct.setTotalRecord(nCount);

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
     *
     * @param listSetting
     * @return
     */
    private List<String> getQueryFields(List<SysListSetting> listSetting) {
        List<String> dbFields = new ArrayList<>();

        boolean bFindId = false;

        for (SysListSetting item : listSetting) {
            if (!StringUtil.isEmpty(item.getField())) {
                dbFields.add(item.getField());
                if (!bFindId && item.getField().contains("id")) {
                    bFindId = true;
                }
            }
        }

        if (!bFindId) dbFields.add(0, "id");

        return dbFields;
    }

    /**
     * @param locale
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result getProductList2(String locale, Integer pageIndex, Integer pageSize) {
        logger.trace("ProductDao.getProductList2() Enter.");
        logger.trace(String.format("Args: locale = %s, pageIndex = %d, pageSize = %d", locale, pageIndex, pageSize));

        CommonListDTO retProdDTO = new CommonListDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(retProdDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfo(SystemListSetting.Product.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        }

        // Get Columns Info
        List<SysListSetting> listColumns = sysListSettingRep.getListColumnsInfo(SystemListSetting.Product.toString());
        if (CommonUtil.isEmpty(listColumns)) {
            return new Result("000121", "List columns info is empty");
        } else {
            retProdDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listColumns));
        }

        List<String> listFields = CommonDao.getQueryFields(listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            logger.debug("There is no field to query");
            return retResult;
        }

        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);

        String strFields = String.join(",", listFields);
        Result retProductResult = getProductMapInfoByField(strFields, pageIndex, pageSize, listUserFields);
        if (retProductResult.hasError()) {
            retResult = retProductResult;
        } else {
            CommonListDTO retProduct = (CommonListDTO) retProductResult.getCd();
            retProdDTO.setTotalRecord(retProduct.getTotalRecord());
            retProdDTO.setFields(retProduct.getFields());
            retProduct.setDateType(retProduct.getDateType());
            retProdDTO.setData(retProduct.getData());
            retResult = new Result(retProdDTO);
        }

        logger.trace("ProductDao.getProductList2() Exit");
        return retResult;
    }

    /**
     * 得到Product 导航条信息
     *
     * @param locale
     * @param total
     * @return
     */
    public Result getNavitionList(String locale, Integer total) {
        Result retResult = new Result("");

        try {
            int nFetchDataMode = 2; // 1 - Product， 2-Map

            if (nFetchDataMode == 1) {
                List<Product> listProduct = null;
                if (total == null || total < 1) {
                    listProduct = productRep.findNavigationList();
                } else {
                    listProduct = productRep.findNavigationPage(total);
                }

                if (CommonUtil.isEmpty(listProduct)) {
                    return new Result("000120", "Product Navigation");
                }
                retResult = new Result(listProduct);
            } else {
                String strFields = "id,name";
                String sql = String.format("SELECT %s FROM product WHERE display = 1 ORDER BY id", strFields);
                if (total != null && total > 0) {
                    sql += String.format(" LIMIT 0,%d", total);
                }
                // Fetch db
                List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
                if (CommonUtil.isEmpty(listResult)) {
                    return new Result("000120", "Product Navigation");
                }
                // Change to Map
                List<Map<String, Object>> listProduct = EntityUtils.castMap(listResult, Product.class, strFields);
                retResult = new Result(listProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retResult = new Result("000102", "Product Navigation");
        }

        return retResult;
    }

    @Data
    public class Progress {
        private int total;
        private int done;
        private String progress;

        public Progress(int done, int total) {
            this.done = done;
            this.total = total;
            if (total > 0) {
                this.progress = String.format("%d%%", Integer.valueOf(done * 100 / total));
            } else {
                this.progress = "";
            }
        }
    }
}
