package calculaprazows.wpos.com.br.calculaprazows;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText txtCepOrigem;
    EditText txtCepDestino;
    EditText txtPeso;
    EditText txtComprimento;
    EditText txtAltura;
    EditText txtLargura;
    EditText txtDiametro;
    EditText txtValorDeclarado;
    Spinner spiTipoServico;
    Spinner spiMaoPropia;
    Spinner spiAvisoRecebimento;
    Spinner spiFormato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Em API maiores que 9, é necessário habilitar um ThreadPolicy
        // para liberar o acesso ao WebService externo
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Identifica os elementos de layout para manipulação
        txtCepOrigem = (EditText) findViewById(R.id.txtCepOrigem);
        txtCepDestino = (EditText) findViewById(R.id.txtCepDestino);
        txtPeso = (EditText) findViewById(R.id.txtPeso);
        txtComprimento  = (EditText) findViewById(R.id.txtComprimento);
        txtAltura  = (EditText) findViewById(R.id.txtAltura);
        txtLargura  = (EditText) findViewById(R.id.txtLargura);
        txtDiametro  = (EditText) findViewById(R.id.txtDiametro);
        txtValorDeclarado  = (EditText) findViewById(R.id.txtValorDeclarado);

        spiTipoServico = (Spinner) findViewById(R.id.spiTipoServico);
        spiMaoPropia = (Spinner) findViewById(R.id.spinnerMaoPropria);
        spiAvisoRecebimento = (Spinner) findViewById(R.id.spinnerAvisoRecebimento);
        spiFormato = (Spinner) findViewById(R.id.spinnerCdFormtado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calcularPrazo(View v)
    {
        // Informações de Conexão WebServices
        final String NAMESPACE = "http://tempuri.org/";
        final String METHOD_NAME = "CalcPrecoData";
        final String SOAP_ACTION = "http://tempuri.org/CalcPrecoData";
        final String URL =
                "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?wsdl";

        // Montando a Requisição SOAP com as informações a serem enviadas
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // Transforma texto de serviço em código de serviço
        String nCdServico = "";
        switch(spiTipoServico.getSelectedItem().toString())
        {
            case "SEDEX Varejo":
                nCdServico = "40010";
                break;
            case "SEDEX a Cobrar Varejo":
                nCdServico = "40045";
                break;
            case "SEDEX 10 Varejo":
                nCdServico = "40215";
                break;
            case "SEDEX Hoje Varejo":
                nCdServico = "40290";
                break;
            case "PAC Varejo":
                nCdServico = "41106";
                break;
        }

        String nCdFormato = "";
        switch(spiFormato.getSelectedItem().toString())
        {
            case "Caixa/Pacote":
                nCdFormato = "1";
                break;
            case "Rolo/Prisma":
                nCdFormato = "2";
                break;
            case "Envelope":
                nCdFormato = "3";
                break;
        }

        if (nCdServico.isEmpty() ||
            txtCepOrigem.getText().toString().isEmpty() ||
            txtCepDestino.getText().toString().isEmpty() ||
            txtPeso.getText().toString().isEmpty() ||
            txtComprimento.getText().toString().isEmpty() ||
            txtAltura.getText().toString().isEmpty() ||
            txtLargura.getText().toString().isEmpty() ||
            txtDiametro.getText().toString().isEmpty() ){
            exibirMensagemDeErro("Informe todos os campos!", v);
            return;
        }

        // Adiciona informações à requisição
        request.addProperty("nCdServico", nCdServico);
        request.addProperty("sCepOrigem", txtCepOrigem.getText().toString());
        request.addProperty("sCepDestino", txtCepDestino.getText().toString());
        request.addProperty("nVlPeso", txtPeso.getText().toString());
        request.addProperty("nCdFormato", nCdFormato);
        request.addProperty("nVlComprimento", txtComprimento.getText().toString());
        request.addProperty("nVlAltura", txtAltura.getText().toString());
        request.addProperty("nVlLargura",txtLargura.getText().toString());
        request.addProperty("nVlDiametro", txtDiametro.getText().toString());
        request.addProperty("sCdMaoPropria", spiMaoPropia.getSelectedItem().toString());
        request.addProperty("nVlValorDeclarado", txtValorDeclarado.getText().toString().isEmpty() ? "0" : txtValorDeclarado.getText().toString());
        request.addProperty("sCdAvisoRecebimento", spiAvisoRecebimento.getSelectedItem().toString());
        request.addProperty("sDtCalculo", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Criando o Envelope de Envio
        SoapSerializationEnvelope envelope = new
                SoapSerializationEnvelope(SoapEnvelope.VER11);
        // Por ser um .ASMX, o Servidor de WebService é feito no padrão .NET. Isso
        // deve ser informado ao KSOAP por se tratar de um padrão diferente.
        envelope.dotNet = true;
        // Adicionando a Requisição SOAP ao seu envelope
        envelope.setOutputSoapObject(request);
        // Colocar o envelope SOAP em um protocolo HTTP de transporte
        HttpTransportSE transport = new HttpTransportSE(URL);
        try {

            transport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.bodyIn;

            SoapObject responseAnyType = (SoapObject) response.getProperty(0);
            SoapObject responseServicos = (SoapObject)
                    responseAnyType.getProperty("Servicos");
            SoapObject responseCServico = (SoapObject)
                    responseServicos.getProperty("cServico");


            String tipoServico = spiTipoServico.getSelectedItem().toString();
            String valor = responseCServico.getProperty("Valor").toString();
            String valorMaoPropria = responseCServico.getProperty("ValorMaoPropria").toString();
            String valorAvisoRecebimento = responseCServico.getProperty("ValorAvisoRecebimento").toString();
            String valorDeclarado = responseCServico.getProperty("ValorValorDeclarado").toString();
            String valorSemAdicionais = responseCServico.getProperty("ValorSemAdicionais").toString();
            String mensagemErro =  responseCServico.getProperty("MsgErro").toString();

            if (mensagemErro != null && !"anyType{}".equals(mensagemErro)){
                exibirMensagemDeErro(mensagemErro, v);
                return;
            }else{
               exibirResultado(tipoServico, valor, valorMaoPropria, valorAvisoRecebimento, valorDeclarado, valorSemAdicionais);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirResultado(String tipo, String valor, String valorMaoPropria, String valorAvisoRecebimento, String valorDeclarado, String valorSemAdicionais){
        Intent nextActivity = new Intent(this, DetalhesPrecoActivity.class);
        nextActivity.putExtra("tipo", tipo);
        nextActivity.putExtra("valor", valor);
        nextActivity.putExtra("valorMaoPropria", valorMaoPropria);
        nextActivity.putExtra("valorAvisoRecebimento", valorAvisoRecebimento);
        nextActivity.putExtra("valorDeclarado", valorDeclarado);
        nextActivity.putExtra("valorSemAdicionais", valorSemAdicionais);
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
}
