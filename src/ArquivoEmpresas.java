// PROJETO BUBBLESORT - ESTRUTURA DE DADOS S.A.
// Integrantes: JARLISSON BRITO SANTOS, BRUNNO PASSOS E LUIZ GUSTAVO

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Leitura e gravação dos arquivos TXT no formato "Nome | IE | Valor". */
public class ArquivoEmpresas {

    /**
     * Lê o arquivo de entrada. Linhas em branco são ignoradas.
     * @param limite quantidade máxima de registros a ler (0 = todos)
     */
    public static Empresa[] ler(Path arquivo, int limite) throws IOException {
        List<Empresa> lista = new ArrayList<>(limite > 0 ? limite : 1_000_000);
        try (BufferedReader leitor = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;
            int numeroLinha = 0;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                if (linha.isBlank()) continue;
                try {
                    lista.add(Empresa.deLinha(linha));
                } catch (IllegalArgumentException e) {
                    System.err.println("Aviso: linha " + numeroLinha + " ignorada (" + e.getMessage() + ")");
                }
                if (limite > 0 && lista.size() >= limite) break;
            }
        }
        return lista.toArray(new Empresa[0]);
    }

    /** Grava os registros, um por linha, no mesmo formato da entrada. */
    public static void gravar(Path arquivo, Empresa[] empresas) throws IOException {
        try (BufferedWriter escritor = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
            for (Empresa e : empresas) {
                escritor.write(e.paraLinha());
                escritor.newLine();
            }
        }
    }
}
