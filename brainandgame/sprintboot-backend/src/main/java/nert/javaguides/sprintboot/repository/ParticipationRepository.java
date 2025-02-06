package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByUserIdAndEventId(Long userId, Long eventId);
    List<Participation> findByUserId(Long userId);

    List<Participation> findByEventIdOrderByPointsDesc(Long eventId);
}
