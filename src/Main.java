// PROJETO BUBBLESORT - ESTRUTURA DE DADOS S.A.
// Integrantes: JARLISSON BRITO SANTOS, BRUNNO PASSOS E LUIZ GUSTAVO

import java.nio.file.Path;

/**
 * Programa principal.
 * Uso: java Main [arquivoEntrada] [arquivoSaida] [limiteDeRegistros]
 * Padrões: Empresas_BubbleSort.txt  Empresas_Ordenadas.txt  0 (todos)
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Path entrada = Path.of(args.length > 0 ? args[0] : "Empresas_BubbleSort.txt");
        Path saida   = Path.of(args.length > 1 ? args[1] : "Empresas_Ordenadas.txt");
        int limite   = args.length > 2 ? Integer.parseInt(args[2]) : 0;

        System.out.println("=== Estrutura de Dados S.A. - Ordenação por Valor de Mercado (Bubble Sort) ===");
        System.out.println("Entrada: " + entrada.toAbsolutePath());

        long t0 = System.nanoTime();
        Empresa[] empresas = ArquivoEmpresas.ler(entrada, limite);
        long t1 = System.nanoTime();
        System.out.printf("Registros lidos: %,d (%.2f s)%n", empresas.length, (t1 - t0) / 1e9);

        System.out.println("Ordenando com Bubble Sort (maior -> menor)... isso pode demorar.");
        BubbleSort sorter = new BubbleSort();
        sorter.ordenarDecrescente(empresas);
        long t2 = System.nanoTime();
        System.out.printf("Ordenação concluída em %.2f s (%.2f min)%n", (t2 - t1) / 1e9, (t2 - t1) / 60e9);
        System.out.printf("Passadas: %,d | Comparações: %,d | Trocas: %,d%n",
                sorter.getPassadas(), sorter.getComparacoes(), sorter.getTrocas());

        ArquivoEmpresas.gravar(saida, empresas);
        long t3 = System.nanoTime();
        System.out.printf("Arquivo gravado: %s (%.2f s)%n", saida.toAbsolutePath(), (t3 - t2) / 1e9);

        int mostrar = Math.min(5, empresas.length);
        System.out.println("\nTop " + mostrar + " maiores valores:");
        for (int i = 0; i < mostrar; i++) System.out.println("  " + (i + 1) + ". " + empresas[i]);
        System.out.println("Menores valores:");
        for (int i = Math.max(0, empresas.length - mostrar); i < empresas.length; i++)
            System.out.println("  " + (i + 1) + ". " + empresas[i]);
        System.out.printf("%nTempo total: %.2f s%n", (t3 - t0) / 1e9);
    }
}
