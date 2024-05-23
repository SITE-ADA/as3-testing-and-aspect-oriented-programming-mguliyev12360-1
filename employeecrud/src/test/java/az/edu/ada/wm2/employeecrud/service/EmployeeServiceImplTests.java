package az.edu.ada.wm2.employeecrud.service;

import az.edu.ada.wm2.employeecrud.model.entity.Employee;
import az.edu.ada.wm2.employeecrud.repository.EmployeeRepository;
import az.edu.ada.wm2.employeecrud.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceImplTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Employee> expectedEmployees = Arrays.asList(new Employee(1L, "John", "Doe", 50000.0),
                new Employee(2L, "Jane", "Doe", 60000.0));
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        List<Employee> actualEmployees = employeeService.getAllEmployees();

        assertEquals(expectedEmployees, actualEmployees);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void saveEmployeeTest() {
        Employee employee = new Employee(1L, "John", "Doe", 50000.0);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.save(employee);

        assertEquals(employee, savedEmployee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void getEmployeeByIdTest() {
        Long id = 1L;
        Optional<Employee> expectedEmployee = Optional.of(new Employee(1L, "John", "Doe", 50000.0));
        when(employeeRepository.findById(id)).thenReturn(expectedEmployee);

        Employee result = employeeService.getById(id);

        assertTrue(result != null);
        assertEquals(expectedEmployee.get(), result);
        verify(employeeRepository).findById(id);
    }

    @Test
    public void deleteEmployeeByIdTest() {
        Long id = 1L;
        doNothing().when(employeeRepository).deleteById(id);

        employeeService.deleteById(id);

        verify(employeeRepository).deleteById(id);
    }

    @Test
    public void updateEmployeeTest() {
        Employee existingEmployee = new Employee(1L, "John", "Doe", 50000.0);
        Employee newDetails = new Employee(1L, "John Updated", "Doe Updated", 55000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(newDetails)).thenReturn(newDetails);

        Employee updatedEmployee = employeeService.save(newDetails);

        assertNotNull(updatedEmployee);
        assertEquals(newDetails.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(newDetails.getLastName(), updatedEmployee.getLastName());
        assertEquals(newDetails.getSalary(), updatedEmployee.getSalary());
        verify(employeeRepository).save(newDetails);
    }
}