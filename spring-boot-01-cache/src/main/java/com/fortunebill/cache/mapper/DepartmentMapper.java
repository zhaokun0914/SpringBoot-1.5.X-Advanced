package com.fortunebill.cache.mapper;

import com.fortunebill.cache.entities.Department;
import com.fortunebill.cache.entities.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author Kevin
 * @date 2021年11月19日12:05
 */
public interface DepartmentMapper {
    int countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}
