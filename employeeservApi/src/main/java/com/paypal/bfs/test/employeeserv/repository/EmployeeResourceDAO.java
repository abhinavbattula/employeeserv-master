package com.paypal.bfs.test.employeeserv.repository;


import com.paypal.bfs.test.employeeserv.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeResourceDAO extends CrudRepository<Employee, Integer> {

}
