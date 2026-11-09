// PROJETO BUBBLESORT - ESTRUTURA DE DADOS S.A.
// Integrantes: JARLISSON BRITO SANTOS, BRUNNO PASSOS E LUIZ GUSTAVO

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Testes do sistema (sem bibliotecas externas). Cada teste imprime OK ou FALHOU.
 * Uso: java TesteBubbleSort [arquivoEmpresas]
 */
public class TesteBubbleSort {
    private static int ok = 0, falhas = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("=== Testes do Bubble Sort ===");
        testeExemploDoEnunciado();
        testeVetorVazioEUmElemento();
        testeJaOrdenado();
        testeOrdemInversa();
        testeValoresIguaisMantemOrdem();
        testeConversaoCentavos();
        testeAleatorioComparadoComJava();
        testeLeituraEGravacao();
        Path arquivoReal = localizarArquivoReal(args);
        if (arquivoReal != null) testeAmostraDoArquivoReal(arquivoReal);
        else System.out.println("[AVISO]  Empresas_BubbleSort.txt não encontrado: teste com o arquivo real não executado");
        System.out.printf("%nResultado: %d OK, %d FALHA(S)%n", ok, falhas);
        if (falhas > 0) System.exit(1);
    }

    private static void verificar(String nome, boolean condicao) {
        if (condicao) { ok++; System.out.println("[OK]     " + nome); }
        else { falhas++; System.out.println("[FALHOU] " + nome); }
    }

    private static boolean estaDecrescente(Empresa[] v) {
        for (int i = 0; i + 1 < v.length; i++)
            if (v[i].getValorCentavos() < v[i + 1].getValorCentavos()) return false;
        return true;
    }

    private static void testeExemploDoEnunciado() {
        Empresa[] v = {
            Empresa.deLinha("Empresa A | 12345678-9 | 5000000"),
            Empresa.deLinha("Empresa B | 98765432-1 | 3000000"),
            Empresa.deLinha("Empresa C | 11223344-5 | 7000000")};
        new BubbleSort().ordenarDecrescente(v);
        verificar("Exemplo do enunciado -> C, A, B",
            v[0].getNomeFantasia().equals("Empresa C") && v[1].getNomeFantasia().equals("Empresa A")
            && v[2].getNomeFantasia().equals("Empresa B"));
    }

    private static void testeVetorVazioEUmElemento() {
        Empresa[] vazio = {};
        new BubbleSort().ordenarDecrescente(vazio);
        Empresa[] um = { Empresa.deLinha("X | 1 | 10.00") };
        new BubbleSort().ordenarDecrescente(um);
        verificar("Vetor vazio e vetor com 1 elemento não quebram", vazio.length == 0 && um.length == 1);
    }

    private static void testeJaOrdenado() {
        Empresa[] v = gerar(1000, 1);
        Arrays.sort(v, Comparator.comparingLong(Empresa::getValorCentavos).reversed());
        BubbleSort b = new BubbleSort();
        b.ordenarDecrescente(v);
        verificar("Vetor já ordenado: 1 passada e 0 trocas (melhor caso O(n))",
            estaDecrescente(v) && b.getPassadas() == 1 && b.getTrocas() == 0);
    }

    private static void testeOrdemInversa() {
        int n = 1000;
        Empresa[] v = new Empresa[n];
        for (int i = 0; i < n; i++) v[i] = new Empresa("E" + i, "0-0", String.valueOf(i));
        BubbleSort b = new BubbleSort();
        b.ordenarDecrescente(v);
        verificar("Ordem inversa (pior caso): n(n-1)/2 trocas = " + (long) n * (n - 1) / 2,
            estaDecrescente(v) && b.getTrocas() == (long) n * (n - 1) / 2);
    }

    private static void testeValoresIguaisMantemOrdem() {
        Empresa[] v = {
            Empresa.deLinha("Primeira | 1 | 100.00"),
            Empresa.deLinha("Maior | 2 | 200.00"),
            Empresa.deLinha("Segunda | 3 | 100.00")};
        new BubbleSort().ordenarDecrescente(v);
        verificar("Valores iguais mantêm a ordem original (algoritmo estável)",
            v[0].getNomeFantasia().equals("Maior") && v[1].getNomeFantasia().equals("Primeira")
            && v[2].getNomeFantasia().equals("Segunda"));
    }

    private static void testeConversaoCentavos() {
        verificar("Conversão de valores para centavos",
            Empresa.converterParaCentavos("652069.19") == 65206919L
            && Empresa.converterParaCentavos("5000000") == 500000000L
            && Empresa.converterParaCentavos("10.5") == 1050L
            && Empresa.converterParaCentavos("0.07") == 7L);
    }

    private static void testeAleatorioComparadoComJava() {
        Empresa[] v = gerar(20_000, 42);
        Empresa[] esperado = v.clone();
        Arrays.sort(esperado, Comparator.comparingLong(Empresa::getValorCentavos).reversed()); // estável
        new BubbleSort().ordenarDecrescente(v);
        verificar("20.000 registros aleatórios: resultado idêntico ao Arrays.sort do Java",
            Arrays.equals(v, esperado));
    }

    private static void testeLeituraEGravacao() throws Exception {
        Path tmp = Files.createTempFile("empresas", ".txt");
        Files.writeString(tmp, "Empresa A | 12345678-9 | 5000000\n\nEmpresa B | 98765432-1 | 3000000\nlinha invalida\n");
        Empresa[] v = ArquivoEmpresas.ler(tmp, 0);
        Path saida = Files.createTempFile("saida", ".txt");
        ArquivoEmpresas.gravar(saida, v);
        String conteudo = Files.readString(saida).replace("\r\n", "\n");
        verificar("Leitura ignora linhas vazias/inválidas e gravação mantém o formato",
            v.length == 2 && conteudo.equals("Empresa A | 12345678-9 | 5000000\nEmpresa B | 98765432-1 | 3000000\n"));
        Files.deleteIfExists(tmp);
        Files.deleteIfExists(saida);
    }

    private static void testeAmostraDoArquivoReal(Path arquivo) throws Exception {
        Empresa[] v = ArquivoEmpresas.ler(arquivo, 50_000);
        Empresa[] esperado = v.clone();
        Arrays.sort(esperado, Comparator.comparingLong(Empresa::getValorCentavos).reversed());
        long t = System.nanoTime();
        new BubbleSort().ordenarDecrescente(v);
        double s = (System.nanoTime() - t) / 1e9;
        verificar(String.format("Primeiros 50.000 registros do arquivo real ordenados corretamente (%.2f s)", s),
            estaDecrescente(v) && Arrays.equals(v, esperado));
    }

    /**
     * Procura o arquivo real: primeiro o caminho passado como argumento, depois
     * Empresas_BubbleSort.txt na pasta do projeto. Se não existir, extrai de dados/Empresas_BubbleSort.zip.
     */
    private static Path localizarArquivoReal(String[] args) throws Exception {
        if (args.length > 0) return Path.of(args[0]);
        Path txt = Path.of("Empresas_BubbleSort.txt");
        if (Files.exists(txt)) return txt;
        Path zip = Path.of("dados", "Empresas_BubbleSort.zip");
        if (!Files.exists(zip)) return null;
        System.out.println("Extraindo " + zip + " ...");
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry entrada;
            while ((entrada = zin.getNextEntry()) != null) {
                if (entrada.getName().endsWith("Empresas_BubbleSort.txt")) {
                    Files.copy(zin, txt);
                    return txt;
                }
            }
        }
        return null;
    }

    private static Empresa[] gerar(int n, long semente) {
        Random r = new Random(semente);
        Empresa[] v = new Empresa[n];
        for (int i = 0; i < n; i++) {
            long centavos = r.nextInt(1_000_000_000);
            v[i] = new Empresa("Empresa " + i, String.format("%08d-%d", r.nextInt(100_000_000), r.nextInt(10)),
                    (centavos / 100) + "." + String.format("%02d", centavos % 100));
        }
        return v;
    }
}
