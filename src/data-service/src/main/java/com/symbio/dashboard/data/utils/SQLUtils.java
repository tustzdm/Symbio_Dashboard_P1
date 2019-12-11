package com.symbio.dashboard.data.utils;

import com.symbio.dashboard.bean.NavigatorQueryVO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.CommonUtil;

@SuppressWarnings("unchecked")
public class SQLUtils {

    public static String buildSql(EnumDef.SQL_TYPE sqlType) {
        String sql = "";
        switch (sqlType) {
            default:
                break;
            case STAT_PROGRESS_TEST_SET:
                sql = "SELECT ts.id, COUNT(if(tr.`status` != 0,true,null)) as done, count(tr.id) as total from test_run tr" +
                        " INNER JOIN test_set ts on ts.id = tr.testset_id" +
                        " INNER JOIN `release` rel on rel.id = ts.release_id" +
                        " INNER JOIN product prod on prod.id = rel.product_id" +
                        " WHERE prod.display = 1 AND prod.status != 3 " +
                        " GROUP BY ts.id";
                break;
            case STAT_PROGRESS_RELEASE:
                sql = "SELECT rel.id, COUNT(if(tr.`status` != 0,true,null)) as done, count(tr.id) as total from test_run tr" +
                        " INNER JOIN test_set ts on ts.id = tr.testset_id" +
                        " INNER JOIN `release` rel on rel.id = ts.release_id" +
                        " INNER JOIN product prod on prod.id = rel.product_id" +
                        " WHERE prod.display = 1 AND prod.status != 3 " +
                        " GROUP BY rel.id";
                ;
                break;
            case STAT_PROGRESS_PRODUCT:
                sql = "SELECT prod.id, COUNT(if(tr.`status` != 0,true,null)) as done, count(tr.id) as total from test_run tr" +
                        " INNER JOIN test_set ts on ts.id = tr.testset_id" +
                        " INNER JOIN `release` rel on rel.id = ts.release_id" +
                        " INNER JOIN product prod on prod.id = rel.product_id" +
                        " WHERE prod.display = 1 AND prod.status != 3 " +
                        " GROUP BY prod.id";
                break;
        }

        return sql;
    }

    public static String buildSql(EnumDef.CHARTS chart, NavigatorQueryVO queryVo) {
        String sql = "";
        switch (chart) {
            default:
                break;
            case BUGS_PIE:
                sql = getBugChartPieSQL(queryVo);
                break;
            case BUGS_BAR:
                sql = getBugChartBarSQL(queryVo);
                break;
        }

        return sql;
    }

    public static String buildSql(EnumDef.DASHBOARD_PAGE page, NavigatorQueryVO queryVo) {
        String sql = buildSqlBase(page, queryVo, true);

        // Add limitation
        if (queryVo.getPageIndex() != null && queryVo.getPageSize() != null) {
            sql += String.format(" LIMIT %d,%d", queryVo.getPageIndex(), queryVo.getPageSize());
        }
        return sql;
    }

    public static String buildSqlCount(EnumDef.DASHBOARD_PAGE page, NavigatorQueryVO queryVo) {
        String sql = buildSqlBase(page, queryVo, false);

        // Remove order clause
        String[] arrNewSql = sql.split("ORDER BY");
        if (arrNewSql != null && arrNewSql.length == 2) {
            sql = arrNewSql[0];
        }

        return sql;
    }

    public static String buildSqlBase(EnumDef.DASHBOARD_PAGE page, NavigatorQueryVO queryVo, boolean bOrderBy) {
        if (CommonUtil.isEmpty(queryVo)) return "";

        String sql = "";
        switch (page) {
            default:
                break;
            case EXECUTE_REVIEW:
                sql = getResultReviewQuerySQL(queryVo);
                break;
//            case TEST_RESULT:
//                sql = getResultReviewQuerySQL(queryVo);
//                break;
            case BUGS_OVERVIEW:
                sql = getBugListQuerySQL(queryVo, bOrderBy);
                break;
        }

        return sql;
    }

