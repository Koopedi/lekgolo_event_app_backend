package com.lekgolo.eventsapi.controller;

import com.lekgolo.eventsapi.model.Event;
import com.lekgolo.eventsapi.repository.EventRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository repository;

    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Event> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/upcoming")
    public List<Event> upcoming() {
        return repository.findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(LocalDate.now());
    }

    @GetMapping("/status/{status}")
    public List<Event> byStatus(@PathVariable String status) {
        return repository.findByStatusOrderByEventDateAsc(status);
    }

    @GetMapping("/category/{category}")
    public List<Event> byCategory(@PathVariable String category) {
        return repository.findByCategoryIgnoreCaseOrderByEventDateAsc(category);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<Event> byOrganizer(@PathVariable String organizerId) {
        return repository.findByOrganizerId(organizerId);
    }

    @GetMapping("/search")
    public List<Event> search(@RequestParam("q") String q) {
        return repository.findByNameContainingIgnoreCase(q);
    }

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody Event event) {
        event.setId(null); // always generate a fresh UUID
        Event saved = repository.save(event);
        // re-read so created_at / updated_at (set by the database) are populated
        Event fresh = repository.findById(saved.getId()).orElse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(fresh);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable String id, @Valid @RequestBody Event incoming) {
        return repository.findById(id).map(existing -> {
            existing.setName(incoming.getName());
            existing.setCategory(incoming.getCategory());
            existing.setDescription(incoming.getDescription());
            existing.setEventDate(incoming.getEventDate());
            existing.setEventTime(incoming.getEventTime());
            existing.setDateSort(incoming.getDateSort() != null ? incoming.getDateSort() : incoming.getEventDate());
            existing.setLocation(incoming.getLocation());
            existing.setVenue(incoming.getVenue());
            existing.setBannerImage(incoming.getBannerImage());
            existing.setCapacity(incoming.getCapacity());
            existing.setOrganizerId(incoming.getOrganizerId());
            existing.setPackages(incoming.getPackages());
            if (incoming.getStatus() != null) {
                existing.setStatus(incoming.getStatus());
            }
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Event> updateStatus(@PathVariable String id, @RequestParam("value") String value) {
        return repository.findById(id).map(existing -> {
            existing.setStatus(value);
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
