package com.codewithaman.otpverification.Service;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.codewithaman.otpverification.Entity.Otp;
import com.codewithaman.otpverification.Repository.OtpRepository;
import jakarta.mail.MessagingException;

@Service
public class OtpService {
	
	@Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Generate OTP
    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
    }

    // Send OTP via email
    public void sendOtp(String email) throws MessagingException {
        String otp = generateOtp();
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP expires in 5 mins
        otpRepository.save(otpEntity);

        // Send email
        sendEmail(email, otp);
    }

    private void sendEmail(String to, String otp) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Your OTP Code");
        helper.setText("Your OTP code is: " + otp + ". It is valid for 5 minutes.");
        mailSender.send(message);
    }

    // Verify OTP
    public boolean verifyOtp(String email, String otp) {
        return otpRepository.findByEmail(email)
                .filter(o -> o.getOtp().equals(otp) && o.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }
	

}
