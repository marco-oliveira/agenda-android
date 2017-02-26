package br.com.marco.agenda.dto;

import java.util.List;

import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 26/02/17.
 */

public class AlunoSync {

    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }

    public void setMomentoDaUltimaModificacao(String momentoDaUltimaModificacao) {
        this.momentoDaUltimaModificacao = momentoDaUltimaModificacao;
    }
}
