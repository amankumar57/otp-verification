package com.codewithaman.otpverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.codewithaman.otpverification.Service.OtpService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
	
	 	@Autowired
	    private OtpService otpService;

	    @PostMapping("/send")
	    public ResponseEntity<String> sendOtp(@RequestParam(name = "email", required = true) String email) {
	        try {
	            otpService.sendOtp(email);
	            return ResponseEntity.ok("OTP sent successfully to " + email);
	        } catch (MessagingException e) {
	            return ResponseEntity.badRequest().body("Error sending OTP");
	        }
	    }

	    @PostMapping("/verify")
	    public ResponseEntity<String> verifyOtp(@RequestParam(name = "email", required = true) String email, @RequestParam(name = "otp", required = true) String otp) {
	        if (otpService.verifyOtp(email, otp)) {
	            return ResponseEntity.ok("OTP verified successfully!");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid or expired OTP");
	        }
	    }
	    

}
