package com.paypal.bfs.test.employeeserv;

import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeResourceDAO;
import com.paypal.bfs.test.employeeserv.service.EmployeeResourceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ServiceLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EmployeeservFunctionalTests.config.class})
public class EmployeeservFunctionalTests {

    @Autowired
    private EmployeeResourceImpl employeeResource;

    private Employee employee;

    @Before
    public void setup() {
        Address address = new Address();
        address.setCity("San Diego");
        address.setCountry("Usa");
        address.setZip_code("92126");
        address.setLine1("line1");
        address.setLine2("line2");
        address.setState("CA");
        employee = new Employee();
        employee.setId(Integer.valueOf(1));
        employee.setDateOfBirth("someDate");
        employee.setFirstName("Abhinav");
        employee.setLastName("Battula");
        employee.setAddress(address);
    }

    @Test
    public void employeeGetByIdPositiveTest() {
        // given
        Mockito.when(employeeResource.getEmployeeResourceService().getEmployeeResourceDAO().findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
        // when
        ResponseEntity<Employee> employeeResponseEntity = employeeResource.employeeGetById("1");
        // then
        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.OK, employeeResponseEntity.getStatusCode());
    }
    @Test
    public void employeeGetByIdNegativeIdNotPresent() {
        // given
        Mockito.when(employeeResource.getEmployeeResourceService().getEmployeeResourceDAO().findById(Mockito.anyInt())).thenReturn(Optional.empty());
        // when
        ResponseEntity<Employee> employeeResponseEntity = employeeResource.employeeGetById("1");
        // then
        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee Id not found", employeeResponseEntity.getBody());
    }
    @Test
    public void createEmployeePositiveTest() {
        // given
        Mockito.when(employeeResource.getEmployeeResourceService().getEmployeeResourceDAO().save(Mockito.any())).thenReturn(employee);
        // when
        ResponseEntity<Employee> employeeResponseEntity = employeeResource.createEmployee(employee);
        // then
        Assert.assertNotNull(employeeResponseEntity);
        Assert.assertEquals(HttpStatus.OK, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Successfully created", employeeResponseEntity.getBody());
    }

    @Test
    public void createEmployeeNegativeTest() {
        // given
        Mockito.when(employeeResource.getEmployeeResourceService().getEmployeeResourceDAO().save(Mockito.any())).thenThrow(InternalServerErrorException.class);
        // when
        ResponseEntity<Employee> employeeResponseEntity = employeeResource.createEmployee(employee);
        // then
        Assert.assertEquals(HttpStatus.BAD_REQUEST, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee is not created. Please check your request body", employeeResponseEntity.getBody());
    }

    @Test
    public void createEmployeeNegativeTestWithBadRequest() {
        // given
        employee.getAddress().setState(null);
        Mockito.when(employeeResource.getEmployeeResourceService().getEmployeeResourceDAO().save(Mockito.any())).thenThrow(InternalServerErrorException.class);
        // when
        ResponseEntity<Employee> employeeResponseEntity = employeeResource.createEmployee(employee);
        // then
        Assert.assertEquals(HttpStatus.BAD_REQUEST, employeeResponseEntity.getStatusCode());
        Assert.assertEquals("Employee is not created. Please check your request body", employeeResponseEntity.getBody());
    }

    @Configuration
    public static class config{

        @MockBean
        private EmployeeResourceDAO employeeResourceDAO;

        @Bean
        public EmployeeResourceService employeeResourceService() {
            EmployeeResourceService employeeResourceService =  new EmployeeResourceService();
            employeeResourceService.setEmployeeResourceDAO(employeeResourceDAO);
            return employeeResourceService;
        };
        @Bean
        public EmployeeResourceImpl employeeResource(EmployeeResourceService employeeResourceService){
            EmployeeResourceImpl employeeResource = new EmployeeResourceImpl();
            employeeResource.setEmployeeResourceService(employeeResourceService);
            return employeeResource;
        }
    }
}
