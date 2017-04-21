# Hiesp
Handelbaren Instrumente exklusive strukturierte Produkte

Folder Eample contains compiled Jar and a demo Config,
attention, you have to adjust path inside demo COnfig!

This is the way to execute this programm:
java -jar ('path')\hiesp.jar ('path')\hiesp1.conf

Config is written and parsed as Json object: here is an example:

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
