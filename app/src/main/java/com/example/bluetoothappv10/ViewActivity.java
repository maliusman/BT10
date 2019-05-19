package com.example.bluetoothappv10;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ViewActivity extends AppCompatActivity {
    private ConnectedThread mConnectedThread;
    Handler mBtHandler;
    final int handlerState = 0; //used to identify handler message
    BluetoothAdapter mBtAdapter = null;
    BluetoothSocket mBtSocket = null;
    private boolean isBtConnected = false;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //SPP UUID
    private static String address;
    private StringBuilder recDataString = new StringBuilder();
    private LineGraphSeries<DataPoint> series;
    int x,y;
    int beat_old = 0, threshold = 620, gapCount = 10, currentBpm = 0;
    int[] beats = new int[40];  // Used to calculate average BPM
    int beatIndex = 0;
    boolean belowThreshold = true;
    public int[] mVreading = new int[400];
    int mVindex = 0;
    String mVstring;
    int bpmSum = 0;
    int avgBPM = 0;
    float rrTimeGap = 0;
    int beatCount= 0;
    TextView mRRTimeTxt, mCurrentBPMTxt, mDateTxt;
    Chronometer mSessionLenCh;
    GraphView mecgGV;
    Button mDisconnectBtn, mStopBtn;
    private ProgressDialog connectingPD;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        setContentView(R.layout.activity_view); //call the widgets
        //mdataTxt = (TextView) findViewById(R.id.dataTxt);
        mDisconnectBtn = findViewById(R.id.disconnectBtnV);
        mStopBtn = findViewById(R.id.stopECGBtnV);
        //intArray = new int[10000];
        mRRTimeTxt = findViewById(R.id.RRTimeTxt);
        mCurrentBPMTxt = findViewById(R.id.currentBPMTxt);
        mDateTxt = findViewById(R.id.dateTxt);
        mSessionLenCh = findViewById(R.id.sessionLenCh);
        mecgGV = findViewById(R.id.ecgGV);  //graph view instance
        series = new LineGraphSeries<DataPoint>(); //data for graph
        mecgGV.addSeries(series);
        dateSetter();
        graphSettings(); //method with settings
        mSessionLenCh.start();
        x =0;

        mDisconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnAnimator(mDisconnectBtn); Disconnect();
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mStopBtn);
                if(mBtSocket.isConnected())
                {
                    try{ mBtSocket.close();}
                    catch (IOException e) { showToast("Error");}
                }
                else
                    showToast("ECG already stopped");
            }
        });

        new ConnectBTAsync().execute(); //Call the AsyncTask class to connect

        mBtHandler = new Handler()
        {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState)
                {
                    String readMessage = (String) msg.obj;
                    recDataString.append(readMessage);
                    mVstring= recDataString.toString();
                    //int i = 0;

                    mVreading[mVindex] = Integer.parseInt(mVstring.trim());

                    //Disconnected electrodes
                    if(mVreading[mVindex]==(00))
                    {
                        series.appendData(new DataPoint(x,0),true,40);
                        x++;
                    }
                    //value received
                    else
                    {
                        series.appendData(new DataPoint(x,mVreading[mVindex]),true, 40); //append x,y values to data series
                        x++; //increment x-axis by 1
                        //value above threshold to be considered R peak and R peak not found for last 9 values
                        if (mVreading[mVindex] >= threshold && gapCount>=9)
                        {
                            int Rtime = (int)System.currentTimeMillis(); // get the current millisecond
                            gapCount = 0;
                            heartRate(Rtime,mVreading[mVindex]);
                            belowThreshold = false;
                        }
                        else if(mVreading[mVindex] < threshold) {belowThreshold = true;}
                    }

                    gapCount++;
                    //i++;

                    mVindex = (mVindex +1)%400; //cycle through the array and overwrite after 400 values
                    if (mVindex==0)
                    {
                        //showToast("30 sec");
                        errorDetection();
                        Arrays.fill(mVreading, 0); //resets array
                        //showToast("Array reset");
                    }
                    /*75 ms delay from hardware
                    1000ms/75ms = 13.3 times a second
                    60 x 13.3 = 798 ~ 800 times a min
                    400 times for 30 seconds*/
                    recDataString.delete(0, recDataString.length());    //clear all string data
                    mVstring = "";
                }
            }
        };
    }

    private void dateSetter()
    {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(date);
        mDateTxt.setText("Date: " + formattedDate);

    }
    private void graphSettings()
    {
        mecgGV.setTitle("Real Time ECG");
        mecgGV.setBackgroundColor(Color.BLACK);
        mecgGV.setTitleTextSize(50);
        mecgGV.setTitleColor(Color.GREEN);
        mecgGV.getViewport().setXAxisBoundsManual(true);
        mecgGV.getViewport().setMinX(0);
        mecgGV.getViewport().setMaxX(40);
        mecgGV.getViewport().setScrollable(true);
        mecgGV.getGridLabelRenderer().setHorizontalAxisTitle("Values");
        mecgGV.getGridLabelRenderer().setVerticalAxisTitle("mV");
        mecgGV.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
        mecgGV.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        mecgGV.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        mecgGV.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        mecgGV.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        mecgGV.getGridLabelRenderer().setGridColor(Color.GRAY);
        mecgGV.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        series.setColor(Color.GREEN);
        series.setThickness(10);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(40,92,197,124));
        //series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
    }
    private void notifyHR(String text)
    {
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext());
        builder.setColor(Color.CYAN);
        builder.setContentTitle("Health app");
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.notification_icon);
        Notification notification = builder.build();
        NotificationManagerCompat.from(getApplicationContext()).notify(0,notification);
    }

    private void heartRate(int Rt, int Rv)
    {

        int newTime = Rt;
        rrTimeGap = newTime - beat_old; // find the time between the last two beats in ms
        if(rrTimeGap!=newTime)
        {
            currentBpm = 60000/(int)rrTimeGap; // convert to beats per minute
            //beats[beatIndex]= currentBpm; // store to array to convert the average
            //total = total + beats[beatIndex];
            bpmSum = bpmSum + currentBpm;
            beatIndex = (beatIndex +1)%10; // cycle through the array instead of using FIFO queue
            String RR = String.format("%.2f", rrTimeGap/1000); //2 decimal point conversion
            mRRTimeTxt.setText("R-R: " + RR + "s");
            mCurrentBPMTxt.setText("Heart Rate: "+ Float.toString(currentBpm));
            //errorDetection(RR, BPM);
            //if(beatIndex==0) //after 10 bpm values, calculate avg and show
            //{
            //avg bpm based on sum of currentBpm/total number of values
            //showToast("Avg BPM: " + BPM);
            //errorDetection(BPM);
            //BPM=0;

            //}
        }
        beatCount++;
        beat_old = newTime;
        //mBPM.setText("BPM:" + Integer.toString(BPM));
        //notifyHR("Current BPM: " + Float.toString(currentBpm));
    }

    private void errorDetection( )
    {
        if(beatCount!=0) {
            avgBPM = bpmSum / beatCount;
            showToast("Average BPM: " + avgBPM);
        }
        if(avgBPM!=0){
            if(avgBPM>=110)
            {
                showToast("Alarmingly fast heart rate");
                notifyHR("Alarmingly fast heart rate");
            }
            else if(avgBPM>=85 && avgBPM<110)
            {
                showToast("Fast heart rate");
                notifyHR("Fast heart rate");
            }
            else if(avgBPM>=40 && avgBPM<60)
            {
                showToast("Slow heart rate");
                notifyHR("Slow heart rate");
            }
            else if(avgBPM<40)
            {
                showToast("Alarmingly slow heart rate!");
                notifyHR("Alarmingly slow heart rate");
            }
        }
        else
            showToast("BPM = 0");
        avgBPM = 0;
        bpmSum=0;
        beatCount=0;
    }

    private void Disconnect()
    {
        if (mBtSocket!=null) //If the btSocket is busy
        {
            try
            {
                mBtSocket.close(); //close connection
                mSessionLenCh.stop();
            }
            catch (IOException e)
            {
                showToast("Error..");
            }
        }
        //notifyHR("ECG device disconnected!");
        finish(); //return to the first layout
    }
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(ViewActivity.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }

    //convenient threading mechanism
    private class ConnectBTAsync extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            connectingPD = ProgressDialog.show
                    (ViewActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }


        //Void used as no parameters used
        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (mBtSocket == null || !isBtConnected)
                {
                    mBtAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice myDevice = mBtAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    mBtSocket = myDevice.createRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBtSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                showToast("Connection failed. Try again please.");
                finish();
            }
            else
            {
                showToast("Connected");
                isBtConnected = true;
                if(mBtSocket!=null) {
                    mConnectedThread = new ConnectedThread(mBtSocket);
                    mConnectedThread.start();
                }
                else
                    showToast("Error..");
            }
            connectingPD.dismiss();
        }
    }
    //receive stream data and send to handler for processing
    private class ConnectedThread extends Thread {
        private final InputStream mInputStream;

        //creation of the connect thread
        //overloaded constructor
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            try
            {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();

            } catch (IOException e)
            {
                showToast("Error");
            }
            mInputStream = tmpIn;
        }

        public void run() {
            byte[] arrybytes = new byte[1024];
            StringBuilder stBuilder = new StringBuilder();
            try
            {
                int n;
                while(-1 != (n = this.mInputStream.read(arrybytes)))
                    {
                    stBuilder.append(new String(arrybytes, 0, n, Charset.forName((String)"UTF-8")));
                    int n2 = stBuilder.indexOf("\n");
                    if (n2!= -1)
                    {
                        String str2 = stBuilder.substring(0, -1 + (n2 + "\n".length()));
                        // Send the obtained data to the UI Activity via handler
                        mBtHandler.obtainMessage(0, n, -1, (Object) str2).sendToTarget();
                    }
                    stBuilder.delete(0, n2 + "\n".length());
                }
                return;
            }
            catch(IOException ioex)
            {
                ioex.printStackTrace();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Disconnect();

    }
}

//Series is a class in GraphView lib used to fill graph with data
//Viewport is a part of graph that is currently visible  on screen
//Runnable is a type of class with a run code inside
//Thread is an independent flow of execution
//Handler or Runnable can work only with worker threads