package br.com.marco.agenda.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by marco on 01/03/17.
 */
public interface DispositivoService {

    @POST("firebase/dispositivo")
    Call<Void> enviaToken(@Header("token") String token);
}
