package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner mRegRoleSpinner;
    TextView mRoleTxt;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegRoleSpinner = (Spinner) findViewById(R.id.regRoleSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mRegRoleSpinner.setAdapter(adapter);
        mRoleTxt=findViewById(R.id.RoleTxt);
        mRegRoleSpinner.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view,int pos, long id)
    {
        if(parent.getItemAtPosition(pos).equals("Patient"))
        {
            role = "Patient";
            Intent patReg = new Intent(Register.this, PatientRegister.class);
            startActivity(patReg);
        }
        else if(parent.getItemAtPosition(pos).equals("Doctor"))
        {
            role = "Doctor";
            Intent docReg = new Intent(Register.this, DoctorRegister.class);
            startActivity(docReg);
        }
        else if(parent.getItemAtPosition(pos).equals("Relative"))
        {
            role = "Relative";
            Intent relReg = new Intent(Register.this, RelativeRegister.class);
            startActivity(relReg);
        }
    }
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
