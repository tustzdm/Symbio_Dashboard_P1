package com.symbio.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 此类为用于每次返回时的错误代码以及错误信息提示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    /**
     * 错误代码
     */
    private String ec;

    /**
     * 错误信息
     */
    private String em;

    /**
     * 当错误代码为0时，返回相应的类型信息
     */
    private Object cd = null;

    /**
     * 根据ec错误代码，返回是否有错误，如果有，则返回错误信息，如果没有，返回其他的cd内容
     *
     * @return 如果为true，则表示有错；如果false，则表示没错
     *
     */
    public boolean hasError(){
        return !"0".equals(ec);
    }

    /**
     * 用于建立一个正确的结果集
     *
     * @param cd 返回对象
     */
    public void setCdAndRightEcAndEm(Object cd){
        setEc("0");
        setEm("successful");
        setCd(cd);
    }




}
