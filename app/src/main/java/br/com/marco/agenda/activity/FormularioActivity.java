package br.com.marco.agenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.marco.agenda.R;
import br.com.marco.agenda.dao.AlunoDAO;
import br.com.marco.agenda.helper.FormularioHelper;
import br.com.marco.agenda.model.Aluno;
import br.com.marco.agenda.task.InserirAlunoTask;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String urlPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null){
            helper.preencheFormulario(aluno);
        }


        //Capturando imagem e salvando no diretório da aplicação
        Button buttonCamera = (Button) findViewById(R.id.form_button_camera);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                urlPhoto = getExternalFilesDir(null) + "/"+System.currentTimeMillis()+".jpg";
                File filePhoto = new File(urlPhoto);
                intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePhoto));
                startActivityForResult(intentFoto, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CODIGO_CAMERA){

               helper.carregaImagem(urlPhoto);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAluno();

                AlunoDAO dao = new AlunoDAO(this);

                if (aluno.getId() == null){
                    dao.insere(aluno);
                } else {
                    dao.altera(aluno);
                }


                dao.close();
                new InserirAlunoTask(aluno).execute();

                Toast.makeText(FormularioActivity.this, "Aluno "+ aluno.getNome()+ " Salvo!", Toast.LENGTH_SHORT).show();
                finish();


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
