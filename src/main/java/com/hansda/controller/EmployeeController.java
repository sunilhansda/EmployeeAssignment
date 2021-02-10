package com.hansda.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hansda.entity.Employee;
import com.hansda.exception.NullReferenceException;
import com.hansda.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeService;
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/employees/add")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		logger.debug("In addEmployee method, calling service");
		employeeService.addEmployee(employee);
		logger.debug("Employee added successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Employee added!");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		logger.debug("In getAllEmployees method, calling service");
		List<Employee> empList = employeeService.getAllEmployees();
		if(empList.size()==0)
			throw new NullReferenceException("List is empty, add some employees");
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(empList);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
		logger.debug("In getEmployee method, calling service");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(employeeService.getEmployee(id));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/employees/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		logger.debug("In deleteEmployee method, calling service");
		employeeService.deleteEmployeeById(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Employee deleted");
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employees/update/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestParam(required = false) String name,
			@RequestParam(required = false) String email) {
		logger.debug("In updateEmployee method, calling service");
		employeeService.updateEmployee(id, name, email);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Employee updated!");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/employees/location/{location}")
	public ResponseEntity<List<Employee>> findByLocation(@PathVariable String location){
		logger.debug("In findByName method, calling service");
		List<Employee> empList = employeeService.findByLocation(location);
		if(empList.size() == 0) {
			logger.debug("List is empty");
			throw new NullReferenceException("List is empty");
		}
		logger.debug("Emp. list fetched");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(empList);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/employees/name/{name}")
	public ResponseEntity<List<Employee>> findByName(@PathVariable String name){
		logger.debug("In findByName method, calling service");
		List<Employee> empList = employeeService.findByName(name);
		if(empList.size() == 0) {
			logger.debug("List is empty");
			throw new NullReferenceException("List is empty");
		}
		logger.debug("Emp. list fetched");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(empList);
	}
}
