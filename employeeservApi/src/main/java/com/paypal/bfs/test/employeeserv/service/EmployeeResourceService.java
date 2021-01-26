package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeResourceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class EmployeeResourceService {

    @Autowired
    EmployeeResourceDAO employeeResourceDAO;

    public ResponseEntity<Employee> employeeGetById( String id) {
        Optional<Employee> employee = employeeResourceDAO.findById(Integer.valueOf(id));
        if (employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity("Employee Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
    };

    public ResponseEntity createEmployee(Employee employee) {

        if (StringUtils.isEmpty(employee.getAddress().getLine1()) ||
                StringUtils.isEmpty(employee.getAddress().getCity()) ||
                StringUtils.isEmpty(employee.getAddress().getState()) ||
                StringUtils.isEmpty(employee.getAddress().getCountry()) ||
                StringUtils.isEmpty(employee.getAddress().getZip_code())) {
            return new ResponseEntity<>("Employee is not created. Please check your request body",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            employeeResourceDAO.save(employee);
        } catch (Exception e) {
            return new ResponseEntity<>("Employee is not created. Please check your request body",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully created",
                HttpStatus.OK);
    };

    public EmployeeResourceDAO getEmployeeResourceDAO() {
        return employeeResourceDAO;
    }

    public void setEmployeeResourceDAO(EmployeeResourceDAO employeeResourceDAO) {
        this.employeeResourceDAO = employeeResourceDAO;
    }
}
