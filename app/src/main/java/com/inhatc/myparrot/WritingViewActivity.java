package com.inhatc.myparrot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class WritingViewActivity extends AppCompatActivity {

    private String title;
    private String content;
    private String writename;
    private String time;
    private int views;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_view);

        TextView road_title = (TextView)findViewById(R.id.textView7);
        TextView road_content = (TextView)findViewById(R.id.textView12);
        TextView road_nickname = (TextView)findViewById(R.id.textView8);
        TextView road_time = (TextView)findViewById(R.id.textView9);
        TextView road_views = (TextView)findViewById(R.id.textView10);
        ImageView road_img1 = (ImageView)findViewById(R.id.imageView6);
        ImageView road_img2 = (ImageView)findViewById(R.id.imageView7);
        ImageView road_img3 = (ImageView)findViewById(R.id.imageView8);
        ImageView road_img4 = (ImageView)findViewById(R.id.imageView9);
        ImageView road_img5 = (ImageView)findViewById(R.id.imageView10);

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        content = getIntent.getStringExtra("content");
        writename = getIntent.getStringExtra("name");
        time = getIntent.getStringExtra("time");
        views = getIntent.getIntExtra("views", 1);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://myparrot-bfc92.appspot.com");
        StorageReference storageRef = storage.getReference();

        if(getIntent.getStringExtra("img1") != null){
            img1 = getIntent.getStringExtra("img1");
            storageRef.child("images/" + img1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(road_img1);
                }
            });
        }
        if(getIntent.getStringExtra("img2") != null){
            img2 = getIntent.getStringExtra("img2");
            storageRef.child("images/" + img2).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(road_img2);
                }
            });
        }
        if(getIntent.getStringExtra("img3") != null){
            img3 = getIntent.getStringExtra("img3");
            storageRef.child("images/" + img3).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(road_img3);
                }
            });
        }
        if(getIntent.getStringExtra("img4") != null){
            img4 = getIntent.getStringExtra("img4");
            storageRef.child("images/" + img4).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(road_img4);
                }
            });
        }
        if(getIntent.getStringExtra("img5") != null){
            img5 = getIntent.getStringExtra("img5");
            storageRef.child("images/" + img5).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(road_img5);
                }
            });
        }

        road_title.setText(title);
        road_content.setText(content);
        road_nickname.setText("작성자 " + writename);
        road_time.setText("작성날짜 "+time);
        road_views.setText("조회수" + String.valueOf(views));

    }
}
