package com.api.PixelPower.repository;

import com.api.PixelPower.entity.UpgradeSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpgradeSuggestionRepository extends JpaRepository<UpgradeSuggestion, Long> {
    List<UpgradeSuggestion> findByGameComparisonId(Long comparisonId);
}
