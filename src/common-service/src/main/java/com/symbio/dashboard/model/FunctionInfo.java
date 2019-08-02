package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
public class FunctionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "fvalue", nullable = false)
    private String fvalue;

    @Column(name = "description")
    private String description;

    @Column(name = "createTime")
    private Date  createTime;
}