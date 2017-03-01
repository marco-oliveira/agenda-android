package br.com.marco.agenda.firebase;

import android.text.LoginFilter;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.marco.agenda.retrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marco on 01/03/17.
 */

public class AgendaInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token Firebase", refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        enviaTokenParaServidor(refreshedToken);
    }

    private void enviaTokenParaServidor(final String token) {

        Call<Void> call = new RetrofitInitializer().getDispositivoService().enviaToken(token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Token Enviado", token);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Token falhou", t.getMessage() );
            }
        });

    }


}
