package com.sareafrica.taskmanagement.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.sareafrica.taskmanagement.service.GoogleCalendarService;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private static final String APPLICATION_NAME = "Task Management API";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private Calendar getCalendarService() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(getClass().getResourceAsStream("/credentials.json"))
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)
        )
                .setAccessType("offline")
                .build();

               LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8888)
                .setCallbackPath("/oauth2/callback")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public String createEvent(String title, String description, LocalDateTime start, LocalDateTime end) throws Exception {
        Event event = new Event()
                .setSummary(title)
                .setDescription(description);

        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());

        event.setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDate)));
        event.setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDate)));

        Calendar service = getCalendarService();
        event = service.events().insert("primary", event).execute();

        return event.getId();
    }

    @Override
    public void updateEvent(String eventId, String title, String description, LocalDateTime start, LocalDateTime end) throws Exception {
        Calendar service = getCalendarService();
        Event event = service.events().get("primary", eventId).execute();

        event.setSummary(title);
        event.setDescription(description);

        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());

        event.setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDate)));
        event.setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDate)));

        service.events().update("primary", eventId, event).execute();
    }

    @Override
    public void deleteEvent(String eventId) throws Exception {
        Calendar service = getCalendarService();
        service.events().delete("primary", eventId).execute();
    }
}
