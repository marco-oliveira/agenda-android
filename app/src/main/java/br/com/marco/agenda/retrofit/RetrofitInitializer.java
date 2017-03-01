package br.com.marco.agenda.retrofit;

import br.com.marco.agenda.services.DispositivoService;
import br.com.marco.agenda.services.AlunoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by marco on 25/02/17.
 */

public class RetrofitInitializer {

    private final Retrofit retrofit;

    public RetrofitInitializer(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        retrofit = new Retrofit.Builder().baseUrl("http://10.1.1.128:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }

    public DispositivoService getDispositivoService() {

        return retrofit.create(DispositivoService.class);
    }
}
