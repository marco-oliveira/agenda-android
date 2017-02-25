package br.com.marco.agenda.trash;

import android.os.AsyncTask;

import br.com.marco.agenda.converter.AlunoConverter;
import br.com.marco.agenda.model.Aluno;
import br.com.marco.agenda.web.WebClient;

/**
 * Created by marco on 24/02/17.
 *
 * NÃO UTILIZAR MAIS, SUBSTITUIDO PELA API RETROFIT
 */
public class InserirAlunoTask extends AsyncTask {
    private Aluno aluno;

    public InserirAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String json = new AlunoConverter().converterParaJsonCompleto(aluno);
     //   new WebClient().insere(json);
        return null;
    }
}
