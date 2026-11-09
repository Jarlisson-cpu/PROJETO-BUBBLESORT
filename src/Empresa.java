// PROJETO BUBBLESORT - ESTRUTURA DE DADOS S.A.
// Integrantes: JARLISSON BRITO SANTOS, BRUNNO PASSOS E LUIZ GUSTAVO

/**
 * Representa um registro do arquivo: Nome Fantasia | Inscrição Estadual | Valor de Mercado.
 * O valor é guardado em centavos (long) para comparar sem erros de arredondamento
 * de ponto flutuante, e o texto original é mantido para gravar o arquivo de saída igual à entrada.
 */
public class Empresa {
    private final String nomeFantasia;
    private final String inscricaoEstadual;
    private final String valorTexto;
    private final long valorCentavos;

    public Empresa(String nomeFantasia, String inscricaoEstadual, String valorTexto) {
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.valorTexto = valorTexto;
        this.valorCentavos = converterParaCentavos(valorTexto);
    }

    /** Converte "652069.19" em 65206919 centavos (aceita também vírgula ou sem casas decimais). */
    static long converterParaCentavos(String texto) {
        String t = texto.trim().replace(',', '.');
        int ponto = t.indexOf('.');
        if (ponto < 0) return Long.parseLong(t) * 100;
        long inteiro = ponto == 0 ? 0 : Long.parseLong(t.substring(0, ponto));
        String dec = (t.substring(ponto + 1) + "00").substring(0, 2);
        return inteiro * 100 + Long.parseLong(dec);
    }

    /** Monta uma linha a partir do formato "Nome | IE | Valor". */
    public static Empresa deLinha(String linha) {
        String[] partes = linha.split("\\|");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Linha fora do formato esperado: " + linha);
        }
        return new Empresa(partes[0].trim(), partes[1].trim(), partes[2].trim());
    }

    public String paraLinha() {
        return nomeFantasia + " | " + inscricaoEstadual + " | " + valorTexto;
    }

    public String getNomeFantasia() { return nomeFantasia; }
    public String getInscricaoEstadual() { return inscricaoEstadual; }
    public String getValorTexto() { return valorTexto; }
    public long getValorCentavos() { return valorCentavos; }

    @Override
    public String toString() { return paraLinha(); }
}
