package br.com.marco.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 13/02/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Alunos (id CHAR(36) PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
            case 2:
                String criarNovaTabela = "CREATE TABLE Alunos_novo (id CHAR(36) PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "endereco TEXT, " +
                        "telefone TEXT, " +
                        "site TEXT, " +
                        "nota REAL, " +
                        "caminhoFoto TEXT);";
                db.execSQL(criarNovaTabela);

                String inserirDadosNovaTabela = "INSERT INTO Alunos_novo " +
                        "(id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                        "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto from " +
                        "Alunos";
                db.execSQL(inserirDadosNovaTabela);

                String excluiTabela = "DROP TABLE Alunos";
                db.execSQL(excluiTabela);

                String alteraNomeTabela = "ALTER TABLE Alunos_novo " +
                        "RENAME TO Alunos";
                db.execSQL(alteraNomeTabela);
                
            case 3:
                String buscaAlunos = "SELECT * FROM alunos";
                Cursor c = db.rawQuery(buscaAlunos, null);
                List<Aluno> alunos = populaAlunos(c);
                String atualizaIdAluno = "UPDATE Alunos SET id=? WHERE id=?";
                for (Aluno aluno:
                     alunos) {
                    db.execSQL(atualizaIdAluno, new String[]{geraUUid(), aluno.getId()});
                }
        }
    }

    private String geraUUid() {
        return UUID.randomUUID().toString();
    }

    @NonNull
    private ContentValues getDados(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone" , aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public void insere(Aluno aluno) {

        SQLiteDatabase database = getWritableDatabase();

        //Gerando UUid para novo cadastrou
        alunoTemId(aluno);

        ContentValues dados = getDados(aluno);
        database.insert("Alunos", null, dados);
    }

    private void alunoTemId(Aluno aluno) {
        if (aluno.getId()==null){
            aluno.setId(geraUUid());
        }
    }


    public void excluir(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        database.delete("Alunos","id = ?", params);

    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();


        ContentValues dados = getDados(aluno);
        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);

    }

    public List<Aluno> buscaAlunos() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Alunos;", null);

        List<Aluno> alunos = populaAlunos(c);

        return alunos;
    }

    @NonNull
    private List<Aluno> populaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<Aluno>();

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getString(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public boolean ehAluno(String telefone){

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[]{telefone});

        int resultado = c.getCount();

        c.close();
        return resultado > 0;
    }


    public void sincroniza(List<Aluno> alunos) {

        for (Aluno aluno :
                alunos) {
            if (isAluno(aluno)) {
                altera(aluno);
            } else {
                insere(aluno);
            }
        }
    }

    private boolean isAluno(Aluno aluno) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM Alunos WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{aluno.getId()});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }
}
