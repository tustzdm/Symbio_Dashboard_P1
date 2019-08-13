package com.symbio.dashboard.jenkins;

import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.enums.JenkinsParameter;
import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class JenkinsParseUtil {

    private Short NODE_TYPE_ELEMENT = 1;

    public List<JenkinsBean> parse(String xml) {
        List<JenkinsBean> jenkinsBeans = null;
        if (!StringUtil.isEmpty(xml)) {
            InputStream in = IOUtils.toInputStream(xml);
            SAXReader reader = new SAXReader();
            jenkinsBeans = new ArrayList<>();
            try {
                Document doc = reader.read(in);
                Node nodeParams = doc.selectSingleNode(ProjectConst.PARAMETERDEFINITIONS);
                if (NODE_TYPE_ELEMENT == nodeParams.getNodeType()) {
                    Element eleParameterDefinitions = (Element) nodeParams;
                    List<Element> childElement = eleParameterDefinitions.elements();
                    for (Element e : childElement) {
                        System.out.println("Element Name = " + e.getName());
                        saveParamInfo(jenkinsBeans, e);
                    }
                } else {
                    throw new RuntimeException("Could not find element");
                }
            } catch (DocumentException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not parse the job's xml file");
            }
        }
        return jenkinsBeans;
    }

    private boolean isFixedParameter(String paramName) {
        return (ProjectConst.REPORTFILENAME.equals(paramName) || ProjectConst.TEST.equals(paramName)
                || ProjectConst.TEST_LANG_LOCALE.equals(paramName));
    }

    private boolean isSupportType(String typeName) {
        return (ProjectConst.STRINGPARAMETER.equals(typeName) || ProjectConst.BOOLEANPARAMETER.equals(typeName)
                || ProjectConst.JENKINS_FILE_PARAMETER.equals(typeName) || ProjectConst.CHOICEPARAMETER.equals(typeName));
    }

    @SuppressWarnings("unchecked")
    public void saveParamInfo(List<JenkinsBean> list, Element e) {
        if (!isSupportType(e.getName())) {
            return;
        }

        JenkinsBean bean = new JenkinsBean();
        if (ProjectConst.STRINGPARAMETER.equals(e.getName())) {
            bean.setType(JenkinsParameter.StringType.toString());
        } else if (ProjectConst.BOOLEANPARAMETER.equals(e.getName())) {
            bean.setType(JenkinsParameter.BooleanType.toString());
        } else if (ProjectConst.CHOICEPARAMETER.equals(e.getName())) {
            bean.setType(JenkinsParameter.ChoiceType.toString());
        } else {
            bean.setType(JenkinsParameter.FileType.toString());
            bean.setDefaultValue("--file0");
        }
        List<Element> ele = e.elements();
        for (Element t : ele) {
            switch (t.getName()) {
                case "name":
                    bean.setName(t.getStringValue());
                    if (isFixedParameter(t.getStringValue())) {
                        bean.setDelete(false);
                    }
                    break;
                case "description":
                    bean.setDescription(t.getStringValue());
                    break;
                case "defaultValue":
                    bean.setDefaultValue(t.getStringValue());
                    break;
                case "choices":
                    List<Element> alist = t.elements();
                    if (!alist.isEmpty()) {
                        Element aElement = alist.get(0);
                        List<Element> stringList = aElement.elements();
                        StringBuilder value = new StringBuilder();
                        for (Element str : stringList) {
                            value.append(str.getText()).append("\n");
                        }
                        bean.setDefaultValue(value.toString());
                    }
                    break;
            }
        }
        list.add(bean);
    }

    public String spliceJenkinsConfigXml(List<JenkinsJobParameter> params) {
        StringBuilder sb = new StringBuilder();
        if (!params.isEmpty()) {
            sb.append("<hudson.model.ParametersDefinitionProperty><parameterDefinitions>");
            for (JenkinsJobParameter param : params) {
                if (param.getRefType().equals(JenkinsParameter.BooleanType.toString())) {
                    sb.append("<hudson.model.BooleanTypeDefinition><name>").append(param.getParamName()).append("</name><description>").append(param.getDescription()).append("</description><defaultValue>").append(param.getDefaultValue()).append("</defaultValue></hudson.model.BooleanTypeDefinition>");
                }
                if (param.getRefType().equals(JenkinsParameter.StringType.toString())) {
                    sb.append("<hudson.model.StringParameterDefinition><name>").append(param.getParamName()).append("</name><description>").append(param.getDescription()).append("</description><defaultValue>").append(param.getDefaultValue()).append("</defaultValue></hudson.model.StringParameterDefinition>");
                }
                if (param.getRefType().equals(JenkinsParameter.ChoiceType.toString())) {
                    sb.append("<hudson.model.ChoiceParameterDefinition><name>").append(param.getParamName()).append("</name><description>").append(param.getDescription()).append("</description><choices class=\"java.util.Arrays\\$ArrayList\"><a class=\"string-array\">").append(param.getJenkinsDefaultValue()).append("</a></choices></hudson.model.ChoiceParameterDefinition>");
                }
            }
            sb.append("</parameterDefinitions></hudson.model.ParametersDefinitionProperty>");
        }
        return sb.toString();
    }


    public String updateJenkinsConfigxml(String jobXml, String updateXml) {
        Pattern p = Pattern
                .compile("<hudson.model.ParametersDefinitionProperty>([\\s\\S]*?)</hudson.model.ParametersDefinitionProperty>");
        Matcher m = p.matcher(jobXml);
        String pageBody = "";
        while (m.find()) {
            pageBody = m.group(1);
        }
        if (!StringUtil.isEmpty(pageBody)) {
            jobXml = jobXml.replaceAll("<hudson.model.ParametersDefinitionProperty>([\\s\\S]*?)</hudson.model.ParametersDefinitionProperty>", updateXml);
        } else {
            jobXml = jobXml.replaceAll("</hudson.security.AuthorizationMatrixProperty>", "</hudson.security.AuthorizationMatrixProperty>" + updateXml);
        }
        return jobXml;
    }

}
