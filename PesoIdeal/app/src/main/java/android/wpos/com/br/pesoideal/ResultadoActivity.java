package android.wpos.com.br.pesoideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent intent = getIntent();
        String resultadoIMC = intent.getStringExtra("resultadoIMC");

        ((TextView) findViewById(R.id.textViewResultadoIMC)).setText(resultadoIMC);
    }
}
