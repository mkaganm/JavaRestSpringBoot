package mkaganm.springboottrial.business.services.impl;

import mkaganm.springboottrial.business.dto.EmployeeDto;
import mkaganm.springboottrial.business.services.EmployeeServices;
import mkaganm.springboottrial.data.entity.EmployeeEntity;
import mkaganm.springboottrial.data.repository.EmployeeRepository;
import mkaganm.springboottrial.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    // * CRUD

    // * http://localhost:8080/api/v1/employees
    @GetMapping("/employees")
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> dtoList = new ArrayList<>();
        Iterable<EmployeeEntity> entityList = employeeRepository.findAll();
        for (EmployeeEntity entity : entityList) {
            EmployeeDto employeeDto = entityToDto(entity);
            dtoList.add(employeeDto);
        }
        return dtoList;
    }

    // * http://localhost:8080/api/v1/employees
    @PostMapping("/employees")
    @Override
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {

        EmployeeEntity employeeEntity = dtoToEntity(employeeDto);
        employeeRepository.save(employeeEntity);
        return employeeDto;
    }

    // * http://localhost:8080/api/v1/employees/1
    @GetMapping("/employees/{id}")
    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) throws Throwable {

        EmployeeEntity employeeEntity = (EmployeeEntity) employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id : " + id));

        EmployeeDto employeeDto = entityToDto(employeeEntity);

        return ResponseEntity.ok(employeeDto);
    }

    // * http://localhost:8080/api/v1/employees/1
    @PutMapping("/employees/{id}")
    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto employeeDto
    ) throws Throwable {

        EmployeeEntity employeeEntity = dtoToEntity(employeeDto);
        EmployeeEntity employee = (EmployeeEntity) employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id : " + id));

        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setEmail(employeeEntity.getEmail());

        EmployeeEntity updatedEntity = (EmployeeEntity) employeeRepository.save(employee);
        EmployeeDto updatedDto = entityToDto(updatedEntity);

        return ResponseEntity.ok(updatedDto);
    }

    // * http://localhost:8080/api/v1/employees/1
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) throws Throwable {

        EmployeeEntity employeeEntity = (EmployeeEntity) employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id : " + id));

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    // * MODEL MAPPER

    // * ENTITY -> DTO
    @Override
    public EmployeeDto entityToDto(EmployeeEntity employeeEntity) {
        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    // * DTO -> ENTITY
    @Override
    public EmployeeEntity dtoToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, EmployeeEntity.class);
    }
}
