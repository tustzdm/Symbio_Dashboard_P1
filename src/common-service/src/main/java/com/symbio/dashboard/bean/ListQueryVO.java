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
