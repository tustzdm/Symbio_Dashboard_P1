package com.symbio.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 本类作为数据库[result_message]表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "result_message")
public class ResultMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "en_us", nullable = false)
    private String enUs;

    @Column(name = "zh_cn", nullable = false)
    private String zhCn;

    @Column(name = "formatter")
    private String formatter;

    public String getFormatter(){
        return formatter == null ? "" : this.formatter;
    }
}
