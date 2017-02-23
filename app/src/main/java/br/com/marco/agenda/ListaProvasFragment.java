package br.com.marco.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.marco.agenda.model.Prova;

/**
 * Created by marco on 22/02/17.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto Direto", "Objeto Indireto");
        Prova portugues = new Prova("Portugues", "22/04/1983", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Trigonometria", "Equações de 2 grau");
        Prova matematica = new Prova("Matematica", "23/04/1985", topicosMatematica);

        List<Prova> provas = Arrays.asList(portugues, matematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView listaProvas = (ListView) view.findViewById(R.id.provas_lista);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova prova = (Prova) parent.getItemAtPosition(position);

                Toast.makeText(getContext(), "Prova de "+ prova, Toast.LENGTH_SHORT).show();

                ProvasActivity activity = (ProvasActivity) getActivity();

                activity.selecionaProva(prova);

            }
        });


        return view;
    }
}
