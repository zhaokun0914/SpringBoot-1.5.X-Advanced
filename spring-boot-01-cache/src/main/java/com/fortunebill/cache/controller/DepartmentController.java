package com.fortunebill.cache.controller;

import com.fortunebill.cache.entities.Department;
import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.service.DepartmentService;
import com.fortunebill.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kavin
 * @date 2021年11月19日12:39
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/dept/{id}")
    public Department get(@PathVariable("id") Integer id) {
        return departmentService.getDepartmentById(id);
    }
    /*
    @PostMapping("/update")
    public Department updateEmployee(Department department) {
        departmentService.updateEmployee(department);
        return employee;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        return departmentService.deleteEmployee(id);
    }

    @GetMapping("/{lastName}")
    public Department getEmpByLastName(@PathVariable("lastName") String lastName) {
        return departmentService.getEmployeeByLastName(lastName);
    }*/

}
