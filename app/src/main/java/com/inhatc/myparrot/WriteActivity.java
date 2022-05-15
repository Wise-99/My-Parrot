package com.inhatc.myparrot;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String title;
    private String content;
    private Button write_btn;
    private Button img_btn;
    private String item;
    private DatabaseReference mDatabase;
    private String uid;
    private int views = 0;
    private String name;
    private ArrayList <Uri> imgList = new ArrayList<>();
    private ArrayList <String> imgNameList = new ArrayList<>();
    private Writing writing;

    //UI
    ImageView image1, image2, image3, image4, image5;

    //constant
    final int PICTURE_REQUEST_CODE = 100;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(WriteActivity.this, "로그인 후 글을 작성할 수 있습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // 현재 시간을 "yyyy-MM-dd hh:mm:ss"로 표시하는 메소드
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter boardAdapter = ArrayAdapter.createFromResource(this, R.array.board, R.layout.spinner_layout);

        boardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(boardAdapter); //어댑터에 연결

        EditText writeTitle = (EditText)findViewById(R.id.writeTitle);
        EditText writeContent =(EditText)findViewById(R.id.writeContent);

        //UI
        image1 = (ImageView)findViewById(R.id.imageView1);
        image2 = (ImageView)findViewById(R.id.imageView2);
        image3 = (ImageView)findViewById(R.id.imageView3);
        image4 = (ImageView)findViewById(R.id.imageView4);
        image5 = (ImageView)findViewById(R.id.imageView5);

        img_btn = (Button)findViewById(R.id.img_btn);
        img_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
            }
        });



        write_btn = (Button)findViewById(R.id.write_btn);

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = writeTitle.getText().toString();
                content = writeContent.getText().toString();
                item = spinner.getSelectedItem().toString();
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();

                mDatabase = FirebaseDatabase.getInstance().getReference("users");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            if(snapshot.getKey().equals(uid)){
                                name = snapshot.child("Info/nickname").getValue(String.class); // 닉네임 가져오기

                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                uploadImg(); // 이미지 업로드

                                //업로드한 이미지 갯수마다 넘기는 객체 다르게 생성
                                switch (imgList.size()) {
                                    case 0:
                                        writing = new Writing(name, title, content, item, getTime(), views);
                                        break;
                                    case 1:
                                        writing = new Writing(name, title, content, item, getTime(), imgNameList.get(0), views);
                                        break;
                                    case 2:
                                        writing = new Writing(name, title, content, item, getTime(), imgNameList.get(0), imgNameList.get(1), views);
                                        break;
                                    case 3:
                                        writing = new Writing(name, title, content, item, getTime(), imgNameList.get(0),imgNameList.get(1), imgNameList.get(2), views);
                                        break;
                                    case 4:
                                        writing = new Writing(name, title, content, item, getTime(), imgNameList.get(0),imgNameList.get(1),imgNameList.get(2), imgNameList.get(3), views);
                                        break;
                                    case 5:
                                        writing = new Writing(name, title, content, item, getTime(), imgNameList.get(0),imgNameList.get(1),imgNameList.get(2),imgNameList.get(3),imgNameList.get(4), views);
                                        break;
                                }
                                mDatabase.child("writing").push().setValue(writing); // DB에 글 저장
                                Toast.makeText(WriteActivity.this, "글 작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                Intent writeIntent = new Intent(WriteActivity.this, MainActivity.class);
                                startActivity(writeIntent);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //기존 이미지 지우기
                image1.setImageResource(0);
                image2.setImageResource(0);
                image3.setImageResource(0);
                image4.setImageResource(0);
                image5.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if (clipData != null) {
                    for (int i = 0; i < 5; i++) {
                        if (i < clipData.getItemCount()) {
                            Uri urione = clipData.getItemAt(i).getUri();
                            System.out.println(urione);
                            switch (i) {
                                case 0:
                                    image1.setImageURI(urione);
                                    imgList.add(urione);
                                    break;
                                case 1:
                                    image2.setImageURI(urione);
                                    imgList.add(urione);
                                    break;
                                case 2:
                                    image3.setImageURI(urione);
                                    imgList.add(urione);
                                    break;
                                case 3:
                                    image4.setImageURI(urione);
                                    imgList.add(urione);
                                    break;
                                case 4:
                                    image5.setImageURI(urione);
                                    imgList.add(urione);
                                    break;
                            }
                        }
                    }
                } else if (uri != null) {
                    image1.setImageURI(uri);
                }
            }
        }
    }

    private void uploadImg(){
        if(!imgList.isEmpty()){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            for(int i = 0; i < imgList.size(); i++) {
                //파일 이름 지정
                String filename = item + "_" + title + "_" + i;
                imgNameList.add(filename);

                //storage 주소와 폴더 파일 이름 지정
                StorageReference storageRef = storage.getReferenceFromUrl("gs://myparrot-bfc92.appspot.com").child("images/" + filename);
                storageRef.putFile(imgList.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
            }
        }
    }
}
