package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

/**
 * 导航条testSet的data
 */

@Data
public class TestSetData {

    /**
     * testSet id
     */
    private Integer id;

    /**
     * 0-automation test
     * 1-manual test
     * 2-api test
     * 3-performance test
     */
    private Integer type;

    /**
     * testset name
     */
    private String name;
}
