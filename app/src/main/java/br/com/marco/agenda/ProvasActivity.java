package br.com.marco.agenda;

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

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto Direto", "Objeto Indireto");
        Prova portugues = new Prova("Portugues", "22/04/1983", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Trigonometria", "Equações de 2 grau");
        Prova matematica = new Prova("Matematica", "23/04/1985", topicosMatematica);

        List<Prova> provas = Arrays.asList(portugues, matematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_list_item_1, provas);

        ListView listaProvas = (ListView) findViewById(R.id.provas_lista);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova prova = (Prova) parent.getItemAtPosition(position);

                Toast.makeText(ProvasActivity.this, "Prova de "+ prova, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
