package com.api.PixelPower.repository;

import com.api.PixelPower.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
//    Configuration findByUserId(Long userId);
    List<Configuration> findByUserId(Long userId);
}
