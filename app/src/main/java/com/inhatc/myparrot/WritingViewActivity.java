package com.inhatc.myparrot;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.HashMap;

public class WritingViewActivity extends AppCompatActivity implements CommentAdapter.Listener{

    private String title;
    private String content;
    private String writename;
    private String time;
    private String tab;
    private int views;
    private int suggestion;
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
    private Button suggestion_btn;
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
    private ArrayList<Comment> commentList;

    // 현재 시간을 "yyyy-MM-dd hh:mm:ss"로 표시하는 메소드
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    // 글 삭제 메소드
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

    // 글 수정 메소드
    private void reviseWriting(){
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_w) {
                for (DataSnapshot snapshot_w : dataSnapshot_w.getChildren()) {
                    if (snapshot_w.child("/title").getValue().equals(title) && snapshot_w.child("/content").getValue().equals(content)) {
                        writing_uid = snapshot_w.getKey();

                        Intent reviseIntent =  new Intent(WritingViewActivity.this, WritingReviseActivity.class);   //화면 넘겨주기
                        reviseIntent.putExtra("title", title);
                        reviseIntent.putExtra("content", content);
                        reviseIntent.putExtra("tab", tab);
                        reviseIntent.putExtra("writingUid", writing_uid);

                        if(img1 != null){
                            reviseIntent.putExtra("img1", img1);
                        }
                        if(img2 != null){
                            reviseIntent.putExtra("img2", img2);
                        }
                        if(img3 != null){
                            reviseIntent.putExtra("img3", img3);
                        }
                        if(img4 != null){
                            reviseIntent.putExtra("img4", img4);
                        }
                        if(img5 != null){
                            reviseIntent.putExtra("img5", img5);
                        }
                        startActivity(reviseIntent);

                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    //댓글 작성 메소드
    private void writeComment(){
        TextView load_content = (TextView)findViewById(R.id.textView12);
        EditText comment = (EditText)findViewById(R.id.comment);
        comments = comment.getText().toString(); // 입력한 댓글 받아오기
        // 입력받는 방법을 관리하는 Manager객체를 요청하여 InputMethodManager에 반환
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // 댓글 작성자의 uid 받아오기

        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(uid)) { // 댓글 작성자의 uid 와 같은 uid 가 있다면
                        name = snapshot.child("Info/nickname").getValue(String.class); // 댓글 작성자의 닉네임 가져오기
                        break;
                    }
                }
                // 글 uid 받아오기
                mDatabase_w.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot_cs) {
                        for (DataSnapshot snapshots_cs : snapshot_cs.getChildren()) {
                            // 파이어베이스에 현재 글의 내용과 같은 내용의 글이 있다면
                            if(snapshots_cs.child("/content").getValue().equals(load_content.getText().toString())) {
                                writing_uid = snapshots_cs.getKey(); // 글의 uid 받아오기
                                break;
                            }
                        }
                        upload_comment = new Comment(writing_uid, getTime(), name, comments); // Comment 객체 생성
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("comments").push().setValue(upload_comment); // DB에 댓글 저장
                        adapter.notifyDataSetChanged();      // 리사이클러뷰 변경 알림
                        comment.setText("");                 // 입력한 댓글 초기화
                        imm.hideSoftInputFromWindow(comment.getWindowToken(),0); // 키보드 자동 내림
                        Toast.makeText(WritingViewActivity.this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    // 댓글 삭제 메소드
    private void deleteComment(String delete_comments){
        mDatabase_c.addListenerForSingleValueEvent(new ValueEventListener() {
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

    // 추천 수 증가 메소드
    private void plusSuggestion(){
        TextView load_suggestion = (TextView)findViewById(R.id.textViewSuggestion);
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot_s) {
                for (DataSnapshot snapshots_s : snapshot_s.getChildren()) {
                    // 내용과 시간이 같은 글을 찾으면
                    if (snapshots_s.child("/content").getValue().equals(content) && snapshots_s.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots_s.getKey(); // 글의 uid 받아오기
                        mDatabase_w.child("/"+writing_uid+"/suggestion").setValue((suggestion + 1)); // 추천수 + 1
                        load_suggestion.setText("추천 수 : "+(suggestion+1));
                        suggestion_btn.setTextColor(Color.GRAY);
                        break;
                    }
                }
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
        TextView load_suggestion = (TextView)findViewById(R.id.textViewSuggestion);
        ImageView load_img1 = (ImageView)findViewById(R.id.imageView6);
        ImageView load_img2 = (ImageView)findViewById(R.id.imageView7);
        ImageView load_img3 = (ImageView)findViewById(R.id.imageView8);
        ImageView load_img4 = (ImageView)findViewById(R.id.imageView9);
        ImageView load_img5 = (ImageView)findViewById(R.id.imageView10);

        writingDelete_btn = findViewById(R.id.writeDeleteBtn);   // 글 삭제 버튼
        writingRevise_btn = findViewById(R.id.writeReviseBtn);   // 글 수정 버튼
        comment_btn = findViewById(R.id.comment_btn);            // 댓글 작성 버튼
        commentDelete_btn = findViewById(R.id.commentDeleteBtn); // 댓글 삭제 버튼
        suggestion_btn = findViewById(R.id.suggestionBtn);       // 추천 버튼

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        content = getIntent.getStringExtra("content");
        writename = getIntent.getStringExtra("name");
        time = getIntent.getStringExtra("time");
        views = getIntent.getIntExtra("views", 1)+1;
        tab = getIntent.getStringExtra("tabs");
        suggestion = getIntent.getIntExtra("suggestion", 0);

        load_title.setText(title);
        load_content.setText(content);
        load_nickname.setText("작성자 " + writename);
        load_time.setText("작성날짜 "+time);
        load_views.setText("조회수 " + String.valueOf(views));
        load_suggestion.setText("추천 수 : "+String.valueOf(suggestion));

        // 조회수 업데이트
        /*mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot_vu) {
                for (DataSnapshot snapshots_vu : snapshot_vu.getChildren()) {
                    // 내용과 시간이 같은 글을 찾으면
                    if (snapshots_vu.child("/content").getValue().equals(content) && snapshots_vu.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots_vu.getKey(); // 글의 uid 받아오기
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("views", views);

                        mDatabase_w.child(writing_uid).updateChildren(hashMap);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/

        // 이미지 불러오기
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
        commentList = new ArrayList<>();

        // 글에 해당되는 댓글 가져오기
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_w) {
                for (DataSnapshot snapshot_w : dataSnapshot_w.getChildren()) {
                    // 받아온 글의 내용과 같은 내용의 글이 있다면
                    if (snapshot_w.child("/content").getValue().equals(content)) {
                        writing_uid = snapshot_w.getKey(); // 글의 uid 받기
                        break;
                    }
                }
                mDatabase_c.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot_cr) {
                        commentList.clear();
                        for (DataSnapshot snapshots_cr : snapshot_cr.getChildren()) {
                            Comment comment = snapshots_cr.getValue(Comment.class); // Comment 객체로 댓글 정보 받기
                            // 글의 uid를 가진 댓글이 있다면
                            if(comment.getWriting_uid().equals(writing_uid)){
                                commentList.add(comment); // 리스트에 저장
                            }
                        }
                        adapter.notifyDataSetChanged(); // 리사이클러뷰에 데이터 변경 알림

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        adapter = new CommentAdapter(commentList, this);
        adapter.setListener(this);
        recycler.setAdapter(adapter);

        // 댓글 리사이클러뷰 내에서 삭제 버튼 클릭 시
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid(); // 사용자 uid 받아오기

                delete_comment = commentList.get(position);   //삭제할 댓글의 위치 가져오기

                mDatabase_u.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot_cd) {
                        for (DataSnapshot snapshot_cd : dataSnapshot_cd.getChildren()) {
                            if (snapshot_cd.getKey().equals(uid)) { // 로그인한 uid를 DB에서 찾으면
                                name = snapshot_cd.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                                if(name.equals(delete_comment.getNickname())) { // 로그인한 닉네임과 댓글 작성자의 닉네임이 같을 때
                                    commentList.remove(position);                 // 리스트에서 삭제
                                    adapter.notifyItemRemoved(position);        // 리사이클러뷰에서 삭제
                                    deleteComment(delete_comment.getComments());// DB에서 삭제
                                } else{
                                    Toast.makeText(WritingViewActivity.this, "작성자만 댓글을 삭제할 수 있습니다!", Toast.LENGTH_SHORT).show();
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
                writeComment();
                finish();
            }

        });

        // 글 삭제 버튼 클릭 시
        writingDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWriting();
            }
        });

        //글 수정 버튼 클릭 시
        writingRevise_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reviseWriting();
            }
        });

        //추천 버튼 클릭 시
        suggestion_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                plusSuggestion();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot_vu) {
                for (DataSnapshot snapshots_vu : snapshot_vu.getChildren()) {
                    // 내용과 시간이 같은 글을 찾으면
                    if (snapshots_vu.child("/content").getValue().equals(content) && snapshots_vu.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots_vu.getKey(); // 글의 uid 받아오기
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("views", views);

                        mDatabase_w.child(writing_uid).updateChildren(hashMap);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        finish(); // 종료
    }

    @Override
    public void onItemClickedAt(Integer position) {

    }
}