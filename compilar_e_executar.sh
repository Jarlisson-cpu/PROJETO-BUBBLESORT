#!/usr/bin/env bash
# Compila, roda os testes e ordena o arquivo completo.
set -e
cd "$(dirname "$0")"
[ -f Empresas_BubbleSort.txt ] || unzip -o dados/Empresas_BubbleSort.zip
javac -encoding UTF-8 -d out src/*.java
java -cp out TesteBubbleSort Empresas_BubbleSort.txt
java -Xmx2g -cp out Main Empresas_BubbleSort.txt Empresas_Ordenadas.txt "$@"
