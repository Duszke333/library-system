# PAP2023Z-Z17
Project z Javy

# Członkowie zespołu
- Jabłoński Piotr
- Wysocki Paweł
- Kośla Jabub
- Sienkiewicz Bruno

## Baza danych

Zbudowanie bazy danych:
- w terminalu `docker compose up --build` w lokalizacji pliku docker-compose.yml
- Powinien utworzyć się folder var z zawartością bazy

Baza będzie dostępna pod adresem `localhost:5432`
- login: `postgres`
- hasło: `postgres`
- baza: `pap`
  - w bazie jest schema `pap` w niej są trzymane tabele

W folderze Docker jest skrypt `init.sql` który tworzy bazę.
Można go zmieniać żeby dostosować startową zawartość bazy.

