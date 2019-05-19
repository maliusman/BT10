package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DoctorRegister extends AppCompatActivity {
    RadioButton mMaleDocRegRB,mFemDocRegRB;
    Button mDocRegBtn;
    EditText mNameDRET,mDegDRET, mSpecialDRET, mContactDRET, mEmailDRET, mPassDRET, mRePassDRET;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        mNameDRET = findViewById(R.id.nameDRET);
        mDegDRET = findViewById(R.id.degreeDRET);
        mSpecialDRET = findViewById(R.id.specialDRET);
        mContactDRET = findViewById(R.id.contactNoDRET);
        mEmailDRET = findViewById(R.id.emailDRET);
        mPassDRET = findViewById(R.id.passwordDRET);
        mRePassDRET = findViewById(R.id.reenterpassDRET);
        mDocRegBtn = findViewById(R.id.doctorRegBtn);
        mMaleDocRegRB = findViewById(R.id.MaleDRRB);
        mFemDocRegRB = findViewById(R.id.FemDRRB);
        mMaleDocRegRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFemDocRegRB.setChecked(false);
            }
        });
        mFemDocRegRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaleDocRegRB.setChecked(false);
            }
        });
        mDocRegBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mDocRegBtn);
                if(mPassDRET.getText().toString().trim().equals("") || mRePassDRET.getText().toString().trim().equals(""))
                {
                    showToast("Must contain account Password");
                }
                else
                {
                    if (mPassDRET.getText().toString().equals(mRePassDRET.getText().toString()))
                    {
                        showToast("Registered successfully!");
                        showToast("Login now to start Monitoring");
                        Intent loginInt = new Intent(DoctorRegister.this, Login.class);
                        startActivity(loginInt);
                    } else
                        showToast("Passwords don't match!");
                }
            }
        });
    }
    //toast message method
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(DoctorRegister.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
