package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.game.QuickGameQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuickGameQuestionRepository extends JpaRepository<QuickGameQuestion, Long> {
}
