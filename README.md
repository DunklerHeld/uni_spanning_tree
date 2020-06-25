# Labor Netztechnik

## Info
Dieses Projekt wurde in Java mir IntelliJ geschrieben. Eine kompilierte, ausführbare .jar kann im Root-Folder gefunden werden.

## Nutzung
Das Programm arbeitet am Besten mit klaren Ein- und Ausgabedateien, welche als erstes und zweites Argument übergeben werden sollten. Falls keine Ausgabedatei spezifiziert ist, so wird die Ausgabe zu stdout geschrieben.

_Beispiele:_
```Spanning_Tree_Protocol_executable.jar ./inputFile.txt ./outputFile.txt```
```Spanning_Tree_Protocol_executable.jar ./inputFile.txt```

```java -jar Spanning_Tree_Protocol_executable.jar ./inputFile.txt ./outputFile.txt```
```java -jar Spanning_Tree_Protocol_executable.jar ./inputFile.txt```

Falls ein Aufruf ohne gegebenes Argument für eine Eingabedatei getätigt wird, so öffnet das Programm einen FileChooser, falls möglich. Jedoch ist diese Art der Verwendung nicht empfohlen.

## Eingabedatei
Die Eingabedatei sollte folgendes Format besitzen:
```
// Node IDs
A = 5;
B = 1;
C = 3;
D = 7;
E = 6;
F = 4;
// Links mit zugeh. Kosten
A - B : 10;
A - C : 10;
B - D : 15;
B - E : 10;
C - D : 3;
C - E : 10;
D - E : 2;
D - F : 10;
E - F : 2;
```

- Zeilen welche mit '/' beginnen sind Kommentare und werden nicht berücksichtigt
- Eine reguläre Zeile muss mit ';' enden
- Die Reihenfolge der Nodes und Links ist egal
- Es sollten keine Nodes mit gleichem Namen oder gleicher ID vorhanden sein
- Als Namen für Nodes können einzelne Characters verwendet werden
- Als IDs können beliebige Integer genutzt werden (niedrigste ID = root node)
- Es können auch mehr als 2 Nodes über einen Link verbunden sein, jedoch ist diese Funktion sehr experimentell
- Link-Kosten können beliebige Integer Werte sein (es wird von negativen Werten abgeraten)

## Ausgabe
Die Ausgabe, egal ob sie über stdout oder in eine Datei passiert, zeigt je Iteration den aktuellen Netzwerkstatus an. Die Ausgabe für die obere Eingabedatei sieht folgendermaßen aus:
```
Network status after 1 iterations
A -> B; - 10
B -> B; - 0
C -> C; - 0
D -> B; - 15
E -> B; - 10
F -> F; - 0


Network status after 2 iterations
A -> B; - 10
B -> B; - 0
C -> D; - 18
D -> E; - 12
E -> B; - 10
F -> E; - 12


Network status after 3 iterations
A -> B; - 10
B -> B; - 0
C -> D; - 15
D -> E; - 12
E -> B; - 10
F -> E; - 12


Network status after 4 iterations
A -> B; - 10
B -> B; - 0
C -> D; - 15
D -> E; - 12
E -> B; - 10
F -> E; - 12
```

## Autor
Erik Erbslöh
Kurs TINF18E
DHBW Stuttgart
