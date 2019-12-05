package com.symbio.dashboard.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.util.CommonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bug Search Object
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListQueryVO implements java.io.Serializable {
    // Fix parameter
    private String token;
    private String locale;

    // business info
    private Integer productId;
    private Integer releaseId;
    private Integer testSetId;

    // page info
    private Integer pageIndex;
    private Integer pageSize;

    public ListQueryVO(String locale, Integer pageIndex, Integer pageSize) {
        this.locale = locale; // == null ? "en_US" : locale;
        this.pageIndex = pageIndex == null ? 0 : pageIndex;
        this.pageSize = pageSize == null ? 20 : pageSize;
    }

    public ListQueryVO(String token, String locale, Integer productId, Integer releaseId, Integer testSetId, Integer pageIndex, Integer pageSize) {
        this.token = token;
        this.locale = locale == null ? "en_US" : locale;
        this.productId = productId;
        this.releaseId = releaseId;
        this.testSetId = testSetId;
        this.pageIndex = pageIndex == null ? 0 : pageIndex;
        this.pageSize = pageSize == null ? 20 : pageSize;
    }

    public ListQueryVO(ListQueryVO query) {
        this.token = query.getToken();

        if (CommonUtil.isEmpty(query.getLocale())) {
            this.locale = Locales.EN_US.toString();
        }

        this.productId = query.getProductId();
        this.releaseId = query.getReleaseId();
        this.testSetId = query.getTestSetId();

        if (CommonUtil.isEmpty(query.getPageIndex())) {
            this.pageIndex = 0;
        }
        if (CommonUtil.isEmpty(query.getPageSize())) {
            this.pageSize = 20;
        }
    }

    @Override
    public String toString() {
        return new StringBuffer().append("token = ").append(token)
                .append(", locale = ").append(locale)
                .append(", productId = ").append(productId)
                .append(", releaseId = ").append(releaseId)
                .append(", testSetId = ").append(testSetId)
                .append(", pageIndex = ").append(pageIndex)
                .append(", pageSize = ").append(pageSize)
                .toString();
    }
}
