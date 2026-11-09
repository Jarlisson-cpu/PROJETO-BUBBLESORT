# PROJETO BUBBLESORT – ESTRUTURA DE DADOS S.A.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Algoritmo](https://img.shields.io/badge/algoritmo-Bubble%20Sort-blue)
![Testes](https://img.shields.io/badge/testes-9%2F9%20OK-brightgreen)

Sistema em Java que lê **1.000.000 de empresas** do arquivo `Empresas_BubbleSort.txt`, ordena os registros pelo **valor de mercado (do maior para o menor)** com o algoritmo **Bubble Sort** e grava o resultado em `Empresas_Ordenadas.txt`.

> Projeto da disciplina **Estrutura de Dados**, Ciência da Computação, Universidade Tiradentes (UNIT).

## 👥 Integrantes
- **JARLISSON BRITO SANTOS**
- **BRUNNO PASSOS**
- **LUIZ GUSTAVO**

---

## 📁 Estrutura do repositório

```
PROJETO-BUBBLESORT/
├── src/                     # Código-fonte Java
│   ├── Empresa.java         # Modelo do registro (nome, IE, valor)
│   ├── BubbleSort.java      # Algoritmo de ordenação
│   ├── ArquivoEmpresas.java # Leitura e gravação dos TXT
│   ├── Main.java            # Programa principal
│   └── TesteBubbleSort.java # Testes automáticos
├── testes/                  # Saídas reais dos testes e da execução
├── docs/                    # Documentação (PDF) e enunciado
├── dados/                   # Arquivo de entrada e resultado (compactados)
├── compilar_e_executar.sh   # Script Linux/macOS
└── compilar_e_executar.bat  # Script Windows
```

## 📄 Formato do arquivo

Cada linha do arquivo é um registro:
```
Nome Fantasia | Inscrição Estadual | Valor de Mercado
Empresa 1 | 67177221-9 | 652069.19
```

## ⚙️ Como executar

**Pré-requisito:** Java 17 ou superior (o projeto foi testado no JDK 21).

1. Extraia o arquivo `dados/Empresas_BubbleSort.zip` na raiz do projeto.
2. Compile e execute:

```bash
javac -encoding UTF-8 -d out src/*.java

# testes automáticos
java -cp out TesteBubbleSort Empresas_BubbleSort.txt

# ordenação completa (1.000.000 de registros)
java -Xmx2g -cp out Main Empresas_BubbleSort.txt Empresas_Ordenadas.txt

# teste rápido só com os 10.000 primeiros registros
java -cp out Main Empresas_BubbleSort.txt Saida_10mil.txt 10000
```

Os scripts `compilar_e_executar.sh` (Linux/macOS) e `compilar_e_executar.bat` (Windows) fazem tudo isso automaticamente.

## 🫧 Como funciona o Bubble Sort

O algoritmo compara cada empresa com a vizinha da direita. Se a da esquerda tiver valor **menor**, as duas trocam de lugar. A cada passada, o menor valor que ainda está fora do lugar "afunda" até o fim do vetor. As passadas se repetem até nenhuma troca acontecer.

**Otimizações** (o algoritmo continua sendo Bubble Sort):
1. **Parada antecipada:** se uma passada não faz nenhuma troca, a lista já está ordenada.
2. **Limite da última troca:** a próxima passada só vai até a posição da última troca, porque o que vem depois já está no lugar certo.
3. **Valores em centavos (`long`):** as comparações não sofrem com erros de arredondamento do `double`.
4. **Vetor auxiliar de valores:** as comparações acontecem num `long[]` contíguo na memória, o que deixa o programa bem mais rápido.

| Caso | Complexidade |
|---|---|
| Melhor (já ordenado) | O(n) |
| Médio / Pior | O(n²) |

## 📊 Resultados

| Registros | Comparações | Trocas | Tempo |
|---:|---:|---:|---:|
| 10.000 | 49.964.098 | 25.044.468 | 0,21 s |
| 50.000 | 1.249.848.657 | 627.826.002 | 6,00 s |
| 100.000 | 4.999.208.836 | 2.508.577.389 | 22,13 s |
| 200.000 | 19.999.249.012 | 10.008.384.923 | 183,23 s |
| **1.000.000** | **499.996.540.252** | **250.159.742.115** | **75,2 min** |

**Maior valor:** Empresa 96433 (9.999.993,82). **Menor valor:** Empresa 339426 (100.012,64).

## ✅ Testes

São 9 testes automáticos, e todos passaram. Eles verificam o exemplo do enunciado, os casos extremos, a estabilidade, o pior caso, a comparação com `Arrays.sort` e uma amostra do arquivo real. Veja a pasta [`testes/`](testes/).

## 📚 Documentação

- [Documentação completa (PDF)](docs/Documentacao_BubbleSort.pdf)
- [Enunciado da atividade](docs/Enunciado_Lista_2_BubbleSort.pdf)
