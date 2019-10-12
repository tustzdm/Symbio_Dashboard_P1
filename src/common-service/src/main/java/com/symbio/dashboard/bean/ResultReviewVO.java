package com.symbio.dashboard.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Page Object
 *
 * @author Shawn
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultRevieVO implements java.io.Serializable {


    private Integer step;

//    @Override
//    public String toString() {
//        return new StringBuffer().append("token = ").append(token)
//                .append(", locale = ").append(locale)
//                .append(", userId = ").append(userId)
//                .append(", productId = ").append(productId)
//                .append(", releaseId = ").append(releaseId)
//                .append(", testSetId = ").append(testSetId)
//                .append(", pageIndex = ").append(pageIndex)
//                .append(", pageSize = ").append(pageSize)
//                .toString();
//    }
}