    public static String getResultReviewQuerySQL(NavigatorQueryVO queryVo) {
        if (CommonUtil.isEmpty(queryVo.getFields())) return "";

        StringBuffer sb = new StringBuffer();
        StringBuffer sbCondition = new StringBuffer();
        String strFields = queryVo.getFields();
        boolean bAddTableJenkinsJobHisMain = false;

        if (strFields.contains("jjhm.status")) {
            strFields = strFields.replace("jjhm.status", String.format("dicLocal.%s as 'jobStatus'", queryVo.getLocale().toLowerCase()));
            bAddTableJenkinsJobHisMain = true;
        }
        if (strFields.contains("jjhm.jobpath")) {
            strFields = strFields.replace("jjhm.jobpath", "IFNULL(jjhm.jobpath,'') as jobLink");
            bAddTableJenkinsJobHisMain = true;
        }

        if (bAddTableJenkinsJobHisMain) {
            sbCondition.append(" LEFT JOIN (SELECT testRunId, MAX(id) as id FROM jenkins_job_history_main")
                    .append(" WHERE testRunId is not null GROUP BY testRunId) jjh on jjh.testRunId = tr.id")
                    .append(" LEFT JOIN jenkins_job_history_main jjhm on jjhm.id = jjh.id");
            if (strFields.contains("jobStatus") || strFields.contains("dicLocal.")) {
                sbCondition.append(" LEFT JOIN dictionary_local dicLocal on dicLocal.name = 'JenkinsJobStatus' AND IFNULL(jjhm.`status`,0) = dicLocal.code AND dicLocal.validation = 1");
            }
        }

        sb.append("SELECT ").append(strFields)
                .append(" FROM test_run tr ")
                .append(" INNER JOIN test_case tc ON tr.testcase_id = tc.id ");

        // Add extra condition
        if (sbCondition.toString().length() > 0) {
            sb.append(sbCondition.toString());
        }

        // Where clause
        sb.append(" WHERE tr.display = 1 AND tr.validation = 1");
        if (!CommonUtil.isEmpty(queryVo.getTestSetId())) {
            sb.append(" AND tr.testset_id = ").append(queryVo.getTestSetId());
        }

        if (strFields.contains("tc.case_id") && strFields.contains("tr.locale")) {
            sb.append(" ORDER BY tc.case_id, tr.locale");
        } else {
            sb.append(" ORDER BY tr.id");
        }

        return sb.toString();
    }

//    public static String getResultReviewQuerySQL(NavigatorQueryVO queryVo, boolean bOrderBy) {
//        return "";
//    }

    public static String getBugListQuerySQL(NavigatorQueryVO queryVo, boolean bOrderBy) {
        if (CommonUtil.isEmpty(queryVo.getFields())) return "";

        StringBuffer sb = new StringBuffer();
        StringBuffer sbCondition = new StringBuffer();
        String strFields = queryVo.getFields();
        Integer tsId = queryVo.getTestSetId();

        if (strFields.contains("bug.status")) {
            strFields = strFields.replace("bug.status", String.format("dicLocal.%s as 'status'", queryVo.getLocale().toLowerCase()));
            sbCondition.append(" LEFT JOIN dictionary_local dicLocal on dicLocal.name = 'BugStatus' AND bug.status = dicLocal.code AND dicLocal.validation = 1");
        }
        if (strFields.contains("bug.assignee")) {
            strFields = strFields.replace("bug.assignee", "us1.full_name as assignee");
            sbCondition.append(" LEFT JOIN `user` us1 on us1.id = bug.assignee");
        }
        if (strFields.contains("bug.verifier")) {
            strFields = strFields.replace("bug.verifier", "us2.full_name as verifier");
            sbCondition.append(" LEFT JOIN `user` us2 on us2.id = bug.verifier");
        }

        if (strFields.contains("bug.issue_category_id")) {
            strFields = strFields.replace("bug.issue_category_id", "ic.category as issueCategory");
            sbCondition.append(" LEFT JOIN issue_category ic on ic.id = bug.issue_category_id");
        }
        if (strFields.contains("bug.issue_reason_id")) {
            strFields = strFields.replace("bug.issue_reason_id", "ir.reason as issueReason");
            sbCondition.append(" LEFT JOIN issue_reason ir on ir.id = bug.issue_reason_id");
        }

        if (strFields.contains("bug.bug_priority")) {
            strFields = strFields.replace("bug.bug_priority", "dic1.value as priority");
            sbCondition.append(" LEFT JOIN dictionary dic1 on dic1.`type` = 'BugPriority' AND dic1.`code` = bug.bug_priority");
        }
        if (strFields.contains("bug.bug_type")) {
            strFields = strFields.replace("bug.bug_type", "dic2.value as 'type'");
            sbCondition.append(" LEFT JOIN dictionary dic2 on dic2.`type` = 'BugType' AND dic2.`code` = bug.bug_type");
        }

        if (bOrderBy) {
            sb.append("SELECT ").append(strFields);
        } else {
            sb.append("SELECT COUNT(*)");
        }

        sb.append(" FROM bug_info bug")
                .append(" INNER JOIN test_result tr ON bug.test_result_id = tr.id")
                .append(" INNER JOIN test_run trun ON trun.id = tr.test_run_id")
                .append(" INNER JOIN test_set ts on ts.id = trun.testset_id");

        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id")
                    .append(" INNER JOIN product prod on prod.id = rel.product_id");
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id");
        }

