package foodmanagement.dto;

public class RegisterAdminRequest {
    private String username;
    private String email;
    private String mobile;   
    private String address;
    private String password;
    private String confirmPassword; // optional: validated in frontend
    private String securityCode;    // ✅ verification code

    public RegisterAdminRequest() {}

    // --- Getters & Setters ---
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getMobile() { 
        return mobile; 
    }
    public void setMobile(String mobile) { 
        this.mobile = mobile; 
    }

    public String getAddress() { 
        return address; 
    }
    public void setAddress(String address) { 
        this.address = address; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getConfirmPassword() { 
        return confirmPassword; 
    }
    public void setConfirmPassword(String confirmPassword) { 
        this.confirmPassword = confirmPassword; 
    }

    public String getSecurityCode() { 
        return securityCode; 
    }
    public void setSecurityCode(String securityCode) { 
        this.securityCode = securityCode; 
    }
}
