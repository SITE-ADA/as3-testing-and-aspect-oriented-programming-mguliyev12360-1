package az.edu.ada.wm2.employeecrud.model.mapper;

import az.edu.ada.wm2.employeecrud.model.dto.EmployeeDTO;
import az.edu.ada.wm2.employeecrud.model.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toEmployeeDTO(Employee employee);
    Employee toEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);
}
