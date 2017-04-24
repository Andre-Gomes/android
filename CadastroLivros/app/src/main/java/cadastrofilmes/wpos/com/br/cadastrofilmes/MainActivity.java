package cadastrofilmes.wpos.com.br.cadastrofilmes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.util.List;

import cadastrofilmes.wpos.com.br.cadastrofilmes.dao.FilmeDAO;
import cadastrofilmes.wpos.com.br.cadastrofilmes.model.Filme;
import cadastrofilmes.wpos.com.br.cadastrofilmes.model.Genero;

public class MainActivity extends AppCompatActivity {

    private EditText titulo;
    private Spinner genero;
    private EditText ano;
    private EditText diretor;


    private FilmeDAO filmeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.titulo = (EditText) findViewById(R.id.editTextTitulo);
        this.ano = (EditText) findViewById(R.id.editTextAno);
        this.diretor = (EditText) findViewById(R.id.editTextDiretor);
        this.genero = ((Spinner) findViewById(R.id.spinnerGenero));

        this.filmeDAO = new FilmeDAO(this);
        List generos = this.filmeDAO.buscaGeneros();
        Log.d("Generos: ", generos.toString());

        ArrayAdapter<Genero> adapter = new ArrayAdapter<Genero>(this, android.R.layout.simple_list_item_1, generos);
        genero.setAdapter(adapter);

        Log.d("Filmes: ", filmeDAO.buscaFilmes().toString());
    }

    public void salvarFilme(View v)
    {
        if (titulo.getText().toString().isEmpty() || diretor.getText().toString().isEmpty() || ano.getText().toString().isEmpty()){
            exibirMensagemDeErro("Preencha todos os campos", v);
            return;
        }

        Filme filme = new Filme();
        filme.setTitulo(titulo.getText().toString());
        filme.setDiretor(diretor.getText().toString());
        filme.setAnoLancamento(Integer.valueOf(ano.getText().toString()));
        filme.setGenero((Genero)genero.getSelectedItem());
        filmeDAO.insere(filme);

        exibirMensagemSucesso("Filme cadastrado!", v);
        titulo.getText().clear();
        diretor.getText().clear();
        ano.getText().clear();
        genero.setSelection(0);
        titulo.requestFocus();
    }

    public void verFilmes(View v){
        Intent nextActivity = new Intent(this, ListagemFilmesActivity.class);
        startActivity(nextActivity);
    }

    private void exibirMensagemDeErro(String mensagemErro, View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle("Alerta!")
                .setMessage(mensagemErro)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void exibirMensagemSucesso(String mensagem, View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle("Informação")
                .setMessage(mensagem)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
