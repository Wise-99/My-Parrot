package com.inhatc.myparrot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class WritingReviseActivity extends AppCompatActivity {
    private String title;
    private String content;
    private String tab;
    private String writing_uid;
    private String u_title;
    private String u_content;
    private String u_tab;
    private Button revise_btn;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_revise);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter boardAdapter = ArrayAdapter.createFromResource(this, R.array.board, R.layout.spinner_layout);
        boardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(boardAdapter); //어댑터에 연결

        // Intent로 값 받기
        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        content = getIntent.getStringExtra("content");
        tab = getIntent.getStringExtra("tab");
        writing_uid = getIntent.getStringExtra("writingUid");

        // 글 제목 불러오기
        TextView revise_title = (TextView)findViewById(R.id.reviseTitle);
        revise_title.setText(title);

        // 글 내용 불러오기
        TextView revise_content = (TextView)findViewById(R.id.reviseContent);
        revise_content.setText(content);

        // 선택된 게시판 보여주기
        if(tab.equals("후기"))
            spinner.setSelection(1);
        else if(tab.equals("자랑"))
            spinner.setSelection(2);
        else if(tab.equals("질문"))
            spinner.setSelection(3);
        else if(tab.equals("분양"))
            spinner.setSelection(4);

        revise_btn = (Button)findViewById(R.id.revise_btn);
        revise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_title = revise_title.getText().toString();
                u_content = revise_content.getText().toString();
                u_tab = spinner.getSelectedItem().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("title", u_title);
                hashMap.put("content", u_content);
                hashMap.put("tab", u_tab);

                mDatabase.child("writing/" + writing_uid).updateChildren(hashMap);
                Toast.makeText(WritingReviseActivity.this, "글 수정이 완료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
