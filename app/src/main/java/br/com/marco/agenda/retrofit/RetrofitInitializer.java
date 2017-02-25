package br.com.marco.agenda.retrofit;

import br.com.marco.agenda.services.AlunoService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by marco on 25/02/17.
 */

public class RetrofitInitializer {

    private final Retrofit retrofit;

    public RetrofitInitializer(){
       retrofit = new Retrofit.Builder().baseUrl("http://10.1.1.128:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }
}
