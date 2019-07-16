package com.symbio.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 此类为用于每次返回时的错误代码以及错误信息提示
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
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

    public Result(String code, String message){
        this.ec = code;
        this.em = message;
    }

    public Result(Object data){
        this.ec = "0";
        this.em = "";
        this.cd = data;
    }

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
     * @return boolean
     * @Author - Danny
     * @Description - 判断是否为成功信息
     * @Date - 2019/7/5
     * @Param - []
     */
    public boolean isSuccess() {
        return "0".equals(ec);
    }

    /**
     * 用于建立一个正确的结果集
     *
     * @param cd 返回对象
     */
    @Deprecated
    public void setCdAndRightEcAndEm(Object cd){
        setEc("0");
        setEm("");
        setCd(cd);
    }

}
