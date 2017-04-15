package calculaprazows.wpos.com.br.calculaprazows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DetalhesPrecoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_preco);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String valor  = intent.getStringExtra("valor");
        String valorMaoPropria  = intent.getStringExtra("valorMaoPropria");
        String valorAvisoRecebimento  = intent.getStringExtra("valorAvisoRecebimento");
        String valorDeclarado  = intent.getStringExtra("valorDeclarado");
        String valorSemAdicionais  = intent.getStringExtra("valorSemAdicionais");

        ((EditText) findViewById(R.id.txtTipoServico)).setText(tipo);
        ((EditText) findViewById(R.id.txtValor)).setText(valor);
        ((EditText) findViewById(R.id.txtValorMaoPropria)).setText(valorMaoPropria);
        ((EditText) findViewById(R.id.txtValorAvisoRecebimento)).setText(valorAvisoRecebimento);
        ((EditText) findViewById(R.id.txtValorDeclarado)).setText(valorDeclarado);
        ((EditText) findViewById(R.id.txtValorSemAdicionais)).setText(valorSemAdicionais);
    }
}
