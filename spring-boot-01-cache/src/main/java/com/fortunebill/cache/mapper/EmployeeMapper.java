package com.fortunebill.cache.mapper;

import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.entities.EmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kevin
 * @date 2021年11月19日12:05
 */
public interface EmployeeMapper {
    int countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}
