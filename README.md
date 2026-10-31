<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?logo=springboot" />
  <img src="https://img.shields.io/badge/React-19-61DAFB?logo=react" />
  <img src="https://img.shields.io/badge/JavaScript-ES6%2B-F7DF1E?logo=javascript" />
  <img src="https://img.shields.io/badge/MySQL-8.4-4479A1?logo=mysql" />
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker" />
</p>

# 📚 Booki-Booki – Online-Buchshop

**Booki-Booki** ist eine Full-Stack-Webanwendung für einen digitalen Buchshop. Nutzer können den Buchkatalog erkunden,
Informationen zu Büchern ansehen und ausgewählte Bücher in einem persönlichen Warenkorb sammeln. Das Frontend basiert
auf React, während ein Java-Backend mit Spring Boot die REST-API bereitstellt. MySQL dient zur dauerhaften Speicherung
der Anwendungsdaten. Die Anwendung kann mit Docker Compose gestartet werden.

---

## 🚀 Funktionen

- 📖 **Buchkatalog:** Bücher übersichtlich anzeigen
- 🔎 **Buchdetails:** Informationen zu einzelnen Büchern einsehen
- 👤 **Benutzerkonto:** Registrierung und Anmeldung
- 🛒 **Persönlicher Warenkorb:** Bücher hinzufügen und Warenkorbeinträge verwalten
- 💾 **Persistenter Warenkorb:** Warenkorbdaten in der Datenbank speichern und nach einer erneuten Anmeldung wieder
  abrufen
- 🔌 **REST-API:** Kommunikation zwischen React-Frontend und Spring-Boot-Backend
- 📥 **CSV-Import:** Buchdaten aus einer CSV-Datei einlesen
- 🐳 **Docker Compose:** Frontend, Backend und Datenbank gemeinsam starten

---

## 🧰 Verwendete Technologien

| Bereich            | Technologie                 |
|--------------------|-----------------------------|
| Backend            | Java 21, Spring Boot 3.5.7  |
| Schnittstelle      | REST API                    |
| Frontend           | React, JavaScript, Vite     |
| Benutzeroberfläche | Bootstrap                   |
| Datenbank          | MySQL 8.4                   |
| Datenbankzugriff   | Spring Data JPA / Hibernate |
| Datenimport        | CSV                         |
| Containerisierung  | Docker, Docker Compose      |

---

## ⚙️ Installation & Setup (Docker)

### 🔸 Voraussetzungen

- Docker Desktop mit Docker Compose
- Git (optional, zum Klonen des Repositorys)

### 🔸 Schritte

1. **Repository klonen**

   ```bash
   git clone https://github.com/yvanzambou/booki-booki-online-bookshop.git
   cd booki-booki-online-bookshop
   ```

2. **Buchdaten bereitstellen**

   Die CSV-Datei ist unter folgendem Pfad abgelegt:

   ```text
   data/books.csv
   ```

   Der Docker-Container greift über `/data/books.csv` auf die Datei zu, um die Datenbank mit Bücherdaten zu füllen.

4. **Container starten**

   Führe den folgenden Befehl im Projekt-Hauptverzeichnis aus:

   ```bash
   docker compose up --build -d
   ```

5. **Anwendung im Browser öffnen**

    - **Frontend:** http://localhost:3000
    - **Backend:** http://localhost:8080

6. **Status und Logs prüfen**

   ```bash
   docker compose ps
   docker compose logs -f
   ```

---

## 🧑‍💻 Verwendung

1. **Buchkatalog öffnen:** Rufe `http://localhost:3000` im Browser auf.
2. **Bücher entdecken:** Sieh dir die verfügbaren Bücher und ihre Details an.
3. **Warenkorb verwalten:** Füge Bücher hinzu und verwalte die ausgewählten Artikel.
4. **Warenkorb erneut aufrufen:** Bei einer neuen Anmeldung werden gespeicherte Warenkorbdaten aus der Datenbank
   geladen.

---

## 🗄️ Datenbankzugriff

Die MySQL-Datenbank ist lokal über Port `3307` erreichbar.

Um eine MySQL-Shell im laufenden Datenbank-Container zu öffnen:

```bash
docker compose exec db mysql -u booki -p booki_booki_db
```

Gib das konfigurierte Datenbankpasswort ein, wenn du dazu aufgefordert wirst.
Passwort: `booki_pass`.

Nützliche SQL-Befehle:

```sql
SHOW
TABLES;
```

Um die Daten einer Tabelle anzuzeigen:

```sql
SELECT *
FROM tabellenname;
```

Ersetze `tabellenname` durch einen tatsächlichen Tabellennamen aus der Ausgabe von `SHOW TABLES`.

---

## 🧱 Architektur

```text
React-Frontend
      |
      | HTTP / REST
      v
Spring-Boot-Backend
      |
      | Spring Data JPA
      v
MySQL-Datenbank
```

- **React:** Darstellung der Benutzeroberfläche
- **Spring Boot:** REST-Endpunkte und Anwendungslogik
- **MySQL:** Dauerhafte Speicherung der Anwendungsdaten
- **Docker Compose:** Koordination der Container

---

## 🛑 Anwendung stoppen

Die Container stoppen, ohne das persistente Datenbank-Volume zu löschen:

```bash
docker compose down
```

**Achtung:** `docker compose down --volumes` löscht auch das persistente Datenbank-Volume und damit die darin
gespeicherten Daten. Verwende diesen Befehl nur, wenn du die Daten bewusst entfernen möchtest.

---

## 💡 Mögliche Erweiterungen (Bald)

- 🧪 Automatisierte Backend-Tests mit JUnit und Spring Boot Test
- 🔍 Erweiterte Suche, Filter, Sortierung und Pagination
- ❤️ Wunschliste für Bücher
- 📦 Bestellhistorie und Bestellverwaltung
- 🛡️ Ergänzende Sicherheitsprüfungen für Authentifizierung und Autorisierung
- ⚙️ Continuous Integration mit GitHub Actions
- ☁️ Bereitstellung einer öffentlichen Live-Demo

---

## 📄 Hinweis

Booki-Booki ist ein Portfolio-Projekt zur praktischen Anwendung von Konzepten der Full-Stack-Webentwicklung. Es
verbindet ein React-Frontend mit einem Java-/Spring-Boot-Backend, einer relationalen MySQL-Datenbank und Docker Compose.

---

## 👤 Autor

**Yvan Zambou**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profil-blue?logo=linkedin)](https://linkedin.com/in/yvan-zambou-29aba9261)
[![GitHub](https://img.shields.io/badge/GitHub-Projekte-black?logo=github)](https://github.com/yvanzambou)