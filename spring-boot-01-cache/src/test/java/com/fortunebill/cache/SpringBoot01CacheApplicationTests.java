package com.fortunebill.cache;

import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.entities.EmployeeExample;
import com.fortunebill.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01CacheApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void test() {
        EmployeeExample example = new EmployeeExample();
        List<Employee> employees = employeeMapper.selectByExample(example);
        System.out.println(employees);
    }

}
