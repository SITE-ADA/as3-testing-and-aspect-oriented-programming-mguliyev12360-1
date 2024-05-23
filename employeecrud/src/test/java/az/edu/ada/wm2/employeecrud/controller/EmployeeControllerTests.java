package az.edu.ada.wm2.employeecrud.controller;

import az.edu.ada.wm2.employeecrud.model.entity.Employee;
import az.edu.ada.wm2.employeecrud.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void listEmployeesTest() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(new Employee(1L, "John", "Doe", 50000.0),
                new Employee(2L, "Jane", "Doe", 60000.0)));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    public void createEmployeeTest() throws Exception {
        Employee employee = new Employee(1L, "John", "Doe", 50000.0);
        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"salary\":50000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void getEmployeeTest() throws Exception {
        Employee employee = new Employee(1L, "John", "Doe", 50000.0);
        when(employeeService.getById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    public void getEmployeeNotFoundTest() throws Exception {
        when(employeeService.getById(1L)).thenReturn(null);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmployeeNotFoundTest() throws Exception {
        when(employeeService.getById(1L)).thenReturn(null);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John Updated\",\"lastName\":\"Doe Updated\",\"salary\":55000.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteEmployeeNotFoundTest() throws Exception {
        when(employeeService.getById(1L)).thenReturn(null);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNotFound());
    }
}