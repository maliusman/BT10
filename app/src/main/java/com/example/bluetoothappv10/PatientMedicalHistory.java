package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class PatientMedicalHistory extends AppCompatActivity {
    EditText mMedicine1ETPMH,mMedicine2ETPMH,mMedicine3ETPMH, mAllergy1ETPMH,mAllergy2ETPMH, mAllergy3ETPMH,
            mHistory1ETPMH,mHistory2ETPMH, mHistory3ETPMH, mCurrentDisease1ETPMH, mCurrentDisease2ETPMH, mCurrentDisease3ETPMH;
    Button mPMHSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical_history);

        mMedicine1ETPMH = findViewById(R.id.Medicine1ETPMH);
        mMedicine2ETPMH = findViewById(R.id.Medicine2ETPMH);
        mMedicine3ETPMH = findViewById(R.id.Medicine3ETPMH);

        mAllergy1ETPMH = findViewById(R.id.Allergy1ETPMH);
        mAllergy2ETPMH = findViewById(R.id.Allergy2ETPMH);
        mAllergy3ETPMH = findViewById(R.id.Allergy3ETPMH);

        mHistory1ETPMH = findViewById(R.id.History1ETPMH);
        mHistory2ETPMH = findViewById(R.id.History2ETPMH);
        mHistory3ETPMH = findViewById(R.id.History3ETPMH);

        mCurrentDisease1ETPMH = findViewById(R.id.CurrentDiseases1ETPMH);
        mCurrentDisease2ETPMH = findViewById(R.id.CurrentDiseases2ETPMH);
        mCurrentDisease3ETPMH = findViewById(R.id.CurrentDiseases3ETPMH);




        mPMHSubmitBtn = findViewById(R.id.PMHSubmitBtn);


        mPMHSubmitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mPMHSubmitBtn);
                Intent loginInt = new Intent(PatientMedicalHistory.this, Login.class);
                startActivity(loginInt);
                finish();
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(PatientMedicalHistory.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
