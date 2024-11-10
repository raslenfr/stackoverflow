package com.raslen.StackOverflow.repositories;

import com.raslen.StackOverflow.entities.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {
}
