package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName - Dictionary
 * @Author - admin
 * @Description - 数据库dictionary表实体类
 * @Date - 2019/7/15 18:39
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "dictionary")
public class Dictionary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "`type`")
    private String type;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "description")
    private String description;

}
