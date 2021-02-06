package com.hansda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hansda.entity.Employee;
import com.hansda.exception.IdNotFoundException;
import com.hansda.exception.ListEmptyException;
import com.hansda.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(method = RequestMethod.POST, value = "/employees/add")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
		return employeeService.addEmployee(employee);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public List<Employee> getAllEmployees() {
		List<Employee> employeesList = employeeService.getAllEmployees();
		if (employeesList.isEmpty())
			throw new ListEmptyException("List is empty, please add some employees");

		return employeesList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees/{id}")
	public Employee getEmployee(@PathVariable int id) {
		Employee employee = employeeService.getEmployee(id);
		if (employee == null)
			throw new IdNotFoundException("Id not found!");
		return employee;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/employees/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
		return employeeService.deleteEmployeeById(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employees/update/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestParam(required = false) String name,
			@RequestParam(required = false) String email) {
		return employeeService.updateEmployee(id, name, email);
	}
}
