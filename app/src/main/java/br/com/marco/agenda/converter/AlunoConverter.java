package br.com.marco.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 16/02/17.
 */
public class AlunoConverter {
    public String converterParaJson(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();

                for (Aluno aluno : alunos){
                    js.object();
                    js.key("nome").value(aluno.getNome());
                    js.key("endereco").value(aluno.getEndereco());
                    js.key("telefone").value(aluno.getTelefone());
                    js.key("site").value(aluno.getSite());
                    js.key("nota").value(aluno.getNota());
                    js.endObject();
                }
            js.endArray().endObject().endArray().endObject();
            return js.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return "";
    }
}