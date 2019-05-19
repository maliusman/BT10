package com.example.bluetoothappv10;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private  static  final int REQUEST_ENABLE_BT = 0;
    private  static  final int REQUEST_DISCOVERABLE_BT = 1;
    TextView mStatusTxt;
    ImageView mBtIv;
    Button mOnBtn, mOffBtn, mDiscoBtn, mPairedListBtn,mLogoutBtn;
    BluetoothAdapter mBtAdapter;
    BluetoothDevice mBtDevice;
    BluetoothSocket mBtSocket;
    InputStream mInpStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusTxt=findViewById(R.id.statusTxt);
        mBtIv=findViewById(R.id.BtIv);
        mOnBtn=findViewById(R.id.onBtnMain); mOffBtn=findViewById(R.id.offBtnMain);mDiscoBtn=findViewById(R.id.discoBtnMain);
        mPairedListBtn=findViewById(R.id.pairedlistBtnMain);mLogoutBtn=findViewById(R.id.logoutBtnMain);

        //Bluetooth Adapter to communicate with bluetooth
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        //check if Bluetooth is available or not
        if (mBtAdapter==null)
            mStatusTxt.setText("Bluetooth is not available");
        else
        {
            mStatusTxt.setText("Bluetooth is available");
            //set image according to On/Off state
            if (mBtAdapter.isEnabled())
                mBtIv.setImageResource(R.drawable.ic_action_on);
            else
                mBtIv.setImageResource(R.drawable.ic_action_off);
        }
        //On button click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mOnBtn);
                if (!mBtAdapter.isEnabled())
                {
                    showToast("Turning Bluetooth On");
                    //Issues a request to enable Bluetooth through the system settings without stopping your application
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, REQUEST_ENABLE_BT);
                }
                else
                    showToast("Bluetooth is already on");
            }
        });
        //Off button click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mOffBtn);
                if (mBtAdapter.isEnabled())
                {
                    mBtAdapter.disable();
                    showToast("Turning bluetooth off");
                    mBtIv.setImageResource(R.drawable.ic_action_off);
                }
                else
                    showToast("Bluetooth is already off");
            }
        });
        //Discovery button click
        mDiscoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mDiscoBtn);
                if (!mBtAdapter.isDiscovering())
                {
                    showToast("Making device discoverable");
                    Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(discoverable, REQUEST_DISCOVERABLE_BT);
                }
            }
        });

        //Get paired devices button click
        mPairedListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mPairedListBtn);
                Intent pairedIntent = new Intent(MainActivity.this, DeviceList.class);
                startActivity(pairedIntent);
            }
        });
        //Logout button click
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mLogoutBtn);
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
    //toast message method
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_CANCELED)
        {
            showToast("Permission denied");
        }
        else if (requestCode == REQUEST_ENABLE_BT)
        {
            mBtIv.setImageResource(R.drawable.ic_action_on);
            showToast("Bluetooth on");
        }
        else if (requestCode == REQUEST_DISCOVERABLE_BT)
        {
            showToast("Device discoverable for 120 seconds");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
/* ----------------Bluetooth API provides different Constants---------------
-> ACTION_REQUEST_DISCOVERABLE (turn on discovering of bluetooth)
-> ACTION_STATE_CHANGED (notify that Bluetooth state has been changed)
-> ACTION_FOUND (receiving information about each device that is discovered)
 */

/*-----onActivityResult(int requestCode, int resultCode, Intent data)--------
-> receives result of starting another activity
-> use          startActivityForResult()
-> instead of   startActivity())
 */

/*---------------------------Request codes-----------------------------------
-> integer value
-> helps identify the Intent
-> Can only use lower 16 bits for requestCode
-> so from 0 to 65535
 */

/*-----------------------------Result codes----------------------------------
-> RESULT_OK (operation successful)
-> RESULT_CANCELED (user backed out or the operation failed for some reason)
 */

/*----------------------------Intent data------------------------------------
-> Activity sends the result as another Intent object
-> carries result data
 */