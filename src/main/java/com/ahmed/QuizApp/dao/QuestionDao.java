package com.ahmed.QuizApp.dao;

import com.ahmed.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "select * from question q where q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
