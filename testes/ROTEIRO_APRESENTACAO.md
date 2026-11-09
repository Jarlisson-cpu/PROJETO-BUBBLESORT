# 🎤 Roteiro de testes para a apresentação

O arquivo `Empresas_BubbleSort.txt` é extraído automaticamente de `dados/` na primeira execução.

| # | O que rodar | Program arguments | Tempo | O que mostrar |
|---|---|---|---|---|
| 1 | `TesteBubbleSort` | *(vazio)* | ~6 s | **9 OK, 0 FALHA(S)** |
| 2 | `Main` | `Empresas_BubbleSort.txt Saida_10mil.txt 10000` | < 1 s | Top 5 maiores e menores, e o arquivo gerado |
| 3 | `Main` | `Empresas_BubbleSort.txt Saida_100mil.txt 100000` | ~25 s | O tempo cresce de forma quadrática (O(n²)) |
| 4 | Abrir `testes/execucao_1milhao.txt` | – | – | Execução completa: 1.000.000 de registros em ~75 min |

**O que cada teste automático prova:**
1. **Exemplo do enunciado:** A, B e C saem na ordem C, A, B.
2. **Vazio e 1 elemento:** os casos extremos não quebram o programa.
3. **Já ordenado:** a parada antecipada funciona (1 passada, 0 trocas, O(n)).
4. **Ordem inversa:** no pior caso, o número de trocas é exatamente n(n−1)/2.
5. **Valores iguais:** o algoritmo é estável e mantém a ordem original dos empates.
6. **Centavos:** "652069.19" é convertido sem erro de arredondamento.
7. **20.000 aleatórios:** o resultado é idêntico ao `Arrays.sort` do Java.
8. **Leitura e gravação:** linhas inválidas são ignoradas. O aviso "linha 4 ignorada" é proposital.
9. **Arquivo real:** os 50.000 primeiros registros reais são ordenados corretamente.
