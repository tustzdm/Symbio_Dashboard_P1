package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 Test Case 的UI 实列类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCaseListDTO extends CommonListDTO {

    private List<Map<String, Object>> listCaseType;

    public TestCaseListDTO(String locale, Integer pageIndex, Integer pageSize) {
        super(locale, pageIndex, pageSize);

        this.listCaseType = new ArrayList();
    }

}
