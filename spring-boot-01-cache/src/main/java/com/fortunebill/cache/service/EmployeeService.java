package com.fortunebill.cache.service;

import com.fortunebill.cache.entities.Employee;

/**
 * @author Kevin
 * @date 2021年11月19日12:05
 */
public interface EmployeeService {

    public Employee getEmployee(Integer id);

    public Employee updateEmployee(Employee employee);

    public String deleteEmployee(Integer id);

    public Employee getEmployeeByLastName(String lastName);
}
