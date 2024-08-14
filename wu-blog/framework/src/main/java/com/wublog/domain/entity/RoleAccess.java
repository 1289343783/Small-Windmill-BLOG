package com.wublog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName role_access
 */
@TableName(value = "role_access")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAccess implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限id
     */
    @TableId
    private Long accessId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}