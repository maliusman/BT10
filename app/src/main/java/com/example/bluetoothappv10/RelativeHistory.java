package com.example.bluetoothappv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RelativeHistory extends AppCompatActivity {
TextView mpatNameRH, mpatHistoryRH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_history);
        mpatNameRH=findViewById(R.id.affiliatedPatNameRHTxt);
        mpatHistoryRH=findViewById(R.id.patHistoryRHTxt);
    }
}
