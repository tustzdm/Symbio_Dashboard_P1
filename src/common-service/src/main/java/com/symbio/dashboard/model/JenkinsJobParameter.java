package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - JenkinsJobParameter
 * @Description - jenkins_job_parameter实体类
 * @Date - 2019/8/5 15:18
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_job_parameter")
public class JenkinsJobParameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jsiId", nullable = false)
    private Integer jsiId;

    @Column(name = "paramName", nullable = false)
    private String paramName;

    @Column(name = "refType", nullable = false)
    private String refType;

    @Column(name = "choiceContent")
    private String choiceContent;

    @Column(name = "defaultValue")
    private String defaultValue;

    @Column(name = "lastRunValue")
    private String lastRunValue;

    @Column(name = "description")
    private String description;

    // JJP default display mode. 0-not display, 1-display
    @Column(name = "displayMode", nullable = false)
    private Integer displayMode;

    @Column(name = "idx", nullable = false)
    private Integer idx;

    // validation or not. 0-hidden ,1-show, 4-delete
    @Column(name = "validation", nullable = false)
    private Integer validation;

    // Fix user columns
    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId", nullable = false)
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

    @Column(name = "updateUserId")
    private Integer updateUserId;

    @Column(name = "updateUserName")
    private String updateUserName;

    public String getJenkinsDefaultValue() {
        StringBuffer sb = new StringBuffer();
        if (null != this.defaultValue && !"".equals(this.defaultValue)) {
            if (defaultValue.contains("\n")) {
                String arr[] = defaultValue.split("\n");
                for (int i = 0; i < arr.length; i++) {
                    sb.append("<string>").append(arr[i]).append("</string>");
                }
            } else {
                sb.append("<string>").append(defaultValue).append("</string>");
            }
        }
        return sb.toString();
    }

}
