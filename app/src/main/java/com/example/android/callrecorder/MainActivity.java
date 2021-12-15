package com.example.android.callrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton startandoff ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startandoff=(ToggleButton) findViewById(R.id.toggleButton2);
    }

    public void togglebutton(View view){
        boolean checked = ((ToggleButton) view). isChecked();
        if(checked){
            Intent intent = new Intent(this,RecordingService.class);
            startService(intent);
            Toast.makeText(getApplicationContext(), "Call Recording Started", Toast.LENGTH_SHORT).show();

        }
        else{
            Intent intent = new Intent(this,RecordingService.class);
            stopService(intent);
            Toast.makeText(getApplicationContext(), "Call Recording Stopped", Toast.LENGTH_SHORT).show();

        }
    }
}