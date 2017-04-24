package cadastrofilmes.wpos.com.br.cadastrofilmes.model;

import java.io.Serializable;

/**
 * Created by gomes on 16/04/2017.
 */

public class Genero implements Serializable {
    private Integer codigo;
    private String nome;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getCodigo() + " - " + getNome();
    }
}
