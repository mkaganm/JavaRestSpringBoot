package mkaganm.springboottrial.business.services;

import mkaganm.springboottrial.business.dto.EmployeeDto;
import mkaganm.springboottrial.data.entity.EmployeeEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmployeeServices {

    // * CRUD
    public List<EmployeeDto> getAllEmployees();
    public EmployeeDto createEmployee(EmployeeDto employeeDto);
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) throws Throwable;
    public ResponseEntity<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto) throws Throwable;
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) throws Throwable;

    // * model mapper
    public EmployeeDto entityToDto(EmployeeEntity employeeEntity);
    public EmployeeEntity dtoToEntity(EmployeeDto employeeDto);
}
