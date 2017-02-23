package br.com.marco.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.marco.agenda.dao.AlunoDAO;
import br.com.marco.agenda.model.Aluno;

/**
 * Created by marco on 23/02/17.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoInicial = pegaCordenadaDoEndereco("Av Brasil sul, 5300, Anápolis, GO");
        if (posicaoInicial != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 17);
            googleMap.moveCamera(update);
       }

        AlunoDAO dao = new AlunoDAO(getContext());

        for (Aluno aluno : dao.buscaAlunos()){
            LatLng cordenada = pegaCordenadaDoEndereco(aluno.getEndereco());
            if (cordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(cordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        dao.close();
    }

    public LatLng pegaCordenadaDoEndereco(String endereco){
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()){
                LatLng posicao =
                        new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
