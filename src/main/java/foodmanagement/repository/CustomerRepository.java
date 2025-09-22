package foodmanagement.repository;

import foodmanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Custom query methods (Spring will auto-implement them)
    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobile(String mobile);
}
