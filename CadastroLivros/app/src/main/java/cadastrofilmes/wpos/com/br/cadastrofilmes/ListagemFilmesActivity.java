package cadastrofilmes.wpos.com.br.cadastrofilmes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import cadastrofilmes.wpos.com.br.cadastrofilmes.dao.FilmeDAO;
import cadastrofilmes.wpos.com.br.cadastrofilmes.model.Filme;

public class ListagemFilmesActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ListView filmesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_filmes);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        filmesListView = ((ListView) findViewById(R.id.filmesListView));

        FilmeDAO filmeDAO = new FilmeDAO(this);
        List<Filme> filmes =  filmeDAO.buscaFilmes();
        ArrayAdapter<Filme> adapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1, filmes);
        filmesListView.setAdapter(adapter);
    }
}
