package com.itible.rpmbackend.repository;

import com.itible.rpmbackend.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("FROM Training t WHERE t.person.name = ?1")
    List<Training> findByPerson(String name);
}

