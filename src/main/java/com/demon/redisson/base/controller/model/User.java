package com.demon.redisson.base.controller.model;

import java.io.Serializable;

/**
 * @Description:
 * @Auther Demon
 * @Date 2017/12/14 17:50 星期四
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
