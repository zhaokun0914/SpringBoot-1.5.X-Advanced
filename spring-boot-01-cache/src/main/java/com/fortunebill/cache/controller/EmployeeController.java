package com.fortunebill.cache.controller;

import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kavin
 * @date 2021年11月19日12:39
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/query/{id}")
    public Employee get(@PathVariable("id") Integer id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/update")
    public Employee updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        return employee;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmployeeByLastName(lastName);
    }

}
