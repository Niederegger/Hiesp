# Loader
## Synopsis and Motivation

This project is supposed to be used as a download tool. Loader listens to Config dependencies. You are able to adjust the Path, RelRegex (more informations later on), etc.. from within the config file, without needing to adjust your code or recompile anything.

## Installation

You can find a precompiled version here: 'Loader/loader/preCompiled/'. Follow the next steps to run this program:
1. Use the precompiled Jar or compile your own one.
2. Choose a directory for your downloads.
3. Adjust your config file (more information down below). // take your time to setup the config correctly
4. Execute: 
```
...>java -jar Loader.jar loaderAlleHandelbareInstrumente.conf
```

## API Reference

- Gson: working with config file (it's written as  a Json-Object)
- Jsoup: parsing of HTML

## Tests

todo

## Config

Config is written as a Json-Object, here is an example:
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
Definition of these Values:

| variable      | explanation |
| ------------- | --------- |
| debug | switches some prints on and off (debug prints) |
| Path  | destination of your download |
| FileName  | under which name this file should be stored |
| Ending  | filename extension |
| WebSite  | website of your wanted file |
| Rel  | Regex representing the text which is connected to the download |
| logName  | currently obsolete |
| DateRegex  | Regex representing the date |
| dateOrder  | changes the date order, you can choose whether it's yyyymmdd or ddmmyyy |

Attention! : 
- Since this program is written in Java, you have to use doubled backslashes instead of single ones, cause it's an escape character in Java.
- Be carefull with special characters like: { ä, ö, ü, Ä, Ö, Ü} when using regex. It's recommended to test your Regex in a simple Java program, runnning from console before using your Regex with this program.
