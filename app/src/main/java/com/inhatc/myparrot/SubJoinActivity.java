package com.inhatc.myparrot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubJoinActivity extends AppCompatActivity {

    private EditText name_join;
    private TextView name_view;
    private Button name_btn;
    private Button join_btn;
    private String nickname;
    private String nickname_confirm;
    private boolean isEnabled;
    FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_join);
        Intent joinIntent = getIntent();
        String cu = joinIntent.getExtras().getString("uid");
        String email = joinIntent.getExtras().getString("email");
        String pwd = joinIntent.getExtras().getString("passwd");
        String number = joinIntent.getExtras().getString("number");
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        name_join=(EditText)findViewById(R.id.join_name);
        name_view=(TextView)findViewById(R.id.name_view);

        name_btn = (Button)findViewById(R.id.name_btn);
        join_btn = (Button)findViewById(R.id.join_btn);

        name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List arData = new ArrayList();
                nickname_confirm = name_join.getText().toString().trim();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            arData.add(fileSnapshot.child("Info/nickname").getValue(String.class));
                        }

                        if(arData.contains(nickname_confirm)){
                            name_view.setText("이미 존재하는 닉네임 입니다.");
                            isEnabled = false;
                        } else{
                            name_view.setText("가입 가능한 닉네임 입니다.");
                            isEnabled = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nickname = name_join.getText().toString().trim();
                if(nickname.equals(nickname_confirm) && isEnabled) {
                    userInfo userdata = new userInfo(email, pwd, number, nickname);
                    mDatabase.child(cu).child("Info").setValue(userdata);
                    Toast.makeText(SubJoinActivity.this, "회원 가입 성공!", Toast.LENGTH_LONG).show();
                    Intent joinIntent = new Intent(SubJoinActivity.this, LoginActivity.class);
                    startActivity(joinIntent);
                    finish();
                } else{
                    Toast.makeText(SubJoinActivity.this, "닉네임 중복 확인 후 가입해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}