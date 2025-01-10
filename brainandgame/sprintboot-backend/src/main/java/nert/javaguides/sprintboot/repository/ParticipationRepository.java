package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByUserIdAndEventId(Long userId, Long eventId);
}
