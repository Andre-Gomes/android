package cadastrofilmes.wpos.com.br.cadastrofilmes.model;

import java.io.Serializable;

/**
 * Created by gomes on 16/04/2017.
 */

public class Filme implements Serializable {
    private Integer codigo;
    private String titulo;
    private String diretor;
    private Integer anoLancamento;
    private Genero genero;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Cód: " + getCodigo() + ", Título: " + getTitulo() + " Diretor: " + getDiretor() + ", Ano: " + getAnoLancamento() + ", Gênero: " + getGenero().getNome();
    }
}
