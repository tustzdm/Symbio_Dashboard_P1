package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - Stat List
 * @Author - Shawn
 * @Description - stat_list 实体类
 * @Date - 2019/11/25
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "stat_list")
public class StatList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type_code")
    private Integer typeCode;

    @Column(name = "fk_id")
    private Integer fkId;

    @Column(name = "field")
    private String field;

    @Column(name = "value_type")
    private Integer valueType;

    @Column(name = "value_content")
    private String valueContent;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
