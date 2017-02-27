package br.com.marco.agenda.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.marco.agenda.R;
import br.com.marco.agenda.adapter.AlunoAdapter;
import br.com.marco.agenda.dao.AlunoDAO;
import br.com.marco.agenda.dto.AlunoSync;
import br.com.marco.agenda.model.Aluno;
import br.com.marco.agenda.retrofit.RetrofitInitializer;
import br.com.marco.agenda.task.AlunoEnvioTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listAlunos;
    private AlunoDAO dao;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        listAlunos = (ListView) findViewById(R.id.lista_alunos);

        listAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                Aluno aluno = (Aluno) listAlunos.getItemAtPosition(position);

                Intent intentParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentParaFormulario.putExtra("aluno", aluno);
                startActivity(intentParaFormulario);
            }
        });

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_lista_aluno);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscaAlunos();
            }
        });


        Button buttonAddAluno = (Button) findViewById(R.id.lista_add_aluno);

        buttonAddAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormAluno = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intentFormAluno);
            }
        });
        registerForContextMenu(listAlunos);
        buscaAlunos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_lista_enviar:
                new AlunoEnvioTask(this).execute();
                break;

            case R.id.menu_provas_baixar:
                Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;

            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void carregaLista() {
        dao = new AlunoDAO(this);

        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        listAlunos = (ListView) findViewById(R.id.lista_alunos);

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);

        listAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();



        carregaLista();
    }

    private void buscaAlunos() {
        Call<AlunoSync> call = new RetrofitInitializer().getAlunoService().lista();

        call.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunosSync = response.body();
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.sincroniza(alunosSync.getAlunos());
                dao.close();
                carregaLista();
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("onFailure chamado", t.getMessage());
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listAlunos.getItemAtPosition(info.position);

        //Menu de Contexto para realizar uma chamada pelo numero cadastrado
        MenuItem itemChamar = menu.add("Chamar");
        itemChamar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else{
                    Intent intentChamar = new Intent(Intent.ACTION_CALL);
                    intentChamar.setData(Uri.parse("tel:"+aluno.getTelefone()));
                    startActivity(intentChamar);
                }
                return false;
            }
        });


        //Menu de Contexto para visualizar endereço no google maps
        MenuItem itemMapa = menu.add("Visualizar Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //Menu de Contexto para Enviar SMS
        MenuItem itemSms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+aluno.getTelefone()));
        itemSms.setIntent(intentSms);


        //Menu de Contexto para acessar o browser
        MenuItem itemSite = menu.add("Visitar Site");
        String site = aluno.getSite();
        if (!site.startsWith("http://")){
            site = "http://"+site;
        }
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        //Menu de Contexto para excluir um item
        MenuItem itemExcluir = menu.add("Excluir");
        itemExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Call<Void> call = new RetrofitInitializer().getAlunoService().deleta(aluno.getId());

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        dao = new AlunoDAO(ListaAlunosActivity.this);

                        dao.excluir(aluno);
                        dao.close();

                        carregaLista();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAlunosActivity.this, "Não pode excluir "+aluno.getNome(), Toast.LENGTH_SHORT).show();
                    }
                });


                return false;
            }
        });

    }
}
