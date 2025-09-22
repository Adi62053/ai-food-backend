package foodmanagement.service;

import foodmanagement.model.Customer;
import foodmanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Register customer with validation
    public Customer registerCustomer(Customer customer) {
        // Validate required fields
        if (customer.getUsername() == null || customer.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email cannot be empty");
        }
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (customer.getMobile() == null || !customer.getMobile().matches("\\d{10}")) {
            throw new RuntimeException("Phone number must be exactly 10 digits and contain only numbers");
        }

        // Check if email already exists
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + customer.getEmail());
        }

        // Check if username already exists
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + customer.getUsername());
        }

        // Save customer (password stored as plain text - NOT recommended for production)
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Login customer using username (plain-text password)
    public boolean loginCustomer(String username, String password) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        return customerOpt.isPresent() && customerOpt.get().getPassword().equals(password);
    }

    // Delete customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
