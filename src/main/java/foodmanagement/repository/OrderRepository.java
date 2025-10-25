// File: src/main/java/foodmanagement/repository/OrderRepository.java
package foodmanagement.repository;

import foodmanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom queries can be added here later for admin dashboard
}