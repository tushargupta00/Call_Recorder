package com.example.android.callrecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;

public class RecordingService extends Service {
    private MediaRecorder rec;
    private boolean recordStarted;
    private File file;
    //String path = "/sdcard/alarms/";
    String path = Environment.getExternalStorageDirectory().getPath();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //return super.onStartCommand(intent, flags, startId);
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        Date date = new Date();
        CharSequence sdf = DateFormat.getDateInstance().format(date.getTime());
        rec = new MediaRecorder();
        rec.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        rec.setOutputFile(file.getAbsolutePath()+"/"+sdf+"rec.3gp");
        rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        TelephonyManager manager = (TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                //super.onCallStateChanged(state, incomingNumber);

                if(TelephonyManager.CALL_STATE_IDLE==state && rec==null){
                    rec.stop();
                    rec.reset();
                    rec.release();
                    recordStarted = false;
                    stopSelf();
                }
                else if(TelephonyManager.CALL_STATE_OFFHOOK==state){
                    try {
                        rec.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    rec.start();
                    recordStarted = true;
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }
}
