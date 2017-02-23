package br.com.marco.agenda;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.marco.agenda.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frame_principal, new ListaProvasFragment());

        if (isModoPaisagem()){
            transaction.replace(R.id.frame_secundario, new DetalhesProvasFragment());
        }

        transaction.commit();

    }

    private boolean isModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!isModoPaisagem()){

            DetalhesProvasFragment detalhesFragment = new DetalhesProvasFragment();

            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            detalhesFragment.setArguments(parametros);

            FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.replace(R.id.frame_principal, detalhesFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else{
            DetalhesProvasFragment detalheFragment =
                    (DetalhesProvasFragment) fragmentManager.findFragmentById(R.id.frame_secundario);

            detalheFragment.populaCamposCom(prova);
        }
    }
}
