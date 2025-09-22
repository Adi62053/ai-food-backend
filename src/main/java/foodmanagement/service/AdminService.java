package foodmanagement.service;

import foodmanagement.dto.RegisterAdminRequest;
import foodmanagement.model.Admin;
import foodmanagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Map<String, CodeInfo> codeStore = new HashMap<>();
    private static final long CODE_EXPIRY_TIME = 5 * 60 * 1000; // 5 min
    private static final String FOUNDER_EMAIL = "aditiwari256@example.com";

    private static class CodeInfo {
        String code;
        long timestamp;
        CodeInfo(String code, long timestamp) { this.code = code; this.timestamp = timestamp; }
    }

    // ---------------- CRUD ----------------
    public List<Admin> getAllAdmins() { return adminRepository.findAll(); }
    public Optional<Admin> getAdminById(Long id) { return adminRepository.findById(id); }
    public String deleteAdmin(Long id) { adminRepository.deleteById(id); return "Admin deleted with id " + id; }

    // ---------------- Code generation ----------------
    public String generateCode(String username) {
        String code = String.format("%06d", new Random().nextInt(999999));
        codeStore.put(username, new CodeInfo(code, System.currentTimeMillis()));
        String subject = "Admin Verification Code";
        String body = "Hi " + username + ",\nYour verification code: " + code + "\nExpires in 5 minutes.";
        emailService.sendEmail(FOUNDER_EMAIL, subject, body);
        return "Code sent to founder's email for " + username;
    }

    public boolean verifyCode(String username, String code) {
        CodeInfo info = codeStore.get(username);
        if (info == null) return false;
        if (System.currentTimeMillis() - info.timestamp > CODE_EXPIRY_TIME) {
            codeStore.remove(username);
            return false;
        }
        return info.code.equals(code);
    }

    // ---------------- Register Admin ----------------
    public Admin registerAdmin(RegisterAdminRequest request) throws Exception {
        CodeInfo info = codeStore.get(request.getUsername());
        if (info == null || !info.code.equals(request.getSecurityCode())) {
            throw new Exception("Invalid or expired code for this username.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new Exception("Passwords do not match.");
        }

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setMobile(request.getMobile());
        admin.setAddress(request.getAddress());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setSecurityCode(info.code);

        adminRepository.save(admin);
        codeStore.remove(request.getUsername());
        return admin;
    }

    // ---------------- Login Admin ----------------
    public boolean loginAdmin(String username, String password, String code) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) return false;
        boolean passwordMatches = passwordEncoder.matches(password, admin.getPassword());
        boolean codeMatches = code != null && code.equals(admin.getSecurityCode());
        return passwordMatches && codeMatches;
    }
}
