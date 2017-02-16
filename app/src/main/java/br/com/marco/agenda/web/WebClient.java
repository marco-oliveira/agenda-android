package br.com.marco.agenda.web;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by marco on 16/02/17.
 */
public class WebClient {

    public String post(String json) {

        URL url = null;
        try {
            url = new URL("http://localhost:8080/mobile");
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
