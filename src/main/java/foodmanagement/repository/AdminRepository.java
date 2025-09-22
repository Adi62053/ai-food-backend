package foodmanagement.repository;

import foodmanagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Lookup methods
    Admin findByUsername(String username);
    Admin findByEmail(String email);
    Admin findByMobile(String mobile);

    // Optional: allow login via either username or email
    Admin findByUsernameOrEmail(String username, String email);

    // Security code check (if needed in DB)
    Admin findBySecurityCode(String securityCode);

    // Duplicate checks for registration
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
}
