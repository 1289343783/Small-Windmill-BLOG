package com.wublog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private Long id;

    private String userName;

    private String nickName;

    private String signature;

    private String avatar;

    private String sex;

    private Boolean isAdmin;

    private String email;

    private String phonenumber;

    /**
     * 用户类型：0 代表普通用户，1 代表管理员(只能有一个)
     */
    private String type;
}
