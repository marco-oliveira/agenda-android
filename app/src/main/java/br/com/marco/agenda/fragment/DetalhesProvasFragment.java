package br.com.marco.agenda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import br.com.marco.agenda.R;
import br.com.marco.agenda.model.Prova;

public class DetalhesProvasFragment extends Fragment {


    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_provas, container, false);

        campoMateria = (TextView) view.findViewById(R.id.detalhes_provas_materia);
        campoData = (TextView) view.findViewById(R.id.detalhes_provas_data);
        listaTopicos = (ListView) view.findViewById(R.id.detalhes_provas_topicos);

        Bundle arguments = getArguments();
        if (arguments != null){
            Prova prova = (Prova) arguments.getSerializable("prova");
            populaCamposCom(prova);

        }


        return view;
    }

    public void populaCamposCom(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);
    }
}
