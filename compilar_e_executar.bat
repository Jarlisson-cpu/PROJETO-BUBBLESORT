@echo off
REM Compila, roda os testes e ordena o arquivo completo.
cd /d "%~dp0"
if not exist Empresas_BubbleSort.txt powershell -Command "Expand-Archive -Force dados\Empresas_BubbleSort.zip ."
javac -encoding UTF-8 -d out src\*.java
java -cp out TesteBubbleSort Empresas_BubbleSort.txt
java -Xmx2g -cp out Main Empresas_BubbleSort.txt Empresas_Ordenadas.txt %*
pause
