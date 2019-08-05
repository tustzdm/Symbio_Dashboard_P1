package com.symbio.dashboard.constant;

public class ProjectConst {
    // properties path
    public static final String PATH = "/jekins.properties";
    // auth key
    public static final String URL = "jenkins.service.url";
    public static final String USERNAME = "jenkins.service.username";
    public static final String PASSWORD = "jenkins.service.password";

    // job name
    public static final String JOBNAME = "jenkins.service.jobname";
    // view name
    public static final String VIEWNAME = "TEP";

    // parse xml
    public static final String PARAMETERDEFINITIONS = "//parameterDefinitions";
    public static final String STRINGPARAMETER = "hudson.model.StringParameterDefinition";
    public static final String BOOLEANPARAMETER = "hudson.model.BooleanParameterDefinition";
    public static final String CHOICEPARAMETER = "hudson.model.ChoiceParameterDefinition";
    public static final String JENKINS_FILE_PARAMETER = "hudson.model.FileParameterDefinition";

    //special param name
    public static final String REPORTFILENAME = "ReportFilename";
    public static final String TEST = "testName";
    public static final String TEST_LANG_LOCALE = "TEST_LANG_LOCALE";

    public static final String PIE_SCROLL_LEGEND_CHART = "/json/PieScrollLegendChart.json";
    public static final String BAR_Y_CATEGORY_STACK_CHART = "/json/BarYCategoryStackChart.json";
}