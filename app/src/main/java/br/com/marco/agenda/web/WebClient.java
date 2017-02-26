package br.com.marco.agenda.web;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 16/02/17.
 */
public class WebClient {

    private static final String LOCALHOST = "http://10.1.1.128:8080/";
    private String endereco = "";

    public String post(String json) {

        endereco = LOCALHOST + "mobile";
        return realizaRequisicao(json, endereco);
    }

    /*
     *NÃO PRECISA MAIS
        public void sincroniza(String json) {
        endereco = LOCALHOST+"api/aluno";
        realizaRequisicao(json, endereco);
    }*/

    @Nullable
    private String realizaRequisicao(String json, String endereco) {
        try {
            URL url = new URL(endereco);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            PrintStream output = new PrintStream(connection.getOutputStream());

            output.println(json);

            Scanner scanner = new Scanner(connection.getInputStream());

            String resposta = scanner.next();

            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
