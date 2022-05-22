package com.inhatc.myparrot;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WritingViewActivity extends AppCompatActivity {

    private String title;
    private String content;
    private String writename;
    private String time;
    private String tab;
    private int views;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private CommentAdapter adapter;
    private Button comment_btn;
    private Button writingDelete_btn;
    private Button writingRevise_btn;
    private Button commentDelete_btn;
    private TextView comment_name;
    private TextView comment_content;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_u = FirebaseDatabase.getInstance().getReference("users");
    private DatabaseReference mDatabase_w = FirebaseDatabase.getInstance().getReference("writing");
    private DatabaseReference mDatabase_c = FirebaseDatabase.getInstance().getReference("comments");
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://myparrot-bfc92.appspot.com");
    private StorageReference storageRef = storage.getReference();
    private StorageReference desertRef;
    private String comments;
    private String uid;
    private String name;
    private String writing_uid;
    private String comment_uid;
    private Comment upload_comment;
    private Comment delete_comment;

    // 현재 시간을 "yyyy-MM-dd hh:mm:ss"로 표시하는 메소드
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    private void deleteWriting(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // 사용자 uid 받아오기

        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(uid)) { // 로그인한 uid와 같은 uid를 DB에서 찾으면
                        name = snapshot.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                        if(name.equals(writename)) { // 로그인한 닉네임과 글 작성자의 닉네임이 같을 때
                            mDatabase_w.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot _snapshot) {
                                    for (DataSnapshot snapshots : _snapshot.getChildren()) {
                                        if (snapshots.child("/content").getValue().equals(content)) {
                                            writing_uid = snapshots.getKey();
                                            break;
                                        }
                                    }
                                    mDatabase_w.child(writing_uid).removeValue();
                                    Toast.makeText(WritingViewActivity.this, "글이 정상적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            // Storage에서 이미지 삭제
                            if(img1 != null){
                                desertRef = storageRef.child("images/" + img1);
                                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                            if(img2 != null){
                                desertRef = storageRef.child("images/" + img2);
                                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                            if(img3 != null){
                                desertRef = storageRef.child("images/" + img3);
                                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                            if(img4 != null){
                                desertRef = storageRef.child("images/" + img4);
                                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                            if(img5 != null){
                                desertRef = storageRef.child("images/" + img5);
                                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                        } else{
                            Toast.makeText(WritingViewActivity.this, "작성자만 글을 삭제할 수 있습니다!", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void deleteComment(String delete_comments){
        mDatabase_c.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _snapshot) {
                for (DataSnapshot snapshots : _snapshot.getChildren()) {
                    if (snapshots.child("/comments").getValue().equals(delete_comments)) { // 삭제할 댓글의 내용과 같은 댓글의 내용이 발견되면
                        comment_uid = snapshots.getKey();                          // 댓글 uid 가져오기
                        break;
                    }
                }
                mDatabase_c.child(comment_uid).removeValue();                      // DB에서 댓글 삭제
                Toast.makeText(WritingViewActivity.this, "댓글이 정상적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_view);

        TextView load_title = (TextView)findViewById(R.id.textView7);
        TextView load_content = (TextView)findViewById(R.id.textView12);
        TextView load_nickname = (TextView)findViewById(R.id.textView8);
        TextView load_time = (TextView)findViewById(R.id.textView9);
        TextView load_views = (TextView)findViewById(R.id.textView10);
        ImageView load_img1 = (ImageView)findViewById(R.id.imageView6);
        ImageView load_img2 = (ImageView)findViewById(R.id.imageView7);
        ImageView load_img3 = (ImageView)findViewById(R.id.imageView8);
        ImageView load_img4 = (ImageView)findViewById(R.id.imageView9);
        ImageView load_img5 = (ImageView)findViewById(R.id.imageView10);

        writingDelete_btn = (Button)findViewById(R.id.writeDeleteBtn);
        writingRevise_btn = (Button)findViewById(R.id.writeReviseBtn);
        comment_btn = (Button)findViewById(R.id.comment_btn);
        commentDelete_btn = (Button)findViewById(R.id.commentDeleteBtn);

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        content = getIntent.getStringExtra("content");
        writename = getIntent.getStringExtra("name");
        time = getIntent.getStringExtra("time");
        views = getIntent.getIntExtra("views", 1) + 1;
        tab = getIntent.getStringExtra("tabs");

        load_title.setText(title);
        load_content.setText(content);
        load_nickname.setText("작성자 " + writename);
        load_time.setText("작성날짜 "+time);
        load_views.setText("조회수 " + String.valueOf(views));

        // 조회수 업데이트
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    // 내용과 시간이 같은 글을 찾으면
                    if (snapshots.child("/content").getValue().equals(content) && snapshots.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots.getKey(); // 글의 uid 받아오기
                        mDatabase_w.child("/"+writing_uid+"/views").setValue(views); // 조회수 업데이트
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        if(getIntent.getStringExtra("img1") != null){
            img1 = getIntent.getStringExtra("img1");
            storageRef.child("images/" + img1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(load_img1);
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
                            .into(load_img2);
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
                            .into(load_img3);
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
                            .into(load_img4);
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
                            .into(load_img5);
                }
            });
        }

        recycler = findViewById(R.id.recyclerComment);
        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        ArrayList<Comment> arrayList = new ArrayList<>();

        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_w) {
                for (DataSnapshot snapshot_w : dataSnapshot_w.getChildren()) {
                    if (snapshot_w.child("/content").getValue().equals(load_content.getText().toString())) {
                        writing_uid = snapshot_w.getKey();
                        mDatabase_c = FirebaseDatabase.getInstance().getReference("comments");
                        mDatabase_c.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot_c) {
                                arrayList.clear();
                                for (DataSnapshot snapshots_c : snapshot_c.getChildren()) {
                                    if(snapshots_c.child("/writing_uid").getValue().equals(writing_uid)){
                                        Comment comment = snapshots_c.getValue(Comment.class);
                                        arrayList.add(comment);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        adapter = new CommentAdapter(arrayList, this);
        recycler.setAdapter(adapter);

        // 댓글 리사이클러뷰 내에서 삭제 버튼 클릭 시
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid(); // 사용자 uid 받아오기

                delete_comment = arrayList.get(position);   //삭제할 댓글의 정보를 Comment 클래스로 가져오기

                mDatabase_u.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.getKey().equals(uid)) { // 로그인한 uid를 DB에서 찾으면
                                name = snapshot.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                                if(name.equals(delete_comment.getNickname())) { // 로그인한 닉네임과 댓글 작성자의 닉네임이 같을 때
                                    arrayList.remove(position);                 // 리스트에서 삭제
                                    adapter.notifyItemRemoved(position);        // 리사이클러뷰에서 삭제
                                    deleteComment(delete_comment.getComments());// DB에서 삭제
                                    adapter.notifyDataSetChanged();             // 리사이클러뷰 변경 알림
                                } else{
                                    Toast.makeText(WritingViewActivity.this, "작성자만 댓글을 삭제할 수 있습니다!", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        // 댓글 저장 버튼 클릭 시
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText comment = (EditText)findViewById(R.id.comment);
                comments = comment.getText().toString();
                // 입력받는 방법을 관리하는 Manager객체를 요청하여 InputMethodManager에 반환
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid(); // 댓글 작성자의 uid 받아오기

                mDatabase_u = FirebaseDatabase.getInstance().getReference("users");
                mDatabase_u.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.getKey().equals(uid)) {
                                name = snapshot.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                                break;
                            }
                        }
                        mDatabase_w = FirebaseDatabase.getInstance().getReference("writing");
                        mDatabase_w.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot _snapshot) {
                                for (DataSnapshot snapshots : _snapshot.getChildren()) {
                                    if(snapshots.child("/content").getValue().equals(load_content.getText().toString())) {
                                        writing_uid = snapshots.getKey();
                                        break;
                                    }
                                }
                                upload_comment = new Comment(writing_uid, getTime(), name, comments);
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("comments").push().setValue(upload_comment); // DB에 글 저장
                                Toast.makeText(WritingViewActivity.this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                adapter.notifyItemChanged(arrayList.size()); // 리사이클러뷰 변경 알림
                                comment.setText("");                         // 입력한 댓글 초기화
                                imm.hideSoftInputFromWindow(comment.getWindowToken(),0); // 키보드 내림
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });

        // 글 삭제 버튼 클릭 시
        writingDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWriting();
            }
        });
    }
}