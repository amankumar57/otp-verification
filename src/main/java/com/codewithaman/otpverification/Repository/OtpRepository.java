package com.codewithaman.otpverification.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codewithaman.otpverification.Entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long>{
	
	Optional<Otp> findByEmail(String email);

}
