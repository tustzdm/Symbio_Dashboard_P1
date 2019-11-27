package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName - SettingExcelImport
 * @Description
 * @Date - 2019/8/12 15:19
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "setting_excel_import")
public class SettingExcelImport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "case_type", nullable = false)
    private String caseType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "idx", nullable = false)
    private Integer idx;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "formatter")
    private String formatter;

    @Column(name = "description")
    private String description;
}
