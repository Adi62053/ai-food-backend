package foodmanagement.controller;

import foodmanagement.model.Customer;
import foodmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")  // Base URL for all customer APIs
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // ✅ Register a new customer with proper error handling
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.registerCustomer(customer);
            return ResponseEntity.ok("Registration successful!");
        } catch (RuntimeException e) {
            // Return a 400 Bad Request with the error message
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // ✅ Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // ✅ Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // ✅ Login using username and password
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody Customer loginData) {
        boolean isValid = customerService.loginCustomer(loginData.getUsername(), loginData.getPassword());
        return isValid ? ResponseEntity.ok("Login successful!") :
                ResponseEntity.status(401).body("Invalid username or password");
    }

    // ✅ Delete customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted with id " + id);
    }
}
