package com.inhatc.myparrot;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity implements RecyclerAdapter.Listener{

    private String uid;
    private DatabaseReference mDatabase_u;
    private String email;
    private String nickname;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Writing> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info);

        TextView tv_email = (TextView)findViewById(R.id.tv_email);
        TextView tv_nickname = (TextView)findViewById(R.id.tv_nickname);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // 댓글 작성자의 uid 받아오기

        mDatabase_u = FirebaseDatabase.getInstance().getReference("users");
        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    if (snapshots.getKey().equals(uid)) {
                        nickname = snapshots.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                        email = snapshots.child("Info/email").getValue(String.class); // 이메일 가져오기
                        tv_email.setText(email);
                        tv_nickname.setText(nickname);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


        recyclerView = findViewById(R.id.recycler_my);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        adapter = new RecyclerAdapter(arrayList, this);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("writing");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Writing writing = dataSnapshot.getValue(Writing.class);
                    if(writing.getNickname().equals(tv_nickname.getText())){
                        arrayList.add(writing);
                    }
                }
                adapter.notifyDataSetChanged();
                updateListItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.my_info);

        TextView tv_email = (TextView)findViewById(R.id.tv_email);
        TextView tv_nickname = (TextView)findViewById(R.id.tv_nickname);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid(); // 댓글 작성자의 uid 받아오기

        mDatabase_u = FirebaseDatabase.getInstance().getReference("users");
        mDatabase_u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    if (snapshots.getKey().equals(uid)) {
                        nickname = snapshots.child("Info/nickname").getValue(String.class); // 닉네임 가져오기
                        email = snapshots.child("Info/email").getValue(String.class); // 이메일 가져오기
                        tv_email.setText(email);
                        tv_nickname.setText(nickname);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


        recyclerView = findViewById(R.id.recycler_my);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        adapter = new RecyclerAdapter(arrayList, this);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("writing");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Writing writing = dataSnapshot.getValue(Writing.class);
                    if(writing.getNickname().equals(tv_nickname.getText())){
                        arrayList.add(writing);
                    }
                }
                adapter.notifyDataSetChanged();
                updateListItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void updateListItems() {
        adapter.submitList(arrayList);
    }

    @Override
    public void onItemClickedAt(Integer position) {
        Writing selectedItem = arrayList.get(position);

        Intent intent =  new Intent(this, WritingViewActivity.class);   //화면 넘겨주기
        intent.putExtra("title", selectedItem.getTitle());
        intent.putExtra("content", selectedItem.getContent());
        intent.putExtra("time", selectedItem.getTime());
        intent.putExtra("name", selectedItem.getNickname());
        intent.putExtra("views", selectedItem.getViews());
        intent.putExtra("tabs", selectedItem.getTab());

        if(selectedItem.getImage1() != null){
            intent.putExtra("img1", selectedItem.getImage1());
        }
        if(selectedItem.getImage2() != null){
            intent.putExtra("img2", selectedItem.getImage2());
        }
        if(selectedItem.getImage3() != null){
            intent.putExtra("img3", selectedItem.getImage3());
        }
        if(selectedItem.getImage4() != null){
            intent.putExtra("img4", selectedItem.getImage4());
        }
        if(selectedItem.getImage5() != null){
            intent.putExtra("img5", selectedItem.getImage5());
        }

        this.startActivity(intent);
    }
}
