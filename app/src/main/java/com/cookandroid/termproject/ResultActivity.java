package com.cookandroid.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView textName;
    ImageView imgResult;
    Button btnHome2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("행운의 부적 테스트");

        textName = (TextView) findViewById(R.id.TextName);
        imgResult = (ImageView) findViewById(R.id.ImgResult);
        btnHome2 = (Button) findViewById(R.id.BtnHome2);

        // 결과 이미지
        final int draw[] = {R.drawable.health, R.drawable.love, R.drawable.money, R.drawable.luck, R.drawable.everything};

        // 인텐트 -> 사용자 이름, 결과 받아서 저장
        Intent inIntent = getIntent();
        final String nameValue = inIntent.getStringExtra("UserName");
        final int[] finalResult = inIntent.getIntArrayExtra("Results");

        // 결고 화면
        textName.setText(nameValue + "님의");

        if(finalResult[0] > finalResult[1] && finalResult[0] > finalResult[2] && finalResult[0] > finalResult[3]){
            imgResult.setImageResource(draw[0]);
        }
        else if(finalResult[1] > finalResult[0] && finalResult[1] > finalResult[2] && finalResult[1] > finalResult[3]){
            imgResult.setImageResource(draw[1]);
        }
        else if(finalResult[2] > finalResult[0] && finalResult[2] > finalResult[1] && finalResult[2] > finalResult[3]){
            imgResult.setImageResource(draw[2]);
        }
        else if(finalResult[3] > finalResult[0] && finalResult[3] > finalResult[1] && finalResult[3] > finalResult[2]){
            imgResult.setImageResource(draw[3]);
        }
        else{
            imgResult.setImageResource(draw[4]);
        }

        // 처음으로 버튼
        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);      // 메인 화면으로 넘어감
            }
        });
    }
}
