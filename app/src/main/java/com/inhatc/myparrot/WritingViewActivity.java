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

    // ?????? ????????? "yyyy-MM-dd hh:mm:ss"??? ???????????? ?????????
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    // ??? ?????? ?????????
    private void deleteWriting(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // ????????? uid ????????????

        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(uid)) { // ???????????? uid??? ?????? uid??? DB?????? ?????????
                        name = snapshot.child("Info/nickname").getValue(String.class); // ????????? ????????????
                        if(name.equals(writename)) { // ???????????? ???????????? ??? ???????????? ???????????? ?????? ???
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
                                    Toast.makeText(WritingViewActivity.this, "?????? ??????????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            // Storage?????? ????????? ??????
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
                            Toast.makeText(WritingViewActivity.this, "???????????? ?????? ????????? ??? ????????????!", Toast.LENGTH_LONG).show();
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

    // ??? ?????? ?????????
    private void reviseWriting(){
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_w) {
                for (DataSnapshot snapshot_w : dataSnapshot_w.getChildren()) {
                    if (snapshot_w.child("/title").getValue().equals(title) && snapshot_w.child("/content").getValue().equals(content)) {
                        writing_uid = snapshot_w.getKey();

                        Intent reviseIntent =  new Intent(WritingViewActivity.this, WritingReviseActivity.class);   //?????? ????????????
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

    //?????? ?????? ?????????
    private void writeComment(){
        TextView load_content = (TextView)findViewById(R.id.textView12);
        EditText comment = (EditText)findViewById(R.id.comment);
        comments = comment.getText().toString(); // ????????? ?????? ????????????
        // ???????????? ????????? ???????????? Manager????????? ???????????? InputMethodManager??? ??????
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // ?????? ???????????? uid ????????????

        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(uid)) { // ?????? ???????????? uid ??? ?????? uid ??? ?????????
                        name = snapshot.child("Info/nickname").getValue(String.class); // ?????? ???????????? ????????? ????????????
                        break;
                    }
                }
                // ??? uid ????????????
                mDatabase_w.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot_cs) {
                        for (DataSnapshot snapshots_cs : snapshot_cs.getChildren()) {
                            // ????????????????????? ?????? ?????? ????????? ?????? ????????? ?????? ?????????
                            if(snapshots_cs.child("/content").getValue().equals(load_content.getText().toString())) {
                                writing_uid = snapshots_cs.getKey(); // ?????? uid ????????????
                                break;
                            }
                        }
                        upload_comment = new Comment(writing_uid, getTime(), name, comments); // Comment ?????? ??????
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("comments").push().setValue(upload_comment); // DB??? ?????? ??????
                        adapter.notifyDataSetChanged();      // ?????????????????? ?????? ??????
                        comment.setText("");                 // ????????? ?????? ?????????
                        imm.hideSoftInputFromWindow(comment.getWindowToken(),0); // ????????? ?????? ??????
                        Toast.makeText(WritingViewActivity.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    // ?????? ?????? ?????????
    private void deleteComment(String delete_comments){
        mDatabase_c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _snapshot) {
                for (DataSnapshot snapshots : _snapshot.getChildren()) {
                    if (snapshots.child("/comments").getValue().equals(delete_comments)) { // ????????? ????????? ????????? ?????? ????????? ????????? ????????????
                        comment_uid = snapshots.getKey();                          // ?????? uid ????????????
                        break;
                    }
                }
                mDatabase_c.child(comment_uid).removeValue();                      // DB?????? ?????? ??????
                Toast.makeText(WritingViewActivity.this, "????????? ??????????????? ?????????????????????.", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // ?????? ??? ?????? ?????????
    private void plusSuggestion(){
        TextView load_suggestion = (TextView)findViewById(R.id.textViewSuggestion);
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot_s) {
                for (DataSnapshot snapshots_s : snapshot_s.getChildren()) {
                    // ????????? ????????? ?????? ?????? ?????????
                    if (snapshots_s.child("/content").getValue().equals(content) && snapshots_s.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots_s.getKey(); // ?????? uid ????????????
                        mDatabase_w.child("/"+writing_uid+"/suggestion").setValue((suggestion + 1)); // ????????? + 1
                        load_suggestion.setText("?????? ??? : "+(suggestion+1));
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

        writingDelete_btn = findViewById(R.id.writeDeleteBtn);   // ??? ?????? ??????
        writingRevise_btn = findViewById(R.id.writeReviseBtn);   // ??? ?????? ??????
        comment_btn = findViewById(R.id.comment_btn);            // ?????? ?????? ??????
        commentDelete_btn = findViewById(R.id.commentDeleteBtn); // ?????? ?????? ??????
        suggestion_btn = findViewById(R.id.suggestionBtn);       // ?????? ??????

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
        load_nickname.setText("????????? " + writename);
        load_time.setText("???????????? "+time);
        load_views.setText("????????? " + String.valueOf(views));
        load_suggestion.setText("?????? ??? : "+String.valueOf(suggestion));

        // ????????? ????????????
        if(getIntent.getStringExtra("img1") != null){
            img1 = getIntent.getStringExtra("img1");
            storageRef.child("images/" + img1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //????????? ?????? ?????????
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
                    //????????? ?????? ?????????
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
                    //????????? ?????? ?????????
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
                    //????????? ?????? ?????????
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
                    //????????? ?????? ?????????
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

        // ?????? ???????????? ?????? ????????????
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_w) {
                for (DataSnapshot snapshot_w : dataSnapshot_w.getChildren()) {
                    // ????????? ?????? ????????? ?????? ????????? ?????? ?????????
                    if (snapshot_w.child("/content").getValue().equals(content)) {
                        writing_uid = snapshot_w.getKey(); // ?????? uid ??????
                        break;
                    }
                }
                mDatabase_c.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot_cr) {
                        commentList.clear();
                        for (DataSnapshot snapshots_cr : snapshot_cr.getChildren()) {
                            Comment comment = snapshots_cr.getValue(Comment.class); // Comment ????????? ?????? ?????? ??????
                            // ?????? uid??? ?????? ????????? ?????????
                            if(comment.getWriting_uid().equals(writing_uid)){
                                commentList.add(comment); // ???????????? ??????
                            }
                        }
                        adapter.notifyDataSetChanged(); // ????????????????????? ????????? ?????? ??????

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

        // ?????? ?????????????????? ????????? ?????? ?????? ?????? ???
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid(); // ????????? uid ????????????

                delete_comment = commentList.get(position);   //????????? ????????? ?????? ????????????

                mDatabase_u.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot_cd) {
                        for (DataSnapshot snapshot_cd : dataSnapshot_cd.getChildren()) {
                            if (snapshot_cd.getKey().equals(uid)) { // ???????????? uid??? DB?????? ?????????
                                name = snapshot_cd.child("Info/nickname").getValue(String.class); // ????????? ????????????
                                if(name.equals(delete_comment.getNickname())) { // ???????????? ???????????? ?????? ???????????? ???????????? ?????? ???
                                    commentList.remove(position);                 // ??????????????? ??????
                                    adapter.notifyItemRemoved(position);        // ???????????????????????? ??????
                                    deleteComment(delete_comment.getComments());// DB?????? ??????
                                } else{
                                    Toast.makeText(WritingViewActivity.this, "???????????? ????????? ????????? ??? ????????????!", Toast.LENGTH_SHORT).show();
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

        // ?????? ?????? ?????? ?????? ???
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeComment();
                finish();
            }

        });

        // ??? ?????? ?????? ?????? ???
        writingDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWriting();
            }
        });

        //??? ?????? ?????? ?????? ???
        writingRevise_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reviseWriting();
            }
        });

        //?????? ?????? ?????? ???
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
        // ????????? ????????????
        mDatabase_w.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot_vu) {
                for (DataSnapshot snapshots_vu : snapshot_vu.getChildren()) {
                    // ????????? ????????? ?????? ?????? ?????????
                    if (snapshots_vu.child("/content").getValue().equals(content) && snapshots_vu.child("/time").getValue().equals(time)) {
                        writing_uid = snapshots_vu.getKey(); // ?????? uid ????????????
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
        finish(); // ??????
    }

    @Override
    public void onItemClickedAt(Integer position) {

    }
}