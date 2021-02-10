package com.hansda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hansda.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findEmployeeById(int id);
	
	List<Employee> findEmployeeByLocation(String location);
	
	List<Employee> findEmployeeByName(String name);
}
