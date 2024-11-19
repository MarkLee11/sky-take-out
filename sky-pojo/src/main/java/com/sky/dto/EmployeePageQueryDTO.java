package com.sky.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.springframework.context.annotation.Description;

import java.io.Serializable;

@Data
@Schema(description = "分页查询参数")
public class EmployeePageQueryDTO implements Serializable {

    @Schema(description = "姓名")
    //员工姓名
    private String name;

    @Schema(description = "页码")
    //页码
    private int page;

    @Schema(description = "每页记录数")
    //每页显示记录数
    private int pageSize;

}
