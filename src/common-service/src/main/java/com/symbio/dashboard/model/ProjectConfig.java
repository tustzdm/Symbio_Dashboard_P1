package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**F
 * @ClassName - ProjectConfig
 * @Author -
 * @Description - 此类是数据库[project_config]的实体类
 * @Date - 2019/8/2
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "project_config")
public class ProjectConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "configName", nullable = false)
    private String configName;

    @Column(name = "configValue", nullable = false)
    private String configValue;

    @Column(name = "description")
    private String description;
}