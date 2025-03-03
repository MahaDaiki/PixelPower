package com.api.PixelPower.repository;

import com.api.PixelPower.entity.GameComparison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameComparisonRepository extends JpaRepository<GameComparison, Long> {
}
