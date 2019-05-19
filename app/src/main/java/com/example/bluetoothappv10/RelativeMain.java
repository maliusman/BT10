package com.example.bluetoothappv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class RelativeMain extends AppCompatActivity {
Button mAffPatHistoryBtn, mAffPatECGBtn, mLogoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_main);
        mAffPatHistoryBtn=findViewById(R.id.viewHistoryBtnRM);
        mAffPatECGBtn=findViewById(R.id.viewECGBtnRM);
        mLogoutBtn=findViewById(R.id.logoutBtnRM);
        mAffPatHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mAffPatHistoryBtn);
                Intent relHis = new Intent(RelativeMain.this, RelativeHistory.class);
                startActivity(relHis);

            }
        });
        mAffPatECGBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnAnimator(mAffPatECGBtn);
                Intent relECG = new Intent(RelativeMain.this, RelativeViewECG.class);
                startActivity(relECG);
            }
        });
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnimator(mLogoutBtn);
            }
        });
    }
    void btnAnimator(Button btn)
    {
        final Animation myAnim = AnimationUtils.loadAnimation(RelativeMain.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
