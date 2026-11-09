// PROJETO BUBBLESORT - ESTRUTURA DE DADOS S.A.
// Integrantes: JARLISSON BRITO SANTOS, BRUNNO PASSOS E LUIZ GUSTAVO

/**
 * Implementação do Bubble Sort para ordenar empresas pelo valor de mercado,
 * do MAIOR para o MENOR.
 *
 * Ideia do algoritmo: percorre o vetor comparando vizinhos (j e j+1). Se o da
 * esquerda for MENOR que o da direita, eles trocam de lugar. A cada passada o
 * menor valor ainda fora do lugar "afunda" até o fim do vetor.
 *
 * Otimizações (continua sendo Bubble Sort):
 *  1. Se uma passada inteira não fizer nenhuma troca, o vetor já está ordenado e paramos.
 *  2. Guardamos a posição da última troca: tudo depois dela já está no lugar certo,
 *     então a próxima passada só vai até ali.
 *  3. As comparações são feitas num vetor de long (valores em centavos) que anda junto
 *     com o vetor de empresas. Isso evita acessar cada objeto na memória a cada comparação
 *     e deixa o programa bem mais rápido com 1.000.000 de registros.
 */
public class BubbleSort {

    /** Contadores para mostrar o trabalho feito pelo algoritmo. */
    private long comparacoes;
    private long trocas;
    private long passadas;

    /** Se true, mostra no console o andamento da ordenação a cada 5%. */
    private boolean mostrarProgresso = false;

    public void setMostrarProgresso(boolean mostrar) { this.mostrarProgresso = mostrar; }

    public void ordenarDecrescente(Empresa[] empresas) {
        comparacoes = 0;
        trocas = 0;
        passadas = 0;
        int n = empresas.length;
        if (n < 2) return;

        long[] valores = new long[n];
        for (int i = 0; i < n; i++) {
            valores[i] = empresas[i].getValorCentavos();
        }

        int limite = n - 1;          // última posição que ainda precisa ser comparada
        int proximoAviso = 5;        // próximo percentual a ser mostrado
        long inicio = System.nanoTime();
        while (limite > 0) {
            int ultimaTroca = 0;
            passadas++;
            for (int j = 0; j < limite; j++) {
                if (valores[j] < valores[j + 1]) {   // ordem decrescente: o maior vai para a esquerda
                    long tmpValor = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = tmpValor;

                    Empresa tmpEmpresa = empresas[j];
                    empresas[j] = empresas[j + 1];
                    empresas[j + 1] = tmpEmpresa;

                    ultimaTroca = j;
                    trocas++;
                }
            }
            comparacoes += limite;
            limite = ultimaTroca;    // se não houve troca, ultimaTroca = 0 e o laço termina

            if (mostrarProgresso) {
                // o trabalho restante é proporcional a limite², então o feito é 1 - (limite/n)²
                double r = (double) limite / n;
                int pct = (int) ((1 - r * r) * 100);
                if (pct >= proximoAviso && limite > 0) {
                    double seg = (System.nanoTime() - inicio) / 1e9;
                    System.out.printf("  %3d%% concluído (%.1f min decorridos, ~%.1f min restantes)%n",
                            pct, seg / 60, seg / pct * (100 - pct) / 60);
                    proximoAviso = (pct / 5 + 1) * 5;
                }
            }
        }
    }

    public long getComparacoes() { return comparacoes; }
    public long getTrocas() { return trocas; }
    public long getPassadas() { return passadas; }
}
