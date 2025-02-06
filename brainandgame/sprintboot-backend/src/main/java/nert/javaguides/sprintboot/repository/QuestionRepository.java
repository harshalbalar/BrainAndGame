package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Custom query methods can be defined here

    List<Question> findAllByQuizId(Long quizId);
}