package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RescueRegister extends AppCompatActivity {
    Button mRescueRegBtn;
    EditText mNameRescueRegET, mContactRescueRegET, mEmailRescueRegET, mPassRescueRegET, mRePassRescueRegET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_register);
        mNameRescueRegET = findViewById(R.id.nameRescueRegET);
        mContactRescueRegET = findViewById(R.id.contactNoRescueRegET);
        mEmailRescueRegET = findViewById(R.id.emailRescueRegET);
        mPassRescueRegET = findViewById(R.id.passwordRescueRegET);
        mRePassRescueRegET = findViewById(R.id.reenterpassRescueRegET);
        mRescueRegBtn=findViewById(R.id.rescueRegBtn);
        mRescueRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mRescueRegBtn);
                if(mPassRescueRegET.getText().toString().trim().equals("") || mRePassRescueRegET.getText().toString().trim().equals(""))
                {
                    showToast("Must contain account Password");
                }
                else
                {
                    if (mPassRescueRegET.getText().toString().equals(mRePassRescueRegET.getText().toString())) {
                        showToast("Registered successfully!");
                        Intent loginInt = new Intent(RescueRegister.this, Login.class);
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
        final Animation myAnim = AnimationUtils.loadAnimation(RescueRegister.this, R.anim.bounce);
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
