package br.com.marco.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;

import br.com.marco.agenda.model.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();

        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView materia = (TextView) findViewById(R.id.detalhes_provas_materia);
        TextView data = (TextView) findViewById(R.id.detalhes_provas_data);
        ListView topicos = (ListView) findViewById(R.id.detalhes_provas_topicos);

        materia.setText(prova.getMateria());
        data.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prova.getTopicos());

        topicos.setAdapter(adapter);

    }
}
