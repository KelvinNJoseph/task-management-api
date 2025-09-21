# Task Management API with Tags and Google Calendar Sync

## 🚀 Project Overview
This API allows users to create, manage, and organize tasks with tags. Each task can be linked to multiple tags, and tasks are synchronized with **Google Calendar** to ensure due dates are reflected in users’ personal calendars.

---

## ✨ Features
### Task Management
- Create tasks with title, description, due date, status, and tags.
- Update tasks (title, status, due date, tags).
- Delete tasks (removes from Google Calendar but retains tags).
- Retrieve all tasks with filters (status, tags).

### Tag Management
- Tags are auto-created when used in a task.
- View all tags and how many tasks use them.
- View details of a tag with its associated tasks.

### Google Calendar Integration
- Sync tasks → calendar events.
- Update task changes → reflected in calendar.
- Delete task → removes calendar event.
- (Optional/Future) Sync changes from Google Calendar back to tasks.
- Handles event/task conflicts gracefully.

---

## 🛠 Tech Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.x
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Calendar API**: Google Calendar API v3

---

## ⚙️ Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/KelvinNJoseph/task-management-api.git
cd task-management-api
```
### 2. Setup Environment Variables
Create a .env file in the project root:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/taskmanagement

SPRING_DATASOURCE_USERNAME=postgres

SPRING_DATASOURCE_PASSWORD=password

GOOGLE_CLIENT_ID=your-client-id.apps.googleusercontent.com

GOOGLE_CLIENT_SECRET=your-client-secret

GOOGLE_REDIRECT_URI=http://localhost:8888/oauth2/callback

### 3. Configure Google API
Go to Google Cloud Console.

Create OAuth 2.0 credentials (Application Type: Web Application).

Add redirect URI: `http://localhost:8888/oauth2/callback`

### 4. Credentials
Download your credentials and place them in src/main/resources/credentials.json (⚠️ excluded from Git).

## 🧪 Testing Guide

### Unit Tests
- Run `mvn test`
- Located under `src/test/java/.../service`
- Test services and mappers with mocked dependencies.

### Integration Tests
- Located under `src/test/java/.../controller`
- Uses `@SpringBootTest` + `MockMvc`
- Verifies full flow from Controller → Service → Repository.

