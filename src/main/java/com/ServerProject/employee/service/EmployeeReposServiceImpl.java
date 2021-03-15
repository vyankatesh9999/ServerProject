/**
 * 
 */
package com.ServerProject.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServerProject.employee.datamodel.Employee;
import com.ServerProject.employee.payload.ApiResponse;
import com.ServerProject.employee.payload.EmployeeRequest;
import com.ServerProject.employee.payload.EmployeeResponse;
import com.ServerProject.employee.repository.EmployeeRepository;
import com.ServerProject.exception.AppException;

/**
 * @author Vyanky
 *
 */
@Service
public class EmployeeReposServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeReposServiceImpl.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  public List<EmployeeResponse> getAllEmployee() {
    try {
      List<EmployeeResponse> lsemployeeResponse =
          employeeRepository.findAll().stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setFirstName(employee.getFirstName());
            employeeResponse.setLastName(employee.getLastName());
            employeeResponse.setEmail(employee.getEmail());
            employeeResponse.setStatus(employee.getStatus());
            employeeResponse.setPhoneNumber(employee.getPhoneNumber());
            
            if (!employee.getStatus()) {
              employeeResponse.setStatusDisplay("Inactive");
            }
            return employeeResponse;
          }).collect(Collectors.toList());
      return lsemployeeResponse;
    } catch (Exception e) {
      logger.error("Internal Exception", e.getMessage(), e);
      throw new AppException("Contact to system admin at internalerror@evolent.com");
    }
  }

  @Transactional
  public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
    EmployeeResponse employeeResponse = new EmployeeResponse();
    employeeResponse.setSuccess(false);
    try {
     
    	  Employee employee = null;
    	  if(employeeRequest.getId()==null) {
    		  if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
    		        employeeResponse.setMessage("Email is already Alrady Present!");
    		        return employeeResponse;
    		   }
    	  }
    	  employee= setEmployee(employeeRequest);
        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeResponse);
        employeeResponse.setSuccess(true);
        employeeResponse.setMessage("Details Save successfully!!");
      
    } catch (Exception e) {
      logger.error("Internal Exception", e.getMessage(), e);
      throw new AppException("Contact to admin");
    }

    return employeeResponse;

  }

  @Transactional
  public EmployeeResponse updateStatus(Integer id) {
    EmployeeResponse employeeResponse = new EmployeeResponse();
    employeeResponse.setSuccess(false);
    employeeResponse.setMessage("user not updated status successfully");
    try {

      Employee employee = employeeRepository.findById(id).orElse(null);
      if (employee != null) {
        if (employee.getStatus().equals(true)) {

          employee.setStatus(false);

        } else if (employee.getStatus().equals(false)) {
          employee.setStatus(true);
        }
        employeeRepository.save(employee);
        employeeResponse.setSuccess(true);
        employeeResponse.setMessage("Employee status updated  successfully");
        employeeResponse.setStatusDisplay("Inactive");
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
      }

    } catch (Exception e) {
      logger.error("Internal Exception", e.getMessage(), e);
      throw new AppException("Contact to admin ");
    }
    return employeeResponse;

  }

  private Employee setEmployee(EmployeeRequest employeeRequest) {
	  Employee employee =null;
	  if(employeeRequest.getId()!=null) {
		  employee =employeeRepository.findById(employeeRequest.getId()).get();
	  }else {
		  employee=new Employee();
	  }
     
    employee.setFirstName(employeeRequest.getFirstName());
    employee.setLastName(employeeRequest.getLastName());
    employee.setPhoneNumber(employeeRequest.getPhoneNumber());
    employee.setEmail(employeeRequest.getEmail());

    return employee;
  }

public EmployeeResponse getEmployeesByID(@Valid EmployeeRequest employeeRequest) {
	 EmployeeResponse employeeResponse = new EmployeeResponse();
	 Employee employee = employeeRepository.findById(employeeRequest.getId()).get();
	 if(employee!=null) {
		    employeeResponse.setId(employee.getId());
		    employeeResponse.setFirstName(employee.getFirstName());
		    employeeResponse.setLastName(employee.getLastName());
		    employeeResponse.setEmail(employee.getEmail());
		    employeeResponse.setStatus(employee.getStatus());
		    employeeResponse.setPhoneNumber(employee.getPhoneNumber());
		    
		    if (!employee.getStatus()) {
		      employeeResponse.setStatusDisplay("Inactive");
		    } 
		    
	 }
	 else {
		 employeeResponse.setMessage("Employee Not Found"); 
	 }
	 return employeeResponse;
}

}
