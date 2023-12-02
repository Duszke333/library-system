# PAP2023Z-Z17
Project z Javy

# Członkowie zespołu
- Jabłoński Piotr
- Wysocki Paweł
- Kośla Jabub
- Sienkiewicz Bruno

# Dokumentacja

1. ### Temat: Aplikacja do zarządzania książkami w bibliotece

Planujemy stworzyć aplikację, która będzie obsługiwała zarówno czytelników (część CLIENT projektu, będzie umożliwione np. przeglądanie zbiorów, zamawianie), jak i pracowników biblioteki (część EMPLOYEE projektu, będzie umożliwione np. zarządzanie zbiorami).

2. ### Technologie
    - Backend: **Java**
    - Frontend: **JavaFX**
    - Baza: **PostgreSQL**

3. ### Podział funkcjonalności
    #### Piotr Jabłoński:
    - [ ] Możliwość przeglądania katalogu książek, szczegółów dotyczących książek takich jak autor, rok wydania książki, ilość stron, rodzaj okładki, wygrane nagrody… - CLIENT
    - [ ] Filtrowanie podglądu według nazwy, roku, autora… - CLIENT
    - [ ] Możliwe będzie zarządzanie książkami, edycja książek, usuwanie, dodawanie rekordów książek - EMPLOYEE
    - [ ] Tworzenie kolejki wypożyczeń, ustawienie ogólnego limitu, konkretny limit per książka - EMPLOYEE
    - [ ] Sposób wyświetlania książek: Okładka/Szczegółowy opis z detalami - CLIENT
    #### Jakub Kośla:
    - [ ] Statystyka wypożyczeń (najczęściej wypożyczane książki w danym okresie czasu) - CLIENT
    - [ ] Będzie możliwe również sprawdzenie historii wypożyczeń konkretnych książek - EMPLOYEE
    - [ ] Dla każdego użytkownika dostępna będzie historia wypożyczeń m. in. z możliwością sortowania wg długości wypożyczenia - CLIENT
    - [ ] Sprawdzanie stanu książek (czy i do kiedy są wypożyczone/zarezerwowane / dostępne do wypożyczenia) - EMPLOYEE
    - [ ] Podgląd pracowników, innych placówek naszej biblioteki - CLIENT
    #### Bruno Sienkiewicz:
    - [ ] Definiowanie kary za przekroczenie limitu oddania książki do biblioteki - EMPLOYEE
    - [ ] Zarządzanie kontami członkowskimi (m. in. blokada w przypadku przekroczenia terminu wypożyczenia) - EMPLOYEE
    - [ ] Możliwe będzie zamówienie książki, tj. w przypadku, gdy książka nie będzie dostępna, możliwe będzie wyrażenie chęci wypożyczenia i dopisania się do kolejki wypożyczeń - CLIENT
    - [ ] Możliwość uregulowania kar za przekroczenie terminu oddania książki - CLIENT
    - [ ] Możliwość przedłużenia wypożyczenia, najwyżej jeden raz, jeżeli klient do tej pory się uregulował z wypożyczeniami, i nie ma oczekujących na wypożyczenie - CLIENT
    #### Paweł Wysocki:
    - [ ] Stworzenie konta bibliotecznego, usunięcie go lub jego edycja - CLIENT
    - [ ] Podgląd wypożyczonych obecnie pozycji - CLIENT
    - [ ] Możliwość tworzenia listy pozycji, które chce się przeczytać - CLIENT
    - [ ] Możliwość oceniania wypożyczonych książek - CLIENT
    - [ ] Możliwość zgłoszenia uszkodzenia lub zgubienia książki - CLIENT

## Baza danych

Zbudowanie bazy danych:
- w terminalu `docker compose up --build` w lokalizacji pliku docker-compose.yml
  - jeżeli mamy już zbudowaną bazę to wystarczy `docker compose up`
- Powinien utworzyć się folder var

Baza będzie dostępna pod adresem `localhost:5432`
- login: `postgres`
- hasło: `postgres`
- baza: `pap`
  - w bazie jest schema `pap` w niej są trzymane tabele

W folderze Docker jest skrypt `init.sql` który tworzy bazę.
Można go zmieniać żeby dostosować startową zawartość bazy.

