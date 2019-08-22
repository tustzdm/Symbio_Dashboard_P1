package com.symbio.dashboard.dto;

import com.symbio.dashboard.model.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName - TestRunExcelDTO
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/8/21 17:58
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunExcelDTO implements Serializable {

    private TestCase testCase;

    // For Automation
    private String locale;
}
