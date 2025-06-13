# 🏎️ Formula 1 Database Application

A Java Swing desktop application for managing and visualizing Formula 1 data, including pilots, cars, teams, circuits, races, and race results. The backend is powered by a relational SQL database.

---

## 📦 Features

- Full CRUD operations for:
  - **Pilots**
  - **Cars**
  - **Teams**
  - **Circuits**
  - **Races**
  - **Race Results**
- User-friendly GUI built with **Java Swing**
- Relational database with foreign key constraints
- Structured query handling with parameterized SQL
- Real-time data refresh in the GUI
- Easy-to-navigate interface for motorsport enthusiasts or database learners

---

## 🛠️ Technologies Used

- **Java** (Swing for GUI)
- **JDBC** (Java Database Connectivity)
- **SQL** (MySQL / PostgreSQL / SQLite – customizable)
- **Maven** *(optional, if you're using it for dependency management)*

---

## 🗃️ Database Schema Overview

```plaintext
+--------------+      +----------+      +--------+
|   Pilots     |◄────►|  Teams   |◄────►|  Cars  |
+--------------+      +----------+      +--------+
       │                                   │
       │                                   │
       ▼                                   ▼
+----------------+      +-------------------------+
|     Races      |◄────►|      RaceResults        |
+----------------+      +-------------------------+
       ▲
       │
       ▼
   +----------+
   | Circuits |
   +----------+
