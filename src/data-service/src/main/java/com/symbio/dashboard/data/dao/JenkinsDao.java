package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.bean.JenkinsJobArgs;
import com.symbio.dashboard.business.JenkinsJobArgsFactory;
import com.symbio.dashboard.business.JenkinsJobParameterFactory;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.repository.JenkinsJobParameterRep;
import com.symbio.dashboard.data.repository.JenkinsRep;
import com.symbio.dashboard.data.repository.JenkinsServerInfoRep;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.model.JenkinsSvrInfo;
import com.symbio.dashboard.model.TestExecPlatform;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
@Slf4j
public class JenkinsDao {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private TestSetDao testSetDao;
    @Autowired
    private JenkinsRep jenkinsRep;
    @Autowired
    private JenkinsServerInfoRep jsiRep;
    @Autowired
    private JenkinsJobParameterRep jjpRep;

    /**
     * 根据 TestSetID 得到TEP List信息
     *
     * @param testSetId
     * @return
     */
    public Result<List<TestExecPlatform>> getTEPList(Integer testSetId) {
        Result<List<TestExecPlatform>> retResult = new Result<List<TestExecPlatform>>();
        Integer productId = 0, testSetType = 0;

        Integer type = testSetDao.getTestSetTypeById(testSetId);
        if (BusinessUtil.isIdEmpty(type)) {
            log.warn(String.format("Could not find exact Test Set data. TestSet Id = [%d]", testSetId));
            return commonDao.getResult("300301", testSetId);
        } else {
            testSetType = type;
        }

        String strTEPProductFlag = commonDao.getConfigValueByKey(ProjectConst.TEP_SPERATED_PRODUCT);

        try {
            EnumDef.TEP_SEPARATED_PRODUCT enumItem = EnumDef.getEnumTypeByCode(EnumDef.TEP_SEPARATED_PRODUCT.class, Integer.parseInt(strTEPProductFlag));
            switch (enumItem) {
                case NO:
                    productId = 0;
                    break;
                case YES:
                    productId = testSetDao.getProductIdByTestSetId(testSetId);
                    if (BusinessUtil.isIdEmpty(productId)) {
                        log.warn(String.format("Could not find exact Product data. TestSet Id = [%d]", testSetId));
                        return commonDao.getResult("200301", testSetId);
                    }
                    break;
            }
        } catch (Exception e) {
            //return commonDao.getResult("000015", "EnumDef.TEP_SEPARATED_PRODUCT", strTEPProductFlag);

            String strMsg = commonDao.getMessage(Locales.EN_US.toString(), "000015", "EnumDef.TEP_SEPARATED_PRODUCT", strTEPProductFlag);
            log.error(e.getMessage());
            log.error(strMsg);

            retResult.setEc("000015");
            retResult.setEm(strMsg);
            return retResult;
        }

        List<TestExecPlatform> retListTEP = getTEPList(strTEPProductFlag, productId, testSetType);
        if (CommonUtil.isEmpty(retListTEP)) {
            log.warn("Could not find TEP data.");
            log.warn(String.format("TEPProductFlag = %s, productId = %d, testSetType = %d", strTEPProductFlag, productId, testSetType));
        }
        retResult.setCd(retListTEP);

        return retResult;
    }

    public List<TestExecPlatform> getTEPList(String strTEPFlag, Integer productId, Integer testSetType) {
        List<TestExecPlatform> retList = new ArrayList<>();

        if (EnumDef.TEP_SEPARATED_PRODUCT.NO.toString().equals(strTEPFlag)) {
            retList = jenkinsRep.getCommonTEPSetting(testSetType);
        } else if (EnumDef.TEP_SEPARATED_PRODUCT.YES.toString().equals(strTEPFlag)) {
            retList = jenkinsRep.getTEPSettingByProductId(productId, testSetType);
            if (CommonUtil.isEmpty(retList)) {
                retList = jenkinsRep.getCommonTEPSetting(testSetType);
            }
        }
        return retList;
    }

    /**
     * Get Job Args List Info by TEP id
     *
     * @param tepId
     * @return
     */
    public Result<List<JenkinsJobArgs>> getJobArgsInfo(String locale, Integer tepId) {
        Result<List<JenkinsJobArgs>> retResult = new Result<>();

        List<JenkinsJobArgs> retList = new ArrayList<>();

        try {
            Integer jsiId = jenkinsRep.getJSIIdByTEPId(tepId);
            if (jsiId == null) {
                return commonDao.getResultArgs(locale, "300502", tepId);
            }

            JenkinsSvrInfo jsi = jsiRep.getById(jsiId);
//            List<JenkinsJobParameter> listJJP = jenkinsRep.getJobParameter(jsi.getId());
            List<JenkinsJobParameter> listJJP = jjpRep.fetchListByJSIId(jsi.getId());
            if (CommonUtil.isEmpty(listJJP)) {
                if (CommonUtil.isEmpty(jsi.getJobConfigXml())) {
                    String strJenkinsAutoFetch = commonDao.getConfigValueByKey(ProjectConst.JENKINS_FETCH_JOBARGS);
                    if (CommonUtil.isTRUEStr(strJenkinsAutoFetch)) {
                        log.debug("Try to fetch job args later due to the config xml is empty.");
                        Result specResult = commonDao.getResult("300503");
                        specResult.setCd(jsi);
                        return specResult;
                    }
                }
                retResult.setCd(retList);
            } else {
                String separatedSymbol = commonDao.getConfigValueByKey(ProjectConst.JENKINS_CHOICE_SEPARATED_SYMBOL);
                retList = JenkinsJobArgsFactory.convertToJJA(listJJP, separatedSymbol);
                retResult.setCd(retList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JenkinsDao.getJobArgsInfo() ERROR!!! " + e.getMessage());
            return commonDao.getResult("000102", "getting Job args info");
        }

        return retResult;
    }

    public Result saveJobParameters(Integer jsiId, List<JenkinsBean> listData) {
        Result retSaveOperation = new Result();

        try {
            JenkinsJobParameter jjp = null;
            Integer jjpIndex = 0;

            User user = new User();
            user.setId(1);
            user.setFullName("admin");

            for (JenkinsBean item : listData) {
                jjp = jjpRep.findByJsiIdAndParamName(jsiId, item.getName());
                if (jjp == null) {
                    jjp = JenkinsJobParameterFactory.createNewJJP(user, jsiId, item, jjpIndex++);
                    jjpRep.saveAndFlush(jjp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retSaveOperation;
    }


}
