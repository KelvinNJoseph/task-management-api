package com.sareafrica.taskmanagement.service;

import java.time.LocalDateTime;

public interface GoogleCalendarService {
    String createEvent(String title, String description, LocalDateTime start, LocalDateTime end) throws Exception;
    void updateEvent(String eventId, String title, String description, LocalDateTime start, LocalDateTime end) throws Exception;
    void deleteEvent(String eventId) throws Exception;
}
