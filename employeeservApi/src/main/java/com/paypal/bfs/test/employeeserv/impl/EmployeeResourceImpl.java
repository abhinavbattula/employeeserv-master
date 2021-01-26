package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeResourceService employeeResourceService;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        return employeeResourceService.employeeGetById(id);
    }

    @Override
    public ResponseEntity createEmployee(@RequestBody @Valid Employee employee) {
        return employeeResourceService.createEmployee(employee);
    }

    public EmployeeResourceService getEmployeeResourceService() {
        return employeeResourceService;
    }

    public void setEmployeeResourceService(EmployeeResourceService employeeResourceService) {
        this.employeeResourceService = employeeResourceService;
    }
}
