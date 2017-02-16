package br.com.marco.agenda.receiver;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.Serializable;

import br.com.marco.agenda.ListaAlunosActivity;
import br.com.marco.agenda.R;
import br.com.marco.agenda.dao.AlunoDAO;

/**
 * Created by marco on 16/02/17.
 */

public class SMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        AlunoDAO dao = new AlunoDAO(context);

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        byte[] pdu = (byte[]) pdus[0];

        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);
        
        String telefone = sms.getOriginatingAddress();
        
        if (dao.ehAluno(telefone)){
            Toast.makeText(context, "Recebeu SMS de um Aluno!",Toast.LENGTH_SHORT).show();

            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

        }

        

    }
}
