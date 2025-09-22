package foodmanagement.dto;

import jakarta.validation.constraints.*;

public record RegisterCustomerRequest(
    @NotBlank String username,
    @Email @NotBlank String email,
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobile,
    @Size(min = 6, message = "Password must be at least 6 characters") String password // plain text
) {}
