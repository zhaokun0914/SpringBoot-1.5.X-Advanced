package com.fortunebill.cache.service.impl;

import com.fortunebill.cache.entities.Department;
import com.fortunebill.cache.mapper.DepartmentMapper;
import com.fortunebill.cache.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * @author Kavin
 * @date 2021年11月19日12:36
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;

    /*@Override
    @Cacheable(cacheNames = {"dept"}, cacheManager = "deptCacheManager")
    public Department getDepartmentById(Integer id) {
        System.out.println("查询【" + id + "】号部门");
        return departmentMapper.selectByPrimaryKey(id);
    }*/

    /**
     * 编码的方式放入缓存中，使用缓存管理器直接操作就行了
     *
     * @param id id
     * @return 返回查询到的对象
     */
    @Override
    public Department getDepartmentById(Integer id) {
        System.out.println("查询【" + id + "】号部门");
        Department department = departmentMapper.selectByPrimaryKey(id);
        redisCacheManager.getCache("dept").put(department.getId(), department);
        return department;
    }

}
