package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**F
 * @ClassName - FunctionInfo
 * @Author -
 * @Description - 此类是数据库[function_info]的实体类
 * @Date - 2019/8/2
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "function_info")
public class FunctionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "fvalue", nullable = false)
    private String fvalue;

    @Column(name = "description")
    private String description;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "createTime")
    private Date  createTime;
}