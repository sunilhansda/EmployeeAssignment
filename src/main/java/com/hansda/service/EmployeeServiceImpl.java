package com.hansda.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansda.entity.Employee;
import com.hansda.exception.DuplicateEntryException;
import com.hansda.exception.IdNotFoundException;
import com.hansda.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Override
	public Employee addEmployee(Employee employee) {
		List<Employee> employees = employeeRepository.findAll();
		for (Employee em : employees) {
			if (em.getName().equals(employee.getName()) && em.getEmail().equals(employee.getEmail())) {
				logger.debug("Cannot add employee, duplicate entry");
				throw new DuplicateEntryException("Duplicate entry");
			}
		}
		logger.debug("Employee persisted");
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployee(int id) {
		if (employeeRepository.existsById(id)) {
			logger.debug("Employee found");
			return employeeRepository.findById(id).get();
		} else {
			logger.debug("Employee doesn't exist with the id");
			throw new IdNotFoundException("Invalid id");
		}
	}

	@Override
	public boolean deleteEmployeeById(int id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			logger.debug("Employee deleted");
			return true;
		} else {
			logger.debug("Could not be deleted as no Employee exist with the id");
			throw new IdNotFoundException("Invalid id");
		}
	}

	@Override
	@Transactional
	public boolean updateEmployee(int id, String name, String email) {
		if (employeeRepository.existsById(id)) {
			logger.debug("Employee found");
			Optional<Employee> newEmployee = employeeRepository.findEmployeeById(id);
			if (name != null && name.length() > 0 && !Objects.equals(name, newEmployee.get().getName())) {
				newEmployee.get().setName(name);
				logger.debug("Name updated");
			}
			if (email != null && email.length() > 0 && !Objects.equals(email, newEmployee.get().getEmail())) {
				newEmployee.get().setEmail(email);
				logger.debug("Email updated");
			}

		} else {
			logger.debug("Could not be updated as no Employee exist with the id");
			throw new IdNotFoundException("Invalid id");

		}
		return true;
	}

	@Override
	public List<Employee> findByLocation(String location) {
		logger.debug("hitting repo");
		return employeeRepository.findEmployeeByLocation(location);
	}

	@Override
	public List<Employee> findByName(String name) {
		logger.debug("hitting repo");
		return employeeRepository.findEmployeeByName(name);
	}

}
