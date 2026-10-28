<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?logo=springboot" />
  <img src="https://img.shields.io/badge/React-19-61DAFB?logo=react" />
  <img src="https://img.shields.io/badge/JavaScript-ES6%2B-F7DF1E?logo=javascript" />
  <img src="https://img.shields.io/badge/MySQL-8.4-4479A1?logo=mysql" />
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker" />
  <img src="https://img.shields.io/badge/Bootstrap-UI-7952B3?logo=bootstrap" />
  <img src="https://img.shields.io/badge/GitHub-Repository-181717?logo=github" />
</p>

# 📚 Booki-Booki – Online-Buchshop

**Booki-Booki** ist eine Full-Stack-Webanwendung für einen digitalen Buchshop. Nutzer können den Buchkatalog erkunden, Informationen zu Büchern ansehen und ausgewählte Bücher in einem persönlichen Warenkorb sammeln. Das Frontend basiert auf React, während ein Java-Backend mit Spring Boot die REST-API bereitstellt. MySQL dient zur dauerhaften Speicherung der Anwendungsdaten. Die Anwendung kann mit Docker Compose gestartet werden.

---

## 🚀 Funktionen

- 📖 **Buchkatalog:** Bücher übersichtlich anzeigen
- 🔎 **Buchdetails:** Informationen zu einzelnen Büchern einsehen
- 👤 **Benutzerkonto:** Registrierung und Anmeldung, sofern im aktuellen Projektstand aktiviert
- 🛒 **Persönlicher Warenkorb:** Bücher hinzufügen und Warenkorbeinträge verwalten
- 💾 **Persistenter Warenkorb:** Warenkorbdaten in der Datenbank speichern und nach einer erneuten Anmeldung wieder abrufen
- 🔌 **REST-API:** Kommunikation zwischen React-Frontend und Spring-Boot-Backend
- 📥 **CSV-Import:** Buchdaten aus einer CSV-Datei einlesen
- 🐳 **Docker Compose:** Frontend, Backend und Datenbank gemeinsam starten

> Prüfe vor der Veröffentlichung, ob alle genannten Funktionen im aktuellen Repository vollständig implementiert sind.

---

## 🧰 Verwendete Technologien

| Bereich | Technologie |
|----------|-------------|
| Backend | Java 21, Spring Boot 3.5.7 |
| Schnittstelle | REST API |
| Frontend | React, JavaScript, Vite |
| Benutzeroberfläche | Bootstrap |
| Datenbank | MySQL 8.4 |
| Datenbankzugriff | Spring Data JPA / Hibernate |
| Datenimport | CSV |
| Containerisierung | Docker, Docker Compose |

---

## ⚙️ Installation & Setup (Docker)

### 🔸 Voraussetzungen

- Docker Desktop mit Docker Compose
- Git (optional, zum Klonen des Repositorys)

### 🔸 Schritte

1. **Repository klonen**

   ```bash
   git clone <URL_DEINES_GITHUB_REPOSITORYS>
   cd bookshop
   ```

2. **Umgebungsvariablen konfigurieren**

   Lege bei Bedarf eine `.env`-Datei im Projekt-Hauptverzeichnis an. Beispiel passend zur Docker-Compose-Konfiguration:

   ```dotenv
   DB_NAME=booki_booki_db
   DB_USERNAME=booki
   DB_PASSWORD=change_me
   DB_ROOT_PASSWORD=change_root_password
   JAVA_OPTS=-Xmx512m
   ```

   Verwende sichere, individuelle Passwörter. Echte Zugangsdaten dürfen nicht in ein öffentliches GitHub-Repository eingecheckt werden.

3. **Buchdaten bereitstellen**

   Lege die CSV-Datei unter folgendem Pfad ab:

   ```text
   data/books.csv
   ```

   Der Docker-Container greift über `/data/books.csv` auf die Datei zu. Wenn du stattdessen eine bereinigte Datei verwenden möchtest, passe `CSV_PATH` in der Compose-Konfiguration beispielsweise auf `/data/books_clean.csv` an.

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
4. **Warenkorb erneut aufrufen:** Bei aktivierter Anmeldung werden gespeicherte Warenkorbdaten aus der Datenbank geladen.

Die verfügbaren Aktionen hängen vom aktuellen Implementierungsstand der Anwendung ab.

---

## 🗄️ Datenbankzugriff

Die MySQL-Datenbank ist lokal über Port `3307` erreichbar.

Um eine MySQL-Shell im laufenden Datenbank-Container zu öffnen:

```bash
docker compose exec db mysql -u booki -p booki_booki_db
```

Gib das konfigurierte Datenbankpasswort ein, wenn du dazu aufgefordert wirst.

Nützliche SQL-Befehle:

```sql
SHOW TABLES;
```

Um die Daten einer Tabelle anzuzeigen:

```sql
SELECT * FROM tabellenname;
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

## 🖼️ Screenshots

Füge hier Screenshots der tatsächlich implementierten Benutzeroberfläche ein. Lege die Bilder beispielsweise im Verzeichnis `docs/screenshots/` ab und passe die Dateinamen entsprechend an.

```html
<p align="center">
  <img src="docs/screenshots/startseite.png" alt="Startseite von Booki-Booki" width="700" />
  <br>
  <em>Abbildung 1: Startseite</em>
</p>
```

Weitere geeignete Screenshots sind der Buchkatalog, die Buchdetailansicht und der Warenkorb.

---

## 🛑 Anwendung stoppen

Die Container stoppen, ohne das persistente Datenbank-Volume zu löschen:

```bash
docker compose down
```

**Achtung:** `docker compose down --volumes` löscht auch das persistente Datenbank-Volume und damit die darin gespeicherten Daten. Verwende diesen Befehl nur, wenn du die Daten bewusst entfernen möchtest.

---

## 💡 Mögliche Erweiterungen

- 🧪 Automatisierte Backend-Tests mit JUnit und Spring Boot Test
- 🔍 Erweiterte Suche, Filter, Sortierung und Pagination
- ❤️ Wunschliste für Bücher
- 📦 Bestellhistorie und Bestellverwaltung
- 🛡️ Ergänzende Sicherheitsprüfungen für Authentifizierung und Autorisierung
- ⚙️ Continuous Integration mit GitHub Actions
- ☁️ Bereitstellung einer öffentlichen Live-Demo

---

## 📄 Hinweis

Booki-Booki ist ein Portfolio-Projekt zur praktischen Anwendung von Konzepten der Full-Stack-Webentwicklung. Es verbindet ein React-Frontend mit einem Java-/Spring-Boot-Backend, einer relationalen MySQL-Datenbank und Docker Compose.

---

## 👤 Autor

**Yvan Zambou**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profil-blue?logo=linkedin)](https://linkedin.com/in/yvan-zambou-29aba9261)
[![GitHub](https://img.shields.io/badge/GitHub-Projekte-black?logo=github)](https://github.com/yvanzambou)