package br.com.marco.agenda.gps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.marco.agenda.fragment.MapaFragment;

/**
 * Created by marco on 23/02/17.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient client;
    private final MapaFragment mapaFragment;

    public Localizador(Context context, MapaFragment mapaFragment) {
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        client.connect();

        this.mapaFragment = mapaFragment;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng cordenada = new LatLng(location.getLatitude(), location.getLongitude());
        mapaFragment.centralizaEm(cordenada);
    }
}
