package com.gms.clientsocket;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gms.clientsocket.socket.ClientSocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String porta;
    private String ip;
    Button btnOpen;
    Button btnClosed;
    EditText etIp;
    EditText etPorta;
    EditText etUsuario;
    EditText etMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btnClosed = (Button) findViewById(R.id.btDesconectar);
        btnOpen = (Button) findViewById(R.id.btConectar);
        etIp = (EditText) findViewById(R.id.etIp);
        etPorta = (EditText) findViewById(R.id.etPorta);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etMensagem = (EditText) findViewById(R.id.etMensagem);


        /**
         * Método que inica a comunicação e envia mensagem para com o server socket
         */

            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Instanciando o objeto responsável por pegar a data atual do sistema
                    Date data = new Date();
                    //Instanciando o objeto responsável por definir mascara de leitura da data do sistema
                    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    //Pegando a data do sistema e atribuindo para a variável do tipo String
                    String dataHora = formatador.format(data);

                    String mensagemCompleta = new String(dataHora + " " + etUsuario.getText().toString() + " " + etMensagem.getText().toString());
                    try {
                        new ClientSocket(Integer.parseInt(etPorta.getText().toString()), etIp.getText().toString(), mensagemCompleta).start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (ClientSocket.sucess) {
                        Toast.makeText(MainActivity.this, "Conexão estabelecida com sucesso!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Erro ao conectar com servidor. Verifique o IP e porta de conexão!", Toast.LENGTH_LONG).show();
                    }


                }

            });


        /**
         * Método que finaliza a comunicação do client socket
         */
        btnClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    new ClientSocket(0, null, null).fecharSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
