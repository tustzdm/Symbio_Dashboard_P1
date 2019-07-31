package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.ReleaseRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.dto.ReleaseUiDTO;
import com.symbio.dashboard.entity.Progress;
import com.symbio.dashboard.enums.DictionaryType;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.enums.UIInfoPage;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
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
 * @ClassName - ReleaseDao
 * @Author - Admin
 * @Description
 * @Date - 2019/7/26
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Repository
public class ReleaseDao {

    private static Logger logger = LoggerFactory.getLogger(ReleaseDao.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private UiInfoRep uiInfoRep;
    @Autowired
    private ReleaseRep releaseRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysListSettingRep sysListSettingRep;

    @Autowired
    private ProductDao productDao;


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
            List<SysListSetting> listSetting = sysListSettingRep.getStatisticsInfo(SystemListSetting.Release.toString());
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

    /**
     * 得到Release List Map数据对象
     *
     * @param strFields
     * @param pageIndex
     * @param pageSize
     * @param listUserFields
     * @return
     */
    public Result getReleaseMapInfoByField(String strFields, Integer productId, Integer pageIndex, Integer pageSize, List<String> listUserFields) {
        Result retResult = null;

        try {
            CommonListDTO retListDTO = new CommonListDTO();
            List<Map<String, Object>> listRelease = new ArrayList<Map<String, Object>>();

            String sql = String.format("SELECT %s FROM `release` WHERE product_id = %d and display = 1 ORDER by id",  strFields, productId);
            if (pageIndex != null && pageSize != null) {
                sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
            }

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listRelease = EntityUtils.castMap(listResult, Release.class, strFields);
                List<Map<String, Object>> listProdInfo = mergeStaticticsData(listRelease);
                retListDTO.setFields(CommonUtil.getListByMergeString(strFields, "progress"));
                retListDTO.setDataType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    retListDTO.setData(listProdInfo);
                } else {
                    listProdInfo = commonDao.setUserMapInfo(listProdInfo, listUserFields);
                    retListDTO.setData(listProdInfo);
                }
            } else if (dataType == ListDataType.JSONArray) {

            }

            int nCount = releaseRep.getProductCount(productId);
            retListDTO.setTotalRecord(nCount);

            retResult = new Result(retListDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception happened while invoking ProductDao.getProductInfoByField()", e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }

    /**
     * @param locale
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result getReleaseList(String locale, Integer productId, Integer pageIndex, Integer pageSize) {
        logger.trace("ReleaseDao.getReleaseList() Enter.");
        logger.trace(String.format("Args: locale = %s, productId = &d, pageIndex = %d, pageSize = %d", locale, productId, pageIndex, pageSize));

        CommonListDTO retProdDTO = new CommonListDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(retProdDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfo(SystemListSetting.Release.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        }

        // Get Columns Info
        List<SysListSetting> listColumns = sysListSettingRep.getListColumnsInfo(SystemListSetting.Release.toString());
        if (CommonUtil.isEmpty(listColumns)) {
            return new Result("000121", "List columns info is empty");
        } else {
            retProdDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listColumns));
        }

        List<String> listAppend = new ArrayList<>();
        listAppend.add("product_id");
        List<String> listFields = CommonDao.getQueryFieldsAppend(listSetting, listAppend);
        if (CommonUtil.isEmpty(listFields)) {
            logger.debug("There is no field to query");
            return retResult;
        }

        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);

        String strFields = String.join(",", listFields);
        Result retReleaseResult = getReleaseMapInfoByField(strFields, productId, pageIndex, pageSize, listUserFields);
        if (retReleaseResult.hasError()) {
            retResult = retReleaseResult;
        } else {
            CommonListDTO retProduct = (CommonListDTO) retReleaseResult.getCd();
            retProdDTO.setTotalRecord(retProduct.getTotalRecord());
            retProdDTO.setFields(retProduct.getFields());
            retProduct.setDataType(retProduct.getDataType());
            retProdDTO.setData(retProduct.getData());
            retResult = new Result(retProdDTO);
        }

        logger.trace("ReleaseDao.getReleaseList() Exit");
        return retResult;
    }

    /**
     * 得到 Release 导航条信息
     *
     * @param locale
     * @param total
     * @return
     */
    public Result getNavigationList(String locale, Integer productId, Integer total) {
        Result retResult = null;

        try {
            int nFetchDataMode = 1; // 1 - Repository Access， 2 - castMap

            if (nFetchDataMode == 1) {
                List<Release> listRelease = null;
                if (BusinessUtil.isIdEmpty(total)) {
                    listRelease = releaseRep.getNavigationList(productId);
                } else {
                    listRelease = releaseRep.getNavigationPageList(productId, total);
                }

                if (CommonUtil.isEmpty(listRelease)) {
                    return new Result(new ArrayList<>());
                }

                List<Map<String, Object>> retList = new ArrayList<>();
                for (Release item : listRelease) {
                    retList.add(BusinessUtil.getReleaseUIListInfo(item));
                }

                retResult = new Result(retList);
            } else {
                String strFields = "id,name";
                String sql = String.format("SELECT %s FROM `release` WHERE product_id = %d AND display = 1 ORDER BY update_time DESC", strFields, productId);
                if (!BusinessUtil.isIdEmpty(total)) {
                    sql += String.format(" LIMIT 0,%d", total);
                }

                // Fetch db
                List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
                if (CommonUtil.isEmpty(listResult)) {
                    return new Result(new ArrayList<>());
                }
                // Change to Map
                List<Map<String, Object>> listRelease = EntityUtils.castMap(listResult, Release.class, strFields);
                retResult = new Result(listRelease);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale, "000102", "getting Release navigation info");
        }

        return retResult;
    }

    public Result getReleaseUiInfo(Integer userId, String locale, Integer uiInfo, Integer id) {
        logger.trace("ReleaseDao.getReleaseUiInfo() Enter");
        Result result;

        try {
            ReleaseUiDTO releaseUiDTO = new ReleaseUiDTO();
            List<UiInfo> listUiInfo = new ArrayList<>();
            if (uiInfo == 1) {
                listUiInfo = commonDao.getUIInfoByPage(UIInfoPage.Release.toString());
            }
            releaseUiDTO.setLocale(locale);
            releaseUiDTO.setRole(7);
            releaseUiDTO.setUiInfo(commonDao.getUiInfoList(locale, UIInfoPage.Release.toString(), listUiInfo));

            if (id != null && id > 0) { // Not add
                Release release = releaseRep.getById(id);
                if (release == null || "".equals(release)) {
                    logger.error("Could not find product data info.");
                    releaseUiDTO.setData(null);
                } else {
                    Map<String, Object> mapEntityData = EntityUtils.castMap(release, listUiInfo);
                    mapEntityData.put("productId", release.getProductId());
                    releaseUiDTO.setData(mapEntityData);
                }
            }

            releaseUiDTO.setProductList(productDao.getProductMapList());
            result = new Result(releaseUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result("000102", "Release UI Info");
        }

        logger.trace("ProductDao.getProductUiInfo() Exit");
        return result;
    }

    public List<Map<String, Object>> getReleaseMapList() {
        List<Map<String, Object>> retList = new ArrayList<>();
        List<Release> listRelease = releaseRep.findAllRelease();

        for(Release item: listRelease) {
            retList.add(BusinessUtil.getReleaseUIListInfo(item));
        }
        return retList;
    }

}
