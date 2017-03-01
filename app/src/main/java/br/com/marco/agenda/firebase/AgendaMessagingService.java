package br.com.marco.agenda.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by marco on 01/03/17.
 */

public class AgendaMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("Mensagem Recebida", String.valueOf(mensagem));
    }
}
