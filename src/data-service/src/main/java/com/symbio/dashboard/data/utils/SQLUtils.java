package com.symbio.dashboard.data.utils;

import com.symbio.dashboard.bean.NavigatorQueryVO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.CommonUtil;

@SuppressWarnings("unchecked")
public class SQLUtils {

    public static String buildSql(EnumDef.DASHBOARD_PAGE page, NavigatorQueryVO queryVo) {
        if (CommonUtil.isEmpty(queryVo)) return "";

        String sql = "";
        switch (page) {
            default:
                break;
            case TEST_RESULT:
                sql = getResultReviewQuerySQL(queryVo);
            case BUGS_OVERVIEW:
                sql = getBugListQuerySQL(queryVo);
        }

        if (queryVo.getPageIndex() != null && queryVo.getPageSize() != null) {
            sql += String.format(" LIMIT %d,%d", queryVo.getPageIndex(), queryVo.getPageSize());
        }

        return sql;
    }

    public static String getResultReviewQuerySQL(NavigatorQueryVO queryVo) {
        return "";
    }

    public static String getBugListQuerySQL(NavigatorQueryVO queryVo) {
        if (CommonUtil.isEmpty(queryVo.getFields())) return "";
        /*
        SELECT
        ts.id,ts.name,
                bug.assignee,
                bug.bug_priority,bug.bug_type,bug.file_url,
                bug.id,bug.issue_category_id,bug.issue_reason_id,bug.thumbnail_url,bug.title,

                bug.verifier
        FROM bug_info bug
        INNER JOIN test_result tr ON bug.test_result_id = tr.id
        INNER JOIN test_run trun ON trun.id = tr.test_run_id
        INNER JOIN test_set ts on ts.id = trun.testset_id

        LEFT JOIN issue_category ic on ic.id = bug.issue_category_id
        LEFT JOIN issue_reason ir on ir.id = bug.issue_reason_id

        LEFT JOIN `user` us1 on us1.id = bug.assignee
        LEFT JOIN `user` us2 on us2.id = bug.verifier

        LEFT JOIN dictionary dic1 on dic1.`type` = 'BugPriority' AND dic1.`code` = bug.bug_priority
        LEFT JOIN dictionary dic2 on dic2.`type` = 'BugType' AND dic2.`code` = bug.bug_type

        -- WHERE ts.id = 1330
        ORDER BY ts.id, bug.id;
        */

        StringBuffer sb = new StringBuffer();
        String strFields = queryVo.getFields();
        Integer tsId = queryVo.getTestSetId();

        sb.append("SELECT ts.id,").append(strFields)
                .append(" FROM bug_info bug")
                .append(" INNER JOIN test_result tr ON bug.test_result_id = tr.id")
                .append(" INNER JOIN test_run trun ON trun.id = tr.test_run_id")
                .append(" INNER JOIN test_set ts on ts.id = trun.testset_id")
                .append(" LEFT JOIN issue_category ic on ic.id = bug.issue_category_id")
                .append(" LEFT JOIN issue_reason ir on ir.id = bug.issue_reason_id")
                .append(" LEFT JOIN `user` us1 on us1.id = bug.assignee")
                .append(" LEFT JOIN `user` us2 on us2.id = bug.verifier")
                .append(" LEFT JOIN dictionary dic1 on dic1.`type` = 'BugPriority' AND dic1.`code` = bug.bug_priority ")
                .append(" LEFT JOIN dictionary dic2 on dic2.`type` = 'BugType' AND dic2.`code` = bug.bug_type ");
        //.append("").append("").append("").append("").append("").append("").append("").append("").append("").append("").append("");

        if (!CommonUtil.isEmpty(tsId)) {
            sb.append(" WHERE ts.id = ").append(tsId);
        }
        sb.append(" ORDER BY ts.id, bug.id");

        return sb.toString();
    }
}
