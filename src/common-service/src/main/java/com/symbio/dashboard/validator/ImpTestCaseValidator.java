package com.symbio.dashboard.validator;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class ImpTestCaseValidator implements java.io.Serializable {

    private static final long serialVersionUID = 2035013017139483936L;

    private String token;
    private String locale;
    private String testSetId;
    private String fileName;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
