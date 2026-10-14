package com.lekgolo.eventsapi.repository;

import com.lekgolo.eventsapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByStatusOrderByEventDateAsc(String status);

    List<Event> findByCategoryIgnoreCaseOrderByEventDateAsc(String category);

    List<Event> findByOrganizerId(String organizerId);

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(LocalDate date);

    List<Event> findByNameContainingIgnoreCase(String name);
}
