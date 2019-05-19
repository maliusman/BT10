package com.example.bluetoothappv10;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends AppCompatActivity {

    //widgets
    TextView heading;


    ListView devicelist;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        //Calling widgets

        devicelist = (ListView)findViewById(R.id.listView);

        heading = (TextView)findViewById(R.id.textView);
        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        pairedDevicesList();
        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }


    }

    private void pairedDevicesList()
    {
        //heading.setVisibility(View.VISIBLE);
        //info.setVisibility(View.GONE);

        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            Intent i = new Intent(DeviceList.this, ViewActivity.class);

            //Change the activity.
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.share_menu,menu);
//        return true ;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId()){
//            case R.id.shareButton:
//                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
//                if (btAdapter == null) {
//                    Toast.makeText(this,"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
//                }
//
//                ApplicationInfo app = getApplicationContext().getApplicationInfo();
//                String filePath = app.sourceDir;
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//
//                intent.setType("*/*");
//
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//                startActivity(Intent.createChooser(intent, "Share App"));
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
