# BierBest

### Założenia
Aplikacja wspiera proces zamawiania piwa przez sieć. Użytkownik korzystając z aplikacji dla klienta, rejestruje się i może złożyć zamówienie na wybrane przez siebie piwo. Następnie właściciel sklepu potwierdza, czy może takie piwo zamówić w hurtowni oraz określa cenę. Klient akceptuje lub odrzuca ofertę.



### Do tej pory zrealizowane
Aplikacja pozwala wyszukiwać piwa z bazy BreweryDB.com oraz zapisywać zapytania o cenę do pliku orders.txt

 
 
### Uruchomienie programu

Linux: 

    ./gradlew jfxJar
    java -jar ./build/jfx/app/BierBest.jar
    
Windows:

    gradlew.bat jfxJar
    java -jar ./build/jfx/app/BierBest.jar
 
