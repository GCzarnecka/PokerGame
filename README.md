<div align='center'>

<h2> POKER </h2>
Poker dobierany. Projekt gry karcianej dla 2-5 osób.
</div>
<br>


<details>
  <summary>Spis treści</summary>
  <ol>
    <li>
      <a href="#o-projekcie">O projekcie</a>
      <ul>
        <li><a href="#wykorzystane-technologie">Wykorzystane technologie</a></li>
      </ul>
    </li>
    <li>
      <a href="#na-czym-polega">Na czym polega</a>
    </li>
    <li>
     <a href="#instalacja">Instalacja</a>
    </li>
    <li>
     <a href="#sposób-użycia">Sposób użycia</a>
    </li>
  </ol>
</details>


## O projekcie

Projekt został stworzony z wykorzystaniem gniazd komunikacji sieciowej
w Javie. Umożliwia równoczesne połączenie się klientów z serwerem i komunikacje z nim.

#### Wykorzystane technologie:
* [Maven](https://maven.apache.org/)
* [SonarQube](https://www.sonarqube.org/)

## Na czym polega

Celem gry jest zdobywanie pieniędzy. Gracz dąży do zdobywania jak najbardziej wartościowych układów kart ich poprzez wymianę.

Na początku od każdego z graczy pobierana jest opłata początkowa (ante) umożliwiająca wzięcie udziału w rozgrywce.
Następnie zaczyna się pierwsza licytacja, po której następuje wymiana kart i druga licytacja.
Po drugiej licytacji, jest sprawdzana wartość kart każdego z graczy i ustalany jest wygrany

## Instalacja

1. Upewnij się, czy masz zainstalowane [JDK](https://adoptium.net/)
2. Sklonuj projekt
   ```  <-- usuń te backshlashe potem
   git clone https://github.com/GCzarnecka/poker.git
   ```

## Sposób użycia

1. Uruchamiamy klasę `Server` znajdującą się w module `poker-server`.
2. Następnie uruchamiamy pierwszego klienta za pomocą `java Client`. Pierwszy klient dostaje pytania o liczbę graczy.
3. Kolejnym krokiem jest uruchomienie odpowiednią ilość razy `java Client` (żeby dołączyła odpowiednia ilość graczy)


