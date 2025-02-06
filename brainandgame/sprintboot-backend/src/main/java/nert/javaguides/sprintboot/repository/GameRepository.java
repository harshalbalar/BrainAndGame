// package nert.javaguides.sprintboot.repository;

package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // Additional query methods if needed

    @Query("SELECT g FROM Game g " +
            "WHERE (g.initiator.id = :participantId OR g.joiner.id = :participantId) " +
            "AND g.status = 'ONGOING'")
    List<Game> findOngoingGamesForParticipant(@Param("participantId") Long participantId);

    @Query("SELECT g FROM Game g " +
            "WHERE (g.initiator.id = :participantId OR g.joiner.id = :participantId) " +
            "AND g.status = 'COMPLETED'")
    List<Game> findCompletedGamesForParticipant(@Param("participantId") Long participantId);

}
