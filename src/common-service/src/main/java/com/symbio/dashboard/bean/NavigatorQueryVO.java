package com.symbio.dashboard.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bug Search Object
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NavigatorQueryVO implements java.io.Serializable {

    // business info
    private Integer productId;
    private Integer releaseId;
    private Integer testSetId;

    private String locale;
    private String fields;

    // page info
    private Integer pageIndex;
    private Integer pageSize;

    public NavigatorQueryVO(String locale, String fields, Integer pageIndex, Integer pageSize) {
        this.locale = locale == null ? "en_US" : locale;
        this.fields = fields;
        this.pageIndex = pageIndex == null ? 0 : pageIndex;
        this.pageSize = pageSize == null ? 20 : pageSize;
        this.productId = null;
        this.releaseId = null;
        this.testSetId = null;
    }

    public NavigatorQueryVO(String locale, String fields, Integer productId, Integer releaseId, Integer testSetId) {
        this.locale = locale == null ? "en_US" : locale;
        this.fields = fields;
        this.productId = productId;
        this.releaseId = releaseId;
        this.testSetId = testSetId;
    }

    public NavigatorQueryVO(String fields, ListQueryVO query) {
        this.locale = query.getLocale() == null ? "en_US" : query.getLocale();
        this.fields = fields;
        this.productId = query.getProductId();
        this.releaseId = query.getReleaseId();
        this.testSetId = query.getTestSetId();

        Integer pageIndex = query.getPageIndex();
        Integer pageSize = query.getPageSize();
        this.pageIndex = pageIndex == null ? 0 : pageIndex;
        this.pageSize = pageSize == null ? 20 : pageSize;
    }

}
