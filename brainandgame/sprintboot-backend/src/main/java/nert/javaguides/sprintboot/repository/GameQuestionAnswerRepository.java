// package nert.javaguides.sprintboot.repository;

package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.game.GameQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameQuestionAnswerRepository extends JpaRepository<GameQuestionAnswer, Long> {
    // Additional query methods if needed
}

