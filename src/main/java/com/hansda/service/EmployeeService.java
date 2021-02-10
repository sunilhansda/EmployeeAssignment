package com.hansda.service;

import java.util.List;

import com.hansda.entity.Employee;

public interface EmployeeService {

	public abstract Employee addEmployee(Employee employee);

	public abstract List<Employee> getAllEmployees();

	public abstract Employee getEmployee(int id);

	public abstract boolean deleteEmployeeById(int id);

	public abstract boolean updateEmployee(int id, String name, String email);
	
	public abstract List<Employee> findByLocation(String location);
	
	public abstract List<Employee> findByName(String name);
}
