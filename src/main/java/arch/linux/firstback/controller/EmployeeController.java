package arch.linux.firstback.controller;

import arch.linux.firstback.exception.ResourceNotFoundException;
import arch.linux.firstback.model.Employee;
import arch.linux.firstback.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //GetAll
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //Create
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //GetById
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() ->
                new ResourceNotFoundException("There is no employee with id: " + id));
        return ResponseEntity.ok(employee);
    }

    //Update
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Employee actualEmployee = employeeRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("There is no employee with id: " + id));
        actualEmployee.setFirstName(employee.getFirstName());
        actualEmployee.setLastName(employee.getLastName());
        actualEmployee.setId(employee.getId());

        Employee updatedEmployee = employeeRepository.save(actualEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    //Delete
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee actualEmployee = employeeRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("There is no employee with id: " + id));
        employeeRepository.delete(actualEmployee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Killed", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
