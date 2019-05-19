package com.example.bluetoothappv10;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;


import android.widget.RadioButton;
import android.widget.Toast;

public class PatientRegister extends AppCompatActivity {

    RadioButton mMaleRBtn,mFemRBtn;
    Button mPatRegBtn;
    EditText mNameET,mAgePRET, mEmailET, mContactET, mDocIDET, mPassET, mRePassET;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        mNameET=findViewById(R.id.namePRET);
        mAgePRET=findViewById(R.id.agePRET);
        mEmailET=findViewById(R.id.emailPRET);
        mContactET=findViewById(R.id.contactNoPRET);
        mDocIDET=findViewById(R.id.docIdPRET);
        mPassET=findViewById(R.id.passwordPRET);
        mRePassET=findViewById(R.id.reenterpasswPRET);
        mPatRegBtn=findViewById(R.id.patientRegBtn);
        mMaleRBtn=findViewById(R.id.MaleRBtn);
        mFemRBtn=findViewById(R.id.FemRBtn);
        mMaleRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFemRBtn.setChecked(false);
            }
        });
        mFemRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaleRBtn.setChecked(false);
            }
        });
        mPatRegBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mPatRegBtn);
                if(mPassET.getText().toString().trim().equals("") || mRePassET.getText().toString().trim().equals(""))
                {
                    showToast("Must contain account Password");
                }
                else
                {
                    if (mPassET.getText().toString().equals(mRePassET.getText().toString())) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PatientRegister.this);
                        alertDialogBuilder.setMessage("Do you have any medical history?");
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                Intent patientHistory = new Intent(PatientRegister.this, PatientMedicalHistory.class);
                                startActivity(patientHistory);
                            }
                        });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                showToast("Registered successfully! Login now to start Monitoring");
                                Intent loginInt = new Intent(PatientRegister.this, Login.class);
                                startActivity(loginInt);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else
                        showToast("Passwords don't match!");
                }
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(PatientRegister.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
    //toast message method
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
