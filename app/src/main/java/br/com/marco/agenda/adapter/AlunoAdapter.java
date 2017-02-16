package br.com.marco.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.marco.agenda.ListaAlunosActivity;
import br.com.marco.agenda.R;
import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 15/02/17.
 */
public class AlunoAdapter extends BaseAdapter{


    private final Context context;
    private final List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null){
            view  = inflater.inflate(R.layout.list_item,parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.text_item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.text_item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        TextView campoEndereco = (TextView) view.findViewById(R.id.text_item_endereco);
        if (campoEndereco != null){
            campoEndereco.setText(aluno.getEndereco());
        }

        TextView campoSite = (TextView) view.findViewById(R.id.text_item_site);
        if (campoSite != null){
            campoSite.setText(aluno.getSite());
        }

        ImageView campoImagem = (ImageView) view.findViewById(R.id.list_item_foto);
        String urlPhoto = aluno.getCaminhoFoto();

        if (urlPhoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(urlPhoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoImagem.setScaleType(ImageView.ScaleType.FIT_XY);
            campoImagem.setImageBitmap(bitmapReduzido);
        }

        return view;
    }
}
