package com.lzk.democoreserver.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Users implements Serializable {

    private Long id;

    private String loginName;

    private String password;
}
