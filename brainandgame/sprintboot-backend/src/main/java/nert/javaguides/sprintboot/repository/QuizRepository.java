package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
