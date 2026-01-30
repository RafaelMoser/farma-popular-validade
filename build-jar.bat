@echo off
rem Build runnable jar for Farma Popular
setlocal enabledelayedexpansion
set SRC=src
set OUT=out
set CLS=%OUT%\classes
if exist %OUT% rd /s /q %OUT%
mkdir %CLS%
echo Compiling Java sources...
javac -d %CLS% %SRC%\*.java
if errorlevel 1 (
  echo Compilation failed.
  exit /b 1
)
echo Creating jar...
jar cfe %OUT%\farma-popular-validade.jar App -C %CLS% .
if errorlevel 1 (
  echo Jar creation failed.
  exit /b 1
)
echo Created %OUT%\farma-popular-validade.jar
endlocal
