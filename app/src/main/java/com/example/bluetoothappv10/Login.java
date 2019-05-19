package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Login extends AppCompatActivity
{
    Button mLoginBtn;
    EditText mEmail, mPassword;
    Spinner mRoleSpinner;
    TextView mRegisterTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mRoleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mRoleSpinner.setAdapter(adapter);
        mEmail=findViewById(R.id.emailLoginET);
        mPassword=findViewById(R.id.passLoginET);
        mLoginBtn=findViewById(R.id.loginBtn);
        mRegisterTxt=findViewById(R.id.registerTxt);

        mLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mLoginBtn);
                if(mRoleSpinner.getSelectedItem().equals("Patient"))
                {
                    Intent patientLogin = new Intent(Login.this, MainActivity.class);
                    startActivity(patientLogin);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Doctor"))
                {
//                    Intent mainScreen = new Intent(Login.this, MainActivity.class);
//                    startActivity(mainScreen);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Relative"))
                {
                    Intent relMainScreen = new Intent(Login.this, RelativeMain.class);
                    startActivity(relMainScreen);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Rescue Services"))
                {
                    Intent rescueNotify = new Intent(Login.this, RescueAlert.class);
                    startActivity(rescueNotify);
                }
            }
        });

        mRegisterTxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mRoleSpinner.getSelectedItem().equals("Patient"))
                {
                    Intent patientReg = new Intent(Login.this, PatientRegister.class);
                    startActivity(patientReg);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Doctor"))
                {
                    Intent doctorReg = new Intent(Login.this, DoctorRegister.class);
                    startActivity(doctorReg);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Relative"))
                {
                    Intent relativeReg = new Intent(Login.this, RelativeRegister.class);
                    startActivity(relativeReg);
                }
                else if(mRoleSpinner.getSelectedItem().equals("Rescue Services"))
                {
                    Intent rescueReg = new Intent(Login.this, RescueRegister.class);
                    startActivity(rescueReg);
                }
//                Intent register = new Intent(Login.this, Register.class);
//                startActivity(register);
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(Login.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
