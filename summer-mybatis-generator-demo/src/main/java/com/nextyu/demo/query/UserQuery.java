package com.nextyu.demo.query;

import lombok.Data;

@Data
public class UserQuery {
    private Integer pageNum = 1;

    private Integer pageSize = 10;
}