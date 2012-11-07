# Kom igang med YABE

## Oppsett av Play! på Windows
1. Last ned play-2.0.4.zip fra [playframework.org]
2. Pakk ut til en mappe (eks. `c:\workspace\play\play-2.0.4`)
3. Legg til stien til `play-2.0.4` i PATH
4. Hent ut koden (via Git eller last ned Zip) fra [Github]. Pakk evt. ut.
5. Åpne kommandovindu (cmd) og naviger til undermappen `Play--YABE\yabe-play`
6. Start Play med kommando `play`
7. Start applikasjonen: `run`
8. Sjekk at ting funker på http://localhost:9000/
9. Kjør evolution-script dersom database-evolution er nødvendig (det vil det alltid være første gang applikasjonen kjører) (stor rød knapp)
10. Whohoo!
11. (Valgfritt): test at data hentes fra in-mem-databasen: http://localhost:9000/user/aksel@agresvig.com

## Oppsett av IDE (valgfritt)
Filene i YABE kan redigeres i en hvilken som helst editor, og enkelt kompileres med `play compile`. Men dersom du ønsker å jobbe i en IDE som f. eks. Eclipse kan det enkelt gjøres ved å kjøre `play eclipsify` i prosjektmappen, eller `play idea` for IntelliJ.


### IntelliJ
1. Last ned IntelliJ "Community Edition" fra http://www.jetbrains.com/idea/download/ og installer
2. Kjør `play idea` i prosjektmappen fra kommando
3. Åpne prosjektet fra IntelliJ
4. I IntelliJ, spesifiser JDK installasjonsmappe hvis nødvendig i "Project settings" (under "File)
5. Kod i vei!

For å kjøre tester: 

1. Last ned Ebean 2.7.0 fra [Sourceforge] og pakk den ut, f. eks. til ditt workspace
2. Åpne `ModelTest.java` (ctrl-n og skriv inn)
3. Velg "Run" -> "Edit configurations" og trykk på "ModelTest" i listen til venstre
4. I "VM options" feltet (etter det som evt. står der), lim inn følgende der du spesifiserer stien du pakket ut Ebean til:  
`-javaagent:C:\workspace\play\ebean-2.7.0\ebean-2.7.0-agent.jar`
5. Kjør `play test-only` fra kommando
6. Kjør `ModelTest` i IntelliJ!

Får du feilmeldinger som ligner på "`could not find property name`" e.l., så mulig du må kjøre `play clean` og/eller `play test-only` igjen fra kommando. IntelliJ genererer ikke opp gettere/settere selv, det må Play gjøre for deg.


### Eclipse (eksperimentelt, fungerer ikke 100%)
Kjør `play eclipsify`, så: 

1. Last ned v4.2 fra http://www.eclipse.org/downloads/, og pakk ut.
2. Start eclipse.exe og velg workspace som inneholder YABE-prosjektet
3. Velg File -> Import.. -> Existing projects into workspace -> Browse for root directory -> Velg Play--YABE. Klikk Finish

For å kjøre tester fra Eclipse, gjør følgende:  
1. Last ned Ebean 2.7.0 fra [Sourceforge] og pakk den ut, f. eks. til ditt workspace
2. I Eclipse velg Run -> Run configurations..
3. Velg "JUnit" i listen og trykk på "New launch configuration". Velg `yabe-play` prosjektet som "Project" og `models.ModelTest` som "Test Class".
4. Gå til "Arguments"-fanen, og lim inn følgende under "VM options", der du angir stien du pakket ut "Ebean"-jaren til:  
`-javaagent:C:\workspace\play\ebean-2.7.0\ebean-2.7.0-agent.jar`
5. Kopier `testdata.yml` fra `conf`-mappa til `.target`
6. Kjør test!


  
[Sourceforge]:http://sourceforge.net/projects/ebeanorm/files/2.7.0/
[playframework.org]: http://www.playframework.org/download
[Github]: https://github.com/AGresvig/Play--YABE
