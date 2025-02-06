package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByUserId(Long userId);
}