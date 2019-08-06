package com.symbio.dashboard.constant;

public class ProjectConst {
    // properties path
    public static final String PATH = "/jekins.properties";

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

    // Project Config Key. Refer to Table [project_config]
    public static final String URL = "JenkinsServer.url";
    public static final String JENKINS_PORT = "JenkinsServer.port";
    public static final String USERNAME = "JenkinsServer.username";
    public static final String PASSWORD = "JenkinsServer.password";
    public static final String JOBNAME = "JenkinsServer.jobname";
}
