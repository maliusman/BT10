package com.example.bluetoothappv10;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class RelativeViewECG extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    TextView maffPatNameRV, maffPatConditionRV;
    GraphView mgraphRV;
    ImageView mConditionImageRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_view_ecg);
        maffPatNameRV=findViewById(R.id.affiliatedPatNameRVTxt);
        maffPatConditionRV=findViewById(R.id.conditionRVTxt);
        mConditionImageRV=findViewById(R.id.conditionImgRV);
        mgraphRV = findViewById(R.id.graphviewRV);
        series = new LineGraphSeries<DataPoint>(); //data for graph
        mgraphRV.addSeries(series);
        graphSettings(); //method with settings
    }
    void graphSettings()
    {
        mgraphRV.setTitle("Affiliated Patient ECG");
        mgraphRV.setBackgroundColor(Color.BLACK);
        mgraphRV.setTitleTextSize(50);
        mgraphRV.setTitleColor(Color.GREEN);
        mgraphRV.getViewport().setXAxisBoundsManual(true);
        mgraphRV.getViewport().setMinX(0);
        mgraphRV.getViewport().setMaxX(40);
        mgraphRV.getViewport().setYAxisBoundsManual(true);
        mgraphRV.getViewport().setMinY(0);
        mgraphRV.getViewport().setMaxY(1000);
        mgraphRV.getViewport().setScrollable(true);
        //mgraphRV.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        mgraphRV.getGridLabelRenderer().setVerticalAxisTitle("mV");
        mgraphRV.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
        mgraphRV.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        mgraphRV.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        mgraphRV.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        mgraphRV.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        mgraphRV.getGridLabelRenderer().setGridColor(Color.GRAY);
        mgraphRV.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        series.setColor(Color.GREEN);
        series.setThickness(10);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(30,92,197,124));
        //series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
    }
}
