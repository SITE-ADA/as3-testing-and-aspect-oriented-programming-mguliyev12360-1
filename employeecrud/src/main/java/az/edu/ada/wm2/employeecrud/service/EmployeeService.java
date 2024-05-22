package az.edu.ada.wm2.employeecrud.service;

import az.edu.ada.wm2.employeecrud.model.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee save(Employee employee);

    Employee getById(Long id);

    void deleteById(Long id);
}
