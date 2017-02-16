package br.com.marco.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.marco.agenda.converter.AlunoConverter;
import br.com.marco.agenda.dao.AlunoDAO;
import br.com.marco.agenda.model.Aluno;
import br.com.marco.agenda.web.WebClient;

/**
 * Created by marco on 16/02/17.
 */

public class AlunoEnvioTask extends AsyncTask<Object, String, String> {

    private Context context;
    private ProgressDialog dialog;

    public AlunoEnvioTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde", "Enviando...", true, true);
        dialog.onStart();
    }

    @Override
    protected String doInBackground(Object[] params) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJson(alunos);
        WebClient cliente = new WebClient();
        String resposta = cliente.post(json);



        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