        // Add extra condition
        if (sbCondition.toString().length() > 0) {
            sb.append(sbCondition.toString());
        }

        // Where clause
        sb.append(" WHERE 1=1");
        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" AND prod.id = ").append(queryVo.getProductId());
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" AND rel.id = ").append(queryVo.getReleaseId());
        } else if (!CommonUtil.isEmpty(tsId)) {
            sb.append(" AND ts.id = ").append(tsId);
        }

        if (bOrderBy) {
            sb.append(" ORDER BY ts.id, bug.id");
        }

        return sb.toString();
    }

    private static String getBugChartPieSQL(NavigatorQueryVO queryVo) {
        StringBuffer sb = new StringBuffer();
        Integer tsId = queryVo.getTestSetId();

        sb.append("SELECT dic1.value as priority, count(*) as count")
                .append(" FROM bug_info bug")
                .append(" INNER JOIN test_result tr ON bug.test_result_id = tr.id")
                .append(" INNER JOIN test_run trun ON trun.id = tr.test_run_id")
                .append(" INNER JOIN test_set ts on ts.id = trun.testset_id");

        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id")
                    .append(" INNER JOIN product prod on prod.id = rel.product_id");
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id");
        }

        // Add extra condition
        sb.append(" INNER JOIN dictionary dic1 on dic1.`type` = 'BugPriority' AND dic1.`code` = bug.bug_priority");
        // Where clause
        sb.append(" WHERE 1=1");
        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" AND prod.id = ").append(queryVo.getProductId());
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" AND rel.id = ").append(queryVo.getReleaseId());
        } else if (!CommonUtil.isEmpty(tsId)) {
            sb.append(" AND ts.id = ").append(tsId);
        }
        sb.append(" GROUP BY priority");

        return sb.toString();
    }

    private static String getBugChartBarSQL(NavigatorQueryVO queryVo) {
        StringBuffer sb = new StringBuffer();
        Integer tsId = queryVo.getTestSetId();

        String strStatusField = String.format("dicLocal.%s as 'status'", queryVo.getLocale().toLowerCase());

        // Build clause ...
        sb.append(String.format("SELECT dic1.value as priority, %s, count(*) as count", strStatusField))
                .append(" FROM bug_info bug")
                .append(" INNER JOIN test_result tr ON bug.test_result_id = tr.id")
                .append(" INNER JOIN test_run trun ON trun.id = tr.test_run_id")
                .append(" INNER JOIN test_set ts on ts.id = trun.testset_id");

        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id")
                    .append(" INNER JOIN product prod on prod.id = rel.product_id");
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" INNER JOIN `release` rel on rel.id = ts.release_id");
        }

        // Add extra condition
        sb.append(" INNER JOIN dictionary dic1 on dic1.`type` = 'BugPriority' AND dic1.`code` = bug.bug_priority")
                .append(" LEFT JOIN dictionary_local dicLocal on dicLocal.name = 'BugStatus' AND bug.status = dicLocal.code");

        // Where clause
        sb.append(" WHERE 1=1");
        if (!CommonUtil.isEmpty(queryVo.getProductId())) {
            sb.append(" AND prod.id = ").append(queryVo.getProductId());
        } else if (!CommonUtil.isEmpty(queryVo.getReleaseId())) {
            sb.append(" AND rel.id = ").append(queryVo.getReleaseId());
        } else if (!CommonUtil.isEmpty(tsId)) {
            sb.append(" AND ts.id = ").append(tsId);
        }
        sb.append(" GROUP BY priority, status");

        return sb.toString();
    }

}
