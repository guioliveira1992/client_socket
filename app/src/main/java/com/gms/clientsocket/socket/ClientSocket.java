package com.gms.clientsocket.socket;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket  extends Thread{

    private Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;
    private String texto;
    private String ip;
    private int porta;
    public static volatile boolean sucess = false;


    /**
     *
     * @param porta
     * @param ip
     * @param texto
     * @throws IOException
     * Construtor da classe que realiza a comunicação client socket
     */
    public ClientSocket(int porta, String ip, String texto) throws IOException {
        this.texto = texto;
        this.ip = ip;
        this.porta = porta;

    }

    /**
     * Método que inicia a thread que realiza a comunicação client socket
     */
    public void run() {

        Log.i("LOGANDO","Conectando Socket ip  porta: ");
        try {
            socket = new Socket(ip, porta);

            if(socket!=null){

                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                Log.i("ENVIANDO","Enviando mensagem para o servidor... " );

                out.writeBytes(texto);
                out.flush();
                out.close();
                sucess = true;
            }

        } catch (IOException e) {
            sucess = false;
            e.printStackTrace();
        }
    }

    /**
     * Método que finaliza a comunicação client socket
     */
    public synchronized void fecharSocket() throws IOException {
        if(this.out!=null){
            this.out.close();
        }
       if(this.in != null){
           this.in.close();
       }

       if(this.socket!=null){
           this.socket.close();
       }

    }

}
