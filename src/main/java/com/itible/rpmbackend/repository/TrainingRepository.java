package com.itible.rpmbackend.repository;

import com.itible.rpmbackend.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}

