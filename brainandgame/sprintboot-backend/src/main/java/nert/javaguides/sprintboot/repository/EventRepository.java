package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
