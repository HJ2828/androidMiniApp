package com.cookandroid.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    View toastView;
    Button btnPrev2, btnNext2;
    TextView textArray[] = new TextView[11];
    TextView lastText, toastText;
    RadioGroup rGroup[] = new RadioGroup[11];
    RadioButton r1[] = new RadioButton[2], r2[] = new RadioButton[2], r3[] = new RadioButton[2], r4[] = new RadioButton[2],
            r5[] = new RadioButton[2], r6[] = new RadioButton[2], r7[] = new RadioButton[2], r8[] = new RadioButton[2],
            r9[] = new RadioButton[2], r10[] = new RadioButton[2], r11[] = new RadioButton[2];
    RadioButton rr[][] = new RadioButton[11][2];

    final int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // 문제당 들어가는 선택되는 번호 저장
    final int result[] = {0, 0, 0, 0};  // 라디오버튼 눌렀을 시 값 저장 되는 변수 -> 건강, 사랑, 재물, 행운 순서

    boolean lastCheck = false;    // 모두 선택하고 결과보기 버튼을 눌렀는지 확인하는 변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        setTitle("행운의 부적 테스트");

        final int tArray[] = {R.id.Test1, R.id.Test2, R.id.Test3, R.id.Test4, R.id.Test5,
                R.id.Test6, R.id.Test7, R.id.Test8, R.id.Test9, R.id.Test10, R.id.Test11};
        final int rGroupId[] = {R.id.RdoGroup1, R.id.RdoGroup2, R.id.RdoGroup3, R.id.RdoGroup4, R.id.RdoGroup5,
                R.id.RdoGroup6, R.id.RdoGroup7, R.id.RdoGroup8, R.id.RdoGroup9, R.id.RdoGroup10, R.id.RdoGroup11};
        final int rId[] = {R.id.Rdo1_1, R.id.Rdo2_1, R.id.Rdo3_1, R.id.Rdo4_1, R.id.Rdo5_1, R.id.Rdo6_1,R.id.Rdo7_1,
                R.id.Rdo8_1, R.id.Rdo9_1, R.id.Rdo10_1, R.id.Rdo11_1, R.id.Rdo1_2, R.id.Rdo2_2, R.id.Rdo3_2,
                R.id.Rdo4_2, R.id.Rdo5_2, R.id.Rdo6_2, R.id.Rdo7_2, R.id.Rdo8_2, R.id.Rdo9_2, R.id.Rdo10_2, R.id.Rdo11_2};

        btnPrev2 = (Button) findViewById(R.id.BtnPrev2);
        btnNext2 = (Button) findViewById(R.id.BtnNext2);
        lastText = (TextView) findViewById(R.id.LastText);
        for(int i=0; i<11; i++){
            textArray[i] = (TextView) findViewById(tArray[i]);
            rGroup[i] = (RadioGroup) findViewById(rGroupId[i]);
        }
        for(int i=0; i<11; i++){
            for(int j=0; j<2; j++){
                rr[i][j] = (RadioButton) findViewById(rId[10*j+i+j]);
            }
        }

        // 인텐트 -> 사용자 이름 받아서 저장
        Intent inIntent = getIntent();
        final String nameValue = inIntent.getStringExtra("UserName");

        // 시작 토스트 띄우기
        Toast toast = new Toast(TestActivity.this);  // 빈 토스트 생성
        // toast1.xml 파일을 인플레이트해 toastView에 대입
        toastView = (View) View.inflate(TestActivity.this, R.layout.toast1, null);
        // toast1.xml의 TextView 위젯을 변수에 대입
        toastText = (TextView) toastView.findViewById(R.id.ToastText1);
        toastText.setText(nameValue + "님의 테스트를 시작합니다.");
        toast.setView(toastView);
        toast.show();

        // 1번 질문 제외 invisible
        lastText.setVisibility(View.INVISIBLE);
        for(int i=1; i<11; i++){
            textArray[i].setVisibility(View.INVISIBLE);
            rGroup[i].setVisibility(View.INVISIBLE);
        }

        // 라디오 버튼
        for(int i=0; i<11; i++){
            for(int j=0; j<2; j++){
                final int index_i, index_j;
                index_i = i;
                index_j = j;

                rr[index_i][index_j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(index_i==10){
                            lastText.setVisibility(View.VISIBLE);
                            lastCheck = true;
                        }
                        else{
                            textArray[index_i+1].setVisibility(View.VISIBLE);
                            rGroup[index_i+1].setVisibility(View.VISIBLE);
                        }
                        check[index_i] = index_j;
                    }
                });
            }
        }
        
        // 이전 버튼
        btnPrev2.setOnClickListener(view -> {
            // ProfileActivity 인텟트 생성해서 "Name"에 값 추가
            Intent outIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            outIntent.putExtra("rName", nameValue);
            setResult(RESULT_OK, outIntent);
            finish();
        });

        // 결과 보기 버튼
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastCheck == true){
                    checkResult();

                    Intent outintent = new Intent(getApplicationContext(), ResultActivity.class);
                    final int[] finalResult = new int[]{result[0], result[1], result[2], result[3]};
                    outintent.putExtra("UserName", nameValue);
                    outintent.putExtra("Results", finalResult);
                    startActivity(outintent);
                }
                else{
                    // 토스트 띄우기
                    Toast toast = new Toast(TestActivity.this);  // 빈 토스트 생성
                    // toast1.xml 파일을 인플레이트해 toastView에 대입
                    toastView = (View) View.inflate(TestActivity.this, R.layout.toast1, null);
                    // toast1.xml의 TextView 위젯을 변수에 대입
                    toastText = (TextView) toastView.findViewById(R.id.ToastText1);
                    toastText.setText("모든 문제를 선택완료 해주세요");
                    toast.setView(toastView);
                    toast.show();
                }

            }
        });
    }

    // 라디오 버튼 선택한 결과를 계산하는 함수
    void checkResult(){
        for(int i=0; i<11; i++){
            final int index;
            index = i;
            switch (index){
                case 0:
                case 2:
                case 8:
                    if(check[index] == 0){
                        result[0]++;    // 건강
                        result[3]++;    // 행운
                    }
                    else if(check[0] == 1){
                        result[1]++;    // 사랑
                        result[2]++;    // 재물
                    }
                    break;
                case 1:
                case 10:
                    if(check[index] == 0){
                        result[0]++;    // 건강
                        result[2]++;    // 재물
                    }
                    else if(check[0] == 1){
                        result[1]++;    // 사랑
                        result[3]++;    // 행운
                    }
                    break;
                case 3:
                case 7:
                    if(check[index] == 0){
                        result[2]++;    // 재물
                        result[3]++;    // 행운
                    }
                    else if(check[0] == 1){
                        result[0]++;    // 건강
                        result[1]++;    // 사랑
                    }
                    break;
                case 4:
                case 6:
                    if(check[index] == 0){
                        result[1]++;    // 사랑
                        result[3]++;    // 행운
                    }
                    else if(check[0] == 1){
                        result[0]++;    // 건강
                        result[2]++;    // 재물
                    }
                    break;
                case 5:
                case 9:
                    if(check[index] == 0){
                        result[0]++;    // 건강
                        result[1]++;    // 사랑
                    }
                    else if(check[0] == 1){
                        result[2]++;    // 재물
                        result[3]++;    // 행운
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
