# PAP2023Z-Z17
Project z Javy

# Instalacja w środowisku linuksowym:
Aby uruchomić aplikację należy mieć zainstalowane następujące programy:
- Java JDK17, proponowana liberica17 Bellsoft lub OracleJDK, gdyż te mają wbudowany komponent JavaFX (przykłowa instrukcja: https://linux.how2shout.com/steps-to-install-openjdk-17-ubuntu-linux-such-as-22-04-or-20-04/)
- Maven (przykładowa instrukcja: https://phoenixnap.com/kb/install-maven-on-ubuntu)
- Docker (przykładowa instalacja: https://docs.docker.com/engine/install/ubuntu/)
- JavaFX (przykładowa instalacja: https://openjfx.io/openjfx-docs/)

Aplikację uruchamia się w następujący sposób:
- należy uruchomić Dockera
- należy ściągnąć zawartość repozytorium (git fetch https://gitlab-stud.elka.pw.edu.pl/papuga/pap2023z-z17/)
- należy przejść do katalogu, do którego ściągnięto zawartość repozytorium, a następnie uruchomić skrypt (./pap_install.sh)
- skrypt zbuduje bazę w dockerze, a następnie uruchomi aplikację

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

3. ### Funkcjonalności
- [X] Możliwość przeglądania katalogu książek, szczegółów dotyczących książek takich jak autor, rok wydania książki, ilość stron, rodzaj okładki, wygrane nagrody… - CLIENT
- [X] Filtrowanie podglądu według nazwy, roku, autora… - CLIENT
- [X] Zarządzanie książkami, edycja książek, usuwanie, dodawanie rekordów książek - EMPLOYEE
- [ ] Tworzenie kolejki wypożyczeń, ustawienie ogólnego limitu, konkretny limit per książka - EMPLOYEE
- [X] Wyświetlanie książek: Okładka/Szczegółowy opis z detalami – użytkownik z poziomu katalogu książek ma możliwość wybrania interesującej go książki oraz sprawdzanie dodatkowych informacji o niej – ISBN, status, data dodania, szczegółowy opis - CLIENT
- [X] Stworzenie konta pracowniczego, dezaktywacja lub jego edycja
- [ ] Statystyka wypożyczeń (najczęściej wypożyczane książki w danym okresie czasu) - CLIENT
- [X] Sprawdzenie historii wypożyczeń konkretnych książek - EMPLOYEE
- [X] Dla każdego użytkownika dostępna będzie historia wypożyczeń m. in. z możliwością sortowania wg długości wypożyczenia - CLIENT
- [ ] Sprawdzanie stanu książek (czy i do kiedy są wypożyczone/zarezerwowane / dostępne do wypożyczenia) - EMPLOYEE
- [ ] Podgląd pracowników, innych placówek naszej biblioteki - CLIENT
- [X] Definiowanie kary za przekroczenie limitu oddania książki do biblioteki - EMPLOYEE
- [ ] Zarządzanie kontami członkowskimi (m. in. blokada w przypadku przekroczenia terminu wypożyczenia) - EMPLOYEE
- [ ] Możliwe będzie zamówienie książki, tj. w przypadku, gdy książka nie będzie dostępna, możliwe będzie wyrażenie chęci wypożyczenia i dopisania się do kolejki wypożyczeń - CLIENT
- [ ] Możliwość uregulowania kar za przekroczenie terminu oddania książki - CLIENT
- [ ] Możliwość przedłużenia wypożyczenia, najwyżej jeden raz, jeżeli klient do tej pory się uregulował z wypożyczeniami, i nie ma oczekujących na wypożyczenie - CLIENT
- [X] Stworzenie konta bibliotecznego, usunięcie go lub jego edycja - CLIENT
- [X] Podgląd wypożyczonych obecnie pozycji - CLIENT
- [ ] Możliwość tworzenia listy pozycji, które chce się przeczytać - CLIENT
- [X] Możliwość oceniania wypożyczonych książek - CLIENT
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

