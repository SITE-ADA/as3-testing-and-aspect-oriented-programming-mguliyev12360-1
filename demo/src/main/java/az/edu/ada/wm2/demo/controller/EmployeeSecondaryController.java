package az.edu.ada.wm2.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/secondary/employees")
public class EmployeeSecondaryController {

    private final RestTemplate restTemplate;
    private final String serviceUrl = "http://localhost:8080/employees";

    @Autowired
    public EmployeeSecondaryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<String> listEmployees() {
        return restTemplate.getForEntity(serviceUrl, String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployee(@PathVariable Long id) {
        String url = serviceUrl + "/" + id;
        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<String> createEmployee(@RequestBody String employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(employee, headers);
        restTemplate.postForEntity(serviceUrl, request, String.class);
        return ResponseEntity.ok("Employee created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody String employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = serviceUrl + "/" + id;
        HttpEntity<String> request = new HttpEntity<>(employee, headers);
        restTemplate.put(url, request);
        return ResponseEntity.ok("Employee updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        String url = serviceUrl + "/" + id;
        restTemplate.delete(url);
        return ResponseEntity.ok("Employee deleted successfully.");
    }
}
