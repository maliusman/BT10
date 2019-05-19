package com.example.bluetoothappv10;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DoctorNotification extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    TextView mPatNameDNTxt, mPatIDDNTxt, mIssueDNTxt;
    GraphView mgraphDN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notification);
        mPatNameDNTxt=findViewById(R.id.nameDNTxt);
        mPatIDDNTxt=findViewById(R.id.patIDDNTxt);
        mIssueDNTxt= findViewById(R.id.issueDNTxt);
        mgraphDN = findViewById(R.id.graphviewDN);
        series = new LineGraphSeries<DataPoint>(); //data for graph
        mgraphDN.addSeries(series);
        graphSettings(); //method with settings
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(DoctorNotification.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
    void graphSettings()
    {
        mgraphDN.setTitle("Affiliated Patient ECG");
        mgraphDN.setBackgroundColor(Color.BLACK);
        mgraphDN.setTitleTextSize(50);
        mgraphDN.setTitleColor(Color.GREEN);
        mgraphDN.getViewport().setXAxisBoundsManual(true);
        mgraphDN.getViewport().setMinX(0);
        mgraphDN.getViewport().setMaxX(40);
        mgraphDN.getViewport().setScrollable(true);
        //mgraphDN.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        mgraphDN.getGridLabelRenderer().setVerticalAxisTitle("mV");
        mgraphDN.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
        mgraphDN.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        mgraphDN.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        mgraphDN.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        mgraphDN.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        mgraphDN.getGridLabelRenderer().setGridColor(Color.GRAY);
        mgraphDN.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        series.setColor(Color.GREEN);
        series.setThickness(10);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(30,92,197,124));
        //series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
    }
}
