set RUNTIME=runtime
set RUNTIME=%RUNTIME%;runtime\json.jar
set RUNTIME=%RUNTIME%;runtime\mysql.jar
set RUNTIME=%RUNTIME%;runtime\bobcat.jar
set RUNTIME=%RUNTIME%;runtime\rockstar.jar
set RUNTIME=%RUNTIME%;runtime\freemarker.jar

javac --class-path %RUNTIME% --source-path code code\Start.java

move code\*.class runtime

java --class-path %RUNTIME% Bobcat --home web --port 5200 ^
--deployment-descriptor web.xml
