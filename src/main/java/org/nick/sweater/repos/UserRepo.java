package org.nick.sweater.repos;

import org.nick.sweater.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <User, Long> {
	User findByUsername(String username);
	
	User findByActivationCode(String code);
}
