package com.nus.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String consignee;

    private String phone;

    private String sex;

    private String postalCode;

    private String detailLocation;

    private String label;

    private Integer isDefault;
}
