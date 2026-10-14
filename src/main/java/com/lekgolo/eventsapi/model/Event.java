package com.lekgolo.eventsapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Maps to the `events` table.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private String id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "category", nullable = false)
    private String category;

    @NotBlank
    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @NotNull
    @Column(name = "event_time", nullable = false)
    private LocalTime eventTime;

    // Mirrors event_date; filled automatically if not supplied
    @Column(name = "date_sort")
    private LocalDate dateSort;

    @NotBlank
    @Column(name = "location", nullable = false)
    private String location;

    @NotBlank
    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "banner_image", columnDefinition = "text")
    private String bannerImage;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "organizer_id", length = 36)
    private String organizerId;

    // longtext column - typically a JSON string
    @Column(name = "packages", columnDefinition = "longtext")
    private String packages;

    // enum column in the database (default 'draft'); kept as a String
    @Column(name = "status")
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        if (dateSort == null) {
            dateSort = eventDate;
        }
        if (status == null || status.isBlank()) {
            status = "draft";
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getEventTime() { return eventTime; }
    public void setEventTime(LocalTime eventTime) { this.eventTime = eventTime; }

    public LocalDate getDateSort() { return dateSort; }
    public void setDateSort(LocalDate dateSort) { this.dateSort = dateSort; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getBannerImage() { return bannerImage; }
    public void setBannerImage(String bannerImage) { this.bannerImage = bannerImage; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

    public String getPackages() { return packages; }
    public void setPackages(String packages) { this.packages = packages; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
