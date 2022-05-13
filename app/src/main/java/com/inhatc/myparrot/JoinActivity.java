package com.inhatc.myparrot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    private EditText email_join;
    private EditText pwd1_join;
    private EditText pwd2_join;
    private EditText number_join;
    private Button btn;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email_join=(EditText)findViewById(R.id.join_email);
        pwd1_join = (EditText) findViewById(R.id.join_password1);
        pwd2_join = (EditText) findViewById(R.id.join_password2);
        number_join=(EditText)findViewById(R.id.join_phone);
        btn = (Button) findViewById(R.id.join_next_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_join.getText().toString().trim();
                String pwd1 = pwd1_join.getText().toString().trim();
                String pwd2 = pwd2_join.getText().toString().trim();
                String number = number_join.getText().toString().trim();
                //공백인 부분을 제거하고 보여주는 trim();

                if (!pwd1.equals(pwd2)) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 확인해주세요!", Toast.LENGTH_LONG ).show();
                    return;
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd2)
                            .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        String cu = firebaseAuth.getUid();

                                        Toast.makeText(JoinActivity.this, "닉네임을 설정해주세요.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(JoinActivity.this, SubJoinActivity.class);
                                        intent.putExtra("uid", cu);
                                        intent.putExtra("email", email);
                                        intent.putExtra("passwd", pwd2);
                                        intent.putExtra("number", number);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(JoinActivity.this, "입력 사항을 확인해주세요!", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            });
                }
            }
        });
    }
}
