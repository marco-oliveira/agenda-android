package br.com.marco.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.marco.agenda.FormularioActivity;
import br.com.marco.agenda.R;
import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 13/02/17.
 */
public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoImagem;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){

        campoNome = (EditText) activity.findViewById(R.id.form_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.form_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.form_telefone);
        campoSite = (EditText) activity.findViewById(R.id.form_site);
        campoNota = (RatingBar) activity.findViewById(R.id.form_nota);
        campoImagem = (ImageView) activity.findViewById(R.id.form_foto);
        aluno = new Aluno();

    }

    public Aluno pegaAluno() {
        this.aluno = aluno;
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoImagem.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String urlPhoto) {
        if (urlPhoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(urlPhoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoImagem.setScaleType(ImageView.ScaleType.FIT_XY);
            campoImagem.setImageBitmap(bitmapReduzido);
            campoImagem.setTag(urlPhoto);
        }

    }
}
