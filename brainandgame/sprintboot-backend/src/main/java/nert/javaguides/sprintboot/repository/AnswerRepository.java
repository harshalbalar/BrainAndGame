package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Custom query methods can be defined here
}