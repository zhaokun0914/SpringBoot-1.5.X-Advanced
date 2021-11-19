package com.fortunebill.cache.entities;

/**
 * @author Kevin
 * @date 2021年11月19日12:05
 */
public class Department {
    private Integer id;

    private String departmentname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }
}
