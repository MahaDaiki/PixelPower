package com.api.PixelPower.repository;

import com.api.PixelPower.entity.GameComparison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameComparisonRepository extends JpaRepository<GameComparison, Long> {
    List<GameComparison> findByConfiguration_UserId(Long userId);
}
