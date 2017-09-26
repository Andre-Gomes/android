package android.wpos.com.br.pesoideal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText txtPeso;
    private EditText txtAltura;
    private EditText txtIdade;
    private Spinner spinnerSexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Identifica os elementos de layout para manipulação
        this.txtPeso = (EditText) findViewById(R.id.editTextPeso);
        this.txtAltura  = (EditText) findViewById(R.id.editTextAltura);
        this.txtIdade  = (EditText) findViewById(R.id.editTextIdade);
        this.spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
    }

    public void calcularIMC(View v) {
        //Validando campos obrigatórios
        if (this.txtPeso.getText().toString().isEmpty() ||
                this.txtAltura.getText().toString().isEmpty() ||
                this.txtIdade.getText().toString().isEmpty()
                ){
            exibirMensagemDeErro("Informe todos os campos!", v);
            return;
        }

        Integer idade = Integer.parseInt(this.txtIdade.getText().toString());

        //Não calcule ou apresente nada caso a idade informada seja abaixo de 6.
        if (idade < 6){
            return;
        }

        Double peso = Double.parseDouble(this.txtPeso.getText().toString());
        Double altura = Double.parseDouble(this.txtAltura.getText().toString());
        String sexo = this.spinnerSexo.getSelectedItem().toString();

        Double imc = peso / Math.pow(altura, 2);
        String situacaoIMC = "";
        if (idade > 16) {
            if (imc < 17){
                situacaoIMC = "Muito abaixo do peso";
            }else if (imc < 18.5){
                situacaoIMC = "Abaixo do peso";
            }else if (imc < 25){
                situacaoIMC = "Peso normal";
            }else if (imc < 30){
                situacaoIMC = "Acima do peso";
            }else if (imc < 35){
                situacaoIMC = "Obesidade I";
            }else if (imc < 40){
                situacaoIMC = "Obesidade II (severa)";
            }else{
                situacaoIMC = "Obesidade III (mórbida)";
            }
        }else if (idade >= 6){
            if ("Masculino".equals(sexo)){
                switch (idade){
                    case 6:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 14.5, 16.6, 18.0);
                    case 7:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 15.0, 17.3, 19.1);
                    case 8:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 15.6, 16.7, 20.3);
                    case 9:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 16.1, 18.8, 21.4);
                    case 10:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 16.7, 19.6, 22.5);
                    case 11:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 17.2, 20.3, 23.7);
                    case 12:
                        situacaoIMC =  calcularFaixaIMCMeninoEMenina(imc, 17.8, 21.1, 24.8);
                    case 13:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 18.5, 21.9, 25.9);
                    case 14:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 19.2, 22.7, 26.9);
                    case 15:
                        situacaoIMC =  calcularFaixaIMCMeninoEMenina(imc, 19.9, 23.6, 27.7);
                }
            }else { //Feminino
                switch (idade){
                    case 6:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 14.3, 16.6, 18.0);
                    case 7:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 14.9, 17.1, 18.9);
                    case 8:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 15.6, 18.1, 20.3);
                    case 9:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 16.3, 19.1, 21.7);
                    case 10:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 17.0, 20.1, 23.2);
                    case 11:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 17.6, 21.1, 24.5);
                    case 12:
                        situacaoIMC =  calcularFaixaIMCMeninoEMenina(imc, 18.3, 22.1, 25.9);
                    case 13:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 18.9, 23.0, 27.7);
                    case 14:
                        situacaoIMC = calcularFaixaIMCMeninoEMenina(imc, 19.3, 23.8, 27.9);
                    case 15:
                        situacaoIMC =  calcularFaixaIMCMeninoEMenina(imc, 19.6, 24.2, 28.8);
                }
            }
        }
        CharSequence resultado = "";
        if (!situacaoIMC.isEmpty()){
            resultado = "Olá, seu IMC é " + String.format("%.2f", imc) + " e a sua situação é: " + situacaoIMC;
            // Exibe a notificação
            criarNotificacaoSimples("Alerta Peso Ideal.", "O IMC foi calculado", resultado.toString());
        }
    }

    private void criarNotificacaoSimples(String titulo, String textoNotificacao, String resultadoIMC){
        int id = 1;
        int icone = R.drawable.imc;

        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("resultadoIMC", resultadoIMC);
        PendingIntent p = getPendingIntent(id, intent, this);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this);
        notificacao.setSmallIcon(icone);
        notificacao.setContentTitle(titulo);
        notificacao.setContentText(textoNotificacao);
        notificacao.setContentIntent(p);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(id, notificacao.build());
    }

    private PendingIntent getPendingIntent(int id, Intent intent, Context context){
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);

        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;
    }

    private String calcularFaixaIMCMeninoEMenina(Double imc, double refAbaixoPeso, double refNormal, double refSobrepeso) {
        String resultadoDoCalculo;
        if (imc < refAbaixoPeso){
            resultadoDoCalculo = "Abaixo do peso";
        }else if (imc <= refNormal){
            resultadoDoCalculo = "Normal";
        }else if (imc <= refSobrepeso){
            resultadoDoCalculo = "Sobrepeso";
        }else {
            resultadoDoCalculo = "Obesidade";
        }
        return resultadoDoCalculo;
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

}
