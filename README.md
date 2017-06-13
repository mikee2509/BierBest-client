# BierBest

### Założenia
Aplikacja wspiera proces zamawiania piwa przez sieć. Użytkownik korzystając z aplikacji dla klienta, rejestruje się i może złożyć zamówienie na wybrane przez siebie piwo. Następnie właściciel sklepu potwierdza, czy może takie piwo zamówić w hurtowni oraz określa cenę. Klient akceptuje lub odrzuca ofertę.



### Zrealizowane
* logowanie/rejestracja nowego użytkownika
* wyszukiwanie piw w bazie BreweryDB.com
* wyświetlanie widoku szczegółowego piwa
* formularz składania zamówienia z walidacją wprowadzanych danych
* widok listy zamówień z możliwością sprawdzenia statusu zamówienia, akceptacji/odrzucenia ceny
* szyfrowana komunikacja z serwerem za pomocą socketów

 
 
### Uruchomienie programu

Linux: 

    ./gradlew jfxJar
    java -jar ./build/jfx/app/BierBest.jar
    
Windows:

    gradlew.bat jfxJar
    java -jar build\jfx\app\BierBest.jar
 
