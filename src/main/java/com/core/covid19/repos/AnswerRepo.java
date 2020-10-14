package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Answer;

public interface AnswerRepo extends JpaRepository<Answer, Integer>{
}
