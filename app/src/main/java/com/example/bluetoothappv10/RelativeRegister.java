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

public class RelativeRegister extends AppCompatActivity {
    RadioButton mMaleRelRegRB,mFemRelRegRB;
    Button mRelRegBtn;
    EditText mNameRRET, mContactRRET,mAffPatIDRRET, mEmailRRET, mPassRRET, mRePassRRET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_register);
        mNameRRET = findViewById(R.id.nameRRET);
        mContactRRET = findViewById(R.id.contactNoRRET);
        mAffPatIDRRET = findViewById(R.id.affPatIdRRET);
        mEmailRRET = findViewById(R.id.emailRRET);
        mPassRRET = findViewById(R.id.passwordRRET);
        mRePassRRET = findViewById(R.id.reenterpassRRET);
        mRelRegBtn = findViewById(R.id.relativeRegBtn);
        mMaleRelRegRB = findViewById(R.id.MaleRRRB);
        mFemRelRegRB = findViewById(R.id.FemRRRB);
        mMaleRelRegRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFemRelRegRB.setChecked(false);
            }
        });
        mFemRelRegRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaleRelRegRB.setChecked(false);
            }
        });
        mRelRegBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mRelRegBtn);
                if(mPassRRET.getText().toString().trim().equals("") || mRePassRRET.getText().toString().trim().equals(""))
                {
                    showToast("Must contain account Password");
                }
                else
                {
                    if (mPassRRET.getText().toString().equals(mRePassRRET.getText().toString())) {
                        showToast("Registered successfully!");
                        showToast("Login now to start Monitoring");
                        Intent loginInt = new Intent(RelativeRegister.this, Login.class);
                        startActivity(loginInt);
                    }
                    else
                        showToast("Passwords don't match!");
                }
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(RelativeRegister.this, R.anim.bounce);
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
