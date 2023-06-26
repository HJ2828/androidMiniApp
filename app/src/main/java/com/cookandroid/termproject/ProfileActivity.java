package com.cookandroid.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    Button btnHome1, btnNext1, btnName;
    TextView tvName, toastText;
    EditText dlgEdtName;
    View dialogView, toastView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setTitle("행운의 부적 테스트");

        Intent inIntent = getIntent();

        btnHome1 = (Button) findViewById(R.id.BtnHome1);
        btnNext1 = (Button) findViewById(R.id.BtnNext1);
        btnName = (Button) findViewById(R.id.BtnName);
        tvName = (TextView) findViewById(R.id.TvName);

        // 이름 입력 버튼
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog1.xml 파일을 인플레이트해 dialogView에 대입
                dialogView = (View) View.inflate(ProfileActivity.this, R.layout.dialog1, null);
                // AlertDialog.Builder 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(ProfileActivity.this);
                dlg.setTitle("테스터 정보 입력");
                dlg.setIcon(R.drawable.user);
                dlg.setView(dialogView);        // 인프레이트한 dialogView를 대화상자로 사용

                // setPositionButton(문자열, 리스너)메서드 만들기
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 사용자 이름 위젯을 변수에 대입하고 가져와 설정
                        dlgEdtName = (EditText) dialogView.findViewById(R.id.DlgEdtName);
                        tvName.setText(dlgEdtName.getText().toString());
                    }
                });
                dlg.show();     // 대화상자 보여줌
            }
        });

        // 처음으로 버튼
        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);  // 첫 화면으로 넘어감
            }
        });

        // 다음 버튼
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dlgEdtName == null || tvName.length() == 0){     // 이름 입력안하면 토스트 띄우기
                    Toast toast = new Toast(ProfileActivity.this);  // 빈 토스트 생성

                    // toast1.xml 파일을 인플레이트해 toastView에 대입
                    toastView = (View) View.inflate(ProfileActivity.this, R.layout.toast1, null);
                    // toast1.xml의 TextView 위젯을 변수에 대입
                    toastText = (TextView) toastView.findViewById(R.id.ToastText1);

                    toastText.setText("이름을 입력하세요");
                    toast.setView(toastView);
                    toast.show();
                }
                else{   // 이름 입력했으면 다음 화면으로 넘어감
                    Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                    intent.putExtra("UserName", tvName.getText().toString());
                    startActivity(intent);
                    //startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            String n = data.getStringExtra("rName");
            tvName.setText(n);
        }
    }
}
