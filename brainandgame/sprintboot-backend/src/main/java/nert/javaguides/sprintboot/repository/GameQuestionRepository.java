// src/main/java/nert/javaguides/sprintboot/repository/GameQuestionRepository.java

package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.game.GameQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameQuestionRepository extends JpaRepository<GameQuestion, Long> {

    // H2 syntax for random selection
    @Query(value = "SELECT * FROM game_questions ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<GameQuestion> findRandomQuestions(int limit);


}
