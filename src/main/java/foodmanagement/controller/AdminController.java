package foodmanagement.controller;

import foodmanagement.dto.RegisterAdminRequest;
import foodmanagement.model.Admin;
import foodmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ---------------- Register a new admin ----------------
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody RegisterAdminRequest request) {
        try {
            // Log request to debug
            System.out.println("Received admin registration request: " + request);

            // Call service to save admin
            Admin savedAdmin = adminService.registerAdmin(request);

            // Return success JSON
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Admin registration successful!",
                    "admin", savedAdmin
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Registration failed: " + e.getMessage()
            ));
        }
    }

    // ---------------- Get all admins ----------------
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // ---------------- Get admin by ID ----------------
    @GetMapping("/{id}")
    public Optional<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    // ---------------- Admin login ----------------
    @PostMapping("/login")
    public String loginAdmin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String securityCode = loginData.get("securityCode");

        boolean loginSuccess = adminService.loginAdmin(username, password, securityCode);
        return loginSuccess ? "Admin login successful!" : "Invalid credentials or security code.";
    }

    // ---------------- Delete admin by ID ----------------
    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        return adminService.deleteAdmin(id);
    }

    // ---------------- Generate verification code (username-specific) ----------------
    @PostMapping("/generate-code/{username}")
    public String generateCode(@PathVariable String username) {
        return adminService.generateCode(username);
    }

    // ---------------- Verify code (username + code) ----------------
    @PostMapping("/verify-code")
    public String verifyCode(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String code = request.get("securityCode");

        boolean verified = adminService.verifyCode(username, code);
        return verified ? "Code verified successfully!" : "Invalid or expired code";
    }
}
