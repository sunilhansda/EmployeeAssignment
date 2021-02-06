package com.hansda.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hansda.entity.Employee;
import com.hansda.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 
	 * @param employee
	 * @return
	 */
	public ResponseEntity<Object> addEmployee(Employee employee) {
		employeeRepository.save(employee);
		if (employeeRepository.existsById(employee.getId()))
			return ResponseEntity.accepted().body("Employee successfully added!");
		else
			return ResponseEntity.unprocessableEntity().body("Employee could not be added!");
	}

	/**
	 * 
	 * @return
	 */
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Employee getEmployee(int id) {
		if (employeeRepository.existsById(id))
			return employeeRepository.findById(id).get();
		else
			return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteEmployeeById(int id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			if (employeeRepository.existsById(id))
				return ResponseEntity.unprocessableEntity().body("Error deleting Employee");
			else
				return ResponseEntity.ok().body("Employee deleted");
		} else
			return ResponseEntity.unprocessableEntity().body("Wrong id");
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @return
	 */
	@Transactional
	public ResponseEntity<Object> updateEmployee(int id, String name, String email) {

		if (employeeRepository.existsById(id)) {

			Optional<Employee> newEmployee = employeeRepository.findEmployeeById(id);
			if (name != null && name.length() > 0 && !Objects.equals(name, newEmployee.get().getName())) {
				newEmployee.get().setName(name);
				System.out.println("name updated");
			}
			if (email != null && email.length() > 0 && !Objects.equals(email, newEmployee.get().getEmail())) {
				newEmployee.get().setEmail(email);
			}

		} else
			return ResponseEntity.badRequest().body("Id not found!");

		return ResponseEntity.ok().body("Data updated!");
	}
}
