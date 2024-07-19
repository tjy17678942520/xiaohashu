package com.quanxiaoha.xiaohashu.auth.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2024/5/5 15:21
 * @description: TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;
}
