package com.meas.smarthome1.Repos;

import com.meas.smarthome1.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
