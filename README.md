# Loader
## Synopsis

Dieses Project dient dem Zweck Dateien von Websiten zu laden. Um die relativ flexible zu gestalten wird es über eine Config-File gesteuert.

## Motivation

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## Installation

todo

## API Reference

- Gson: laden der Config-File (Json-Format)
- Jsoup: parsen von HTML

## Tests

todo

## Config

```
java -jar ('path')\hiesp.jar ('path')\hiesp1.conf
```

Config is written and parsed as Json object: here is an example:
```
{
  "debug": true,
  "Path": "D:\\Alexey\\Hiesp-Jar\\hiespConfig\\",
  "FileName": "HIESP",
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
