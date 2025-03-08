# [EN] Application for managing books in a library
Project in Java.

## Installation in Linux environment:
To run the application, you must have the following programs installed:
- Java JDK17, suggested liberica17 Bellsoft or OracleJDK, as these have a built-in JavaFX component (sample instructions: https://linux.how2shout.com/steps-to-install-openjdk-17-ubuntu-linux-such-as-22-04-or-20-04/)
- Maven (sample instructions: https://phoenixnap.com/kb/install-maven-on-ubuntu)
- Docker (sample installation: https://docs.docker.com/engine/install/ubuntu/)
- JavaFX (sample installation: https://openjfx.io/openjfx-docs/)

The application is run as follows:
- download the contents of the repository (git clone https://github.com/Duszke333/library-system.git)
- go to the directory where you downloaded the contents of the repository, and then run the script with the command `python3 pap.py --install`
- the script will build the database in docker, install liberica17, and then run the application

## Team members
- Piotr Jabłoński
- Paweł Wysocki
- Jabub Kośla
- Bruno Sienkiewicz

## Documentation

1. ### Subject: Application for managing books in the library

We plan to create an application that will support both readers (the CLIENT part of the project, it will be possible to browse collections, order them, etc.) and library employees (the EMPLOYEE part of the project, it will be possible to manage collections, etc.).

2. ### Technologies
- Backend: **Java**
- Frontend: **JavaFX**
- Database: **PostgreSQL**

3. ### Functionalities
- [X] Ability to browse the book catalog, details about books such as author, year of publication, number of pages, type of cover, awards won… - CLIENT
- [X] Filtering the preview by name, year, author… - CLIENT
- [X] Managing books, editing books, deleting, adding book records - EMPLOYEE
- [X] Creating a loan queue, setting a general limit, a specific limit per book - EMPLOYEE
- [X] Displaying books: Cover/Detailed description with details – from the book catalog level, the user can select the book they are interested in and check additional information about it – ISBN, status, date added, detailed description - CLIENT
- [X] Creating an employee account, deactivating or editing it
- [X] Statistics loans (most frequently borrowed books in a given period of time) - CLIENT
- [X] Checking the loan history of specific books - EMPLOYEE
- [X] Each user will have access to the loan history, including: with the option of sorting by loan length - CLIENT
- [X] Checking the status of books (if and until when they are loaned/reserved/available for loan) - EMPLOYEE
- [X] Viewing employees, other branches of our library - CLIENT
- [X] Defining a penalty for exceeding the limit of returning a book to the library - EMPLOYEE
- [X] Managing member accounts (including blocking in the event of exceeding the loan deadline) - EMPLOYEE
- [X] It will be possible to order a book, i.e. if the book is not available, it will be possible to express a desire to borrow and join the loan queue - CLIENT
- [X] Possibility to pay penalties for exceeding the book return deadline - CLIENT
- [X] Possibility to extend the loan, at most once, if the customer has already settled the loans and there are no waiting loans - CLIENT
- [X] Creating, deleting or editing a library account - CLIENT
- [X] Viewing currently borrowed items - CLIENT
- [X] Ability to create a list of items you want to read - CLIENT
- [X] Ability to rate borrowed books - CLIENT
- [X] Ability to report damage or loss of a book - CLIENT

## Database

Building the database:
- in the terminal `docker compose up --build` in the location of the docker-compose.yml file
- if we already have a built database, `docker compose up` is enough
- The var folder should be created

The database will be available at `localhost:5432`
- login: `postgres`
- password: `postgres`
- database: `pap`
- the database has the `pap` schema, which stores tables

In the Docker folder there is a script `init.sql` that creates the database.
It can be changed to adjust the initial content of the database.

# [PL] Aplikacja do zarządzania książkami w bibliotece
Projekt w języku Java.

## Instalacja w środowisku linuksowym:
Aby uruchomić aplikację należy mieć zainstalowane następujące programy:
- Java JDK17, proponowana liberica17 Bellsoft lub OracleJDK, gdyż te mają wbudowany komponent JavaFX (przykłowa instrukcja: https://linux.how2shout.com/steps-to-install-openjdk-17-ubuntu-linux-such-as-22-04-or-20-04/)
- Maven (przykładowa instrukcja: https://phoenixnap.com/kb/install-maven-on-ubuntu)
- Docker (przykładowa instalacja: https://docs.docker.com/engine/install/ubuntu/)
- JavaFX (przykładowa instalacja: https://openjfx.io/openjfx-docs/)

Aplikację uruchamia się w następujący sposób:
- należy ściągnąć zawartość repozytorium (git clone https://github.com/Duszke333/library-system.git)
- należy przejść do katalogu, do którego ściągnięto zawartość repozytorium, a następnie uruchomić skrypt komendą `python3 pap.py --install`
- skrypt zbuduje bazę w dockerze, zainstaluję liberica17, a następnie uruchomi aplikację

## Członkowie zespołu
- Jabłoński Piotr
- Wysocki Paweł
- Kośla Jabub
- Sienkiewicz Bruno

## Dokumentacja

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
- [X] Tworzenie kolejki wypożyczeń, ustawienie ogólnego limitu, konkretny limit per książka - EMPLOYEE
- [X] Wyświetlanie książek: Okładka/Szczegółowy opis z detalami – użytkownik z poziomu katalogu książek ma możliwość wybrania interesującej go książki oraz sprawdzanie dodatkowych informacji o niej – ISBN, status, data dodania, szczegółowy opis - CLIENT
- [X] Stworzenie konta pracowniczego, dezaktywacja lub jego edycja
- [X] Statystyka wypożyczeń (najczęściej wypożyczane książki w danym okresie czasu) - CLIENT
- [X] Sprawdzenie historii wypożyczeń konkretnych książek - EMPLOYEE
- [X] Dla każdego użytkownika dostępna będzie historia wypożyczeń m. in. z możliwością sortowania wg długości wypożyczenia - CLIENT
- [X] Sprawdzanie stanu książek (czy i do kiedy są wypożyczone/zarezerwowane / dostępne do wypożyczenia) - EMPLOYEE
- [X] Podgląd pracowników, innych placówek naszej biblioteki - CLIENT
- [X] Definiowanie kary za przekroczenie limitu oddania książki do biblioteki - EMPLOYEE
- [X] Zarządzanie kontami członkowskimi (m. in. blokada w przypadku przekroczenia terminu wypożyczenia) - EMPLOYEE
- [X] Możliwe będzie zamówienie książki, tj. w przypadku, gdy książka nie będzie dostępna, możliwe będzie wyrażenie chęci wypożyczenia i dopisania się do kolejki wypożyczeń - CLIENT
- [X] Możliwość uregulowania kar za przekroczenie terminu oddania książki - CLIENT
- [X] Możliwość przedłużenia wypożyczenia, najwyżej jeden raz, jeżeli klient do tej pory się uregulował z wypożyczeniami, i nie ma oczekujących na wypożyczenie - CLIENT
- [X] Stworzenie konta bibliotecznego, usunięcie go lub jego edycja - CLIENT
- [X] Podgląd wypożyczonych obecnie pozycji - CLIENT
- [X] Możliwość tworzenia listy pozycji, które chce się przeczytać - CLIENT
- [X] Możliwość oceniania wypożyczonych książek - CLIENT
- [X] Możliwość zgłoszenia uszkodzenia lub zgubienia książki - CLIENT

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

