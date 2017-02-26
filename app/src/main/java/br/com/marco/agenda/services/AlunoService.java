package br.com.marco.agenda.services;

import java.util.List;

import br.com.marco.agenda.dto.AlunoSync;
import br.com.marco.agenda.model.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by marco on 25/02/17.
 */

public interface AlunoService {

    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSync> lista();
}
