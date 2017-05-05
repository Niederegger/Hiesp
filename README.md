# Loader
## Synopsis

Dieses Project dient dem Zweck Dateien von Websiten zu laden. Um die relativ flexible zu gestalten wird es über eine Config-File gesteuert.

## Motivation

Um nicht für jede Anforderung specielle Programme schreiben zu müssen wurde dieses Programm erstellt. Die Idee ist es das gewünschte Download Ziel über ein Regex auf der Seite zu Filtern. Durch die Steuerung über eine Config erspart man sich einiges an Compilieren.

## Installation

Es liegt eine pre- kompilierte Jar im Verzeichnis: "Loader/loader/preCompiled/". Diese kann genutzt werden um das Programm auszuführen.
Die Installation ist wie folgt auszuführen.
1. Nutzen Sie die bereits kompilierte Jar oder kompilieren diese selbst.
2. Wählen Sie ein Arveitsverzeichnis und ein Verzeichnis wo ihr Download hin soll.
3. Passen Sie das Verzeichnis in der Config File an (ein Beispiel einer Config liegt in: "Loader/loader/preCompiled/Config/alleHandelbareInstrumente.conf") an folgender Stelle an:
```
"Path": "D:\\ExampleUser\\ExampleLoader\\Destination\\",
```
4. Anschließend muss ein Regex erstellt werden, anhand dessen der Download-Link auf der Webseite gefunden werden kann, hier ein Beispiel:
- Dieser Text befindet sich auf der Seite http://www.xetra.com/xetra-de/instrumente/alle-handelbaren-instrumente/boersefrankfurt, wobei sich das Datum täglich ändert.
```
Alle handelbaren Instrumente exklusive strukturierte Produkte
Xetra (XETR): 05 Mai 2017 02:00
Börse Frankfurt (XFRA): 05 Mai 2017 01:58
```
- Hier ist das passende Regex Beispiel:
´´´
  "Rel": "\\s*(Alle handelbaren Instrumente exklusive strukturierte Produkte\\sXetra \\(XETR\\):\\s*\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d)\\s(B\\p{L}rse Frankfurt \\(XFRA\\):\\s*\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d)\s*",
´´´
'Achtumg:!' Backslash muss jeweils immer doppelt angegeben werden, da es sich um ein Java Programm handelt und Backslash in Java ein escape Zeichen ist. Außerdem ist auf Sonderzeichen wie Umlaut zu achten. Wie Sie sehen wird so aus Börse -> B\\p{L}rse.
5. Passen Sie das File-Ending an, ist Ihre Datei eine csv Datei so geben sie das passend an:
´´´
  "Ending": ".csv",
´´´

Weitere Informationen zur Steuerung über die Config finden Sie weiter unten.

## API Reference

- Gson: laden der Config-File (Json-Format)
- Jsoup: parsen von HTML

## Tests

todo

## Ausführung

```
...>java -jar Loader.jar hiesp.conf
```

## Config

Bei der Config handelt es sich um ein Json Object:
```
{
  "debug": true,
  "Path": "D:\\Alexey\\Hiesp-Jar\\hiespConfig\\",
  "FileName": "alleHandelbareInstrumente",
  "Ending": ".csv",
  "WebSite": "http://www.xetra.com/xetra-de/instrumente/alle-handelbaren-instrumente/boersefrankfurt",
  "Rel": "\\s*(Alle handelbaren Instrumente exklusive strukturierte Produkte\\sXetra \\(XETR\\):\\s*\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d)\\s(B\\p{L}rse Frankfurt \\(XFRA\\):\\s*\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d)\s*",
  "logName": "log.txt",
  "DateRegex": "\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d",
  "dateOrder": [
    2,
    1,
    0,
    3,
    4
  ]
}
```  
| Variable      | Erklärung |
| ------------- | --------- |
| debug | Steuert ob gewisse prints ausgeführt werden sollen  |
| Path  | Das Verzeichnis zum Download  |
| FileName  | unter welchen Namen wird die Datei abgespeichert |
| Ending  | filename extension / Dateinamenserweiterung |
| WebSite  | auf welcher Webseite befindet sich die Datei |
| Rel  | Regex für den Text, der mit der Date verknüpft ist |
| logName  | momentan nicht in Benutzung |
| DateRegex  | Regex für das Datum, befindlich im Text |
| dateOrder  | Hier kann das Datum umformatiert werden (yyy_mm_dd) |
