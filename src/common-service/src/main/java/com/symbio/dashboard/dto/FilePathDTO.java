package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.symbio.dashboard.constant.CommonDef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;

/**
 * 本类作为 Screen Shot 的 Path Util
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilePathDTO implements Serializable {

    private Integer companyId;
    private Integer productId;
    private Integer releaseId;
    private Integer testSetId;
    private Integer caseType;
    private String testCaseId;
    private String zipFile;
    private String locale;

    public FilePathDTO(Integer productId, Integer releaseId, Integer testSetId, Integer caseType, String testCaseId, String locale) {
        this.productId = productId;
        this.releaseId = releaseId;
        this.testSetId = testSetId;
        this.caseType = caseType;
        this.testCaseId = testCaseId;
        this.locale = locale;
    }

    private String getTestSetWorkPath() {
        StringBuffer sb = new StringBuffer();

        // {company}_{productId}/{releaseId}_{testSetId}_{caseType}/{testCaseId}/{locale}/screenshot/
        sb.append(String.format("%s_%d%s", getCompanySymbol(companyId), productId, File.separator))
                .append(String.format("%d_%d_%d%s", releaseId, testSetId, caseType, File.separator))
                .append(String.format("%s%s", testCaseId, File.separator))
                .append(String.format("%s%s", locale, File.separator));

        return sb.toString();
    }

    public String getScreenShotPath() {
        StringBuffer sb = new StringBuffer();

        sb.append(getTestSetWorkPath()).append(CommonDef.SCREENSHOT + File.separator);
        String filePath = sb.toString();
        filePath = filePath.replace(' ', '_');

        return filePath;
    }

    public String getSourceFilePath() {
        StringBuffer sb = new StringBuffer();

        sb.append(getTestSetWorkPath()).append(CommonDef.SOURCE + File.separator);
        String filePath = sb.toString();
        filePath = filePath.replace(' ', '_');

        return filePath;
    }

    public String getThumbnailPath() {
        StringBuffer sb = new StringBuffer();

        sb.append(getScreenShotPath()).append(CommonDef.THUMBNAIL + File.separator);
        String filePath = sb.toString();
        filePath = filePath.replace(' ', '_');

        return filePath;
    }

    public String getCompanySymbol(Integer companyId) {
        return (companyId == null) ? "0" : String.valueOf(companyId);
    }

}
