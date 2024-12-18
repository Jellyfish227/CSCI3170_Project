@echo off
REM 
cd /d "%~dp0"

REM
javac -cp ".;../lib/ojdbc8.jar" *.java

REM 
if %ERRORLEVEL% EQU 0 (
    java -cp ".;../lib/ojdbc8.jar" Main
) else (
    echo Compilation failed!
    pause
)

pause