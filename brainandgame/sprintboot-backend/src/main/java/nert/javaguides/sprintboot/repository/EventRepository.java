package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(Long userId);
}
