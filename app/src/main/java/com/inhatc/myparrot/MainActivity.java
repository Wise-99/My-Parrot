package com.inhatc.myparrot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.Listener {
    TabHost myTabHost = null;
    TabHost.TabSpec myTabSpec;

    private DatabaseReference mDatabase_w = FirebaseDatabase.getInstance().getReference("writing");
    private String writing_uid;
    private FirebaseAuth mAuth;
    private RecyclerAdapter adapter;
    private RecyclerAdapter adapter_review;
    private RecyclerAdapter adapter_QA;
    private RecyclerAdapter adapter_notice;
    private RecyclerAdapter adapter_boast;
    private RecyclerAdapter adapter_parcelOut;
    private DatabaseReference mDatabase;
    private RecyclerView recycler_rank;
    private RecyclerView recycler_notice;
    private RecyclerView recycler_review;
    private RecyclerView recycler_boast;
    private RecyclerView recycler_QA;
    private RecyclerView recycler_parcel_out;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;
    private RecyclerView.LayoutManager layoutManager4;
    private RecyclerView.LayoutManager layoutManager5;
    private RecyclerView.LayoutManager layoutManager6;
    private ArrayList<Writing> arrayList;
    private ArrayList<Writing> noticeList;
    private ArrayList<Writing> reviewList;
    private ArrayList<Writing> boastList;
    private ArrayList<Writing> QAList;
    private ArrayList<Writing> parcelOutList;
    private FirebaseDatabase database;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_rank = findViewById(R.id.recyclerView1);
        recycler_rank.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recycler_rank.setLayoutManager(layoutManager1);

        recycler_notice = findViewById(R.id.recycler_notice);
        recycler_notice.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recycler_notice.setLayoutManager(layoutManager2);

        recycler_boast = findViewById(R.id.recycler_boast);
        recycler_boast.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(this);
        recycler_boast.setLayoutManager(layoutManager3);

        recycler_review = findViewById(R.id.recycler_review);
        recycler_review.setHasFixedSize(true);
        layoutManager4 = new LinearLayoutManager(this);
        recycler_review.setLayoutManager(layoutManager4);

        recycler_QA = findViewById(R.id.recycler_QA);
        recycler_QA.setHasFixedSize(true);
        layoutManager5 = new LinearLayoutManager(this);
        recycler_QA.setLayoutManager(layoutManager5);

        recycler_parcel_out = findViewById(R.id.recycler_parcel_out);
        recycler_parcel_out.setHasFixedSize(true);
        layoutManager6 = new LinearLayoutManager(this);
        recycler_parcel_out.setLayoutManager(layoutManager6);

        arrayList = new ArrayList<>();
        noticeList = new ArrayList<>();
        reviewList = new ArrayList<>();
        boastList = new ArrayList<>();
        QAList = new ArrayList<>();
        parcelOutList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("writing");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                noticeList.clear();
                reviewList.clear();
                boastList.clear();
                QAList.clear();
                parcelOutList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Writing writing = dataSnapshot.getValue(Writing.class);

                    arrayList.add(writing);

                    // 주제별로 리스트에 넣기
                    if (writing.getTab().equals("공지")) {
                        noticeList.add(0, writing);
                    } else if (writing.getTab().equals("후기")) {
                        reviewList.add(0, writing);
                    } else if (writing.getTab().equals("자랑")) {
                        boastList.add(0, writing);
                    } else if (writing.getTab().equals("질문")) {
                        QAList.add(0, writing);
                    } else if (writing.getTab().equals("분양")) {
                        parcelOutList.add(0, writing);
                    }
                }

                // 추천 수로 정렬하기
                Collections.sort(arrayList);
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();
                adapter_boast.notifyDataSetChanged();
                adapter_notice.notifyDataSetChanged();
                adapter_QA.notifyDataSetChanged();
                adapter_review.notifyDataSetChanged();
                adapter_parcelOut.notifyDataSetChanged();
                updateListItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new RecyclerAdapter(arrayList, this);
        adapter.setListener(this);
        recycler_rank.setAdapter(adapter);

        adapter_review = new RecyclerAdapter(reviewList, this);
        adapter_review.setListener(this);
        recycler_review.setAdapter(adapter_review);

        adapter_QA = new RecyclerAdapter(QAList, this);
        adapter_QA.setListener(this);
        recycler_QA.setAdapter(adapter_QA);

        adapter_notice = new RecyclerAdapter(noticeList, this);
        adapter_notice.setListener(this);
        recycler_notice.setAdapter(adapter_notice);

        adapter_boast = new RecyclerAdapter(boastList, this);
        adapter_boast.setListener(this);
        recycler_boast.setAdapter(adapter_boast);

        adapter_parcelOut = new RecyclerAdapter(parcelOutList, this);
        adapter_parcelOut.setListener(this);
        recycler_parcel_out.setAdapter(adapter_parcelOut);


        //Toolbar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myTabHost = (TabHost) findViewById(R.id.tabhost);
        myTabHost.setup();

        myTabSpec = myTabHost.newTabSpec("rank").setIndicator("랭킹").setContent(R.id.tab1);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("notice").setIndicator("공지").setContent(R.id.tab2);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("review").setIndicator("후기").setContent(R.id.tab3);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("boast").setIndicator("자랑").setContent(R.id.tab4);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("Q&A").setIndicator("질문").setContent(R.id.tab5);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("parcel_out").setIndicator("분양").setContent(R.id.tab6);
        myTabHost.addTab(myTabSpec);

        myTabHost.setCurrentTab(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        recycler_rank = findViewById(R.id.recyclerView1);
        recycler_rank.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recycler_rank.setLayoutManager(layoutManager1);

        recycler_notice = findViewById(R.id.recycler_notice);
        recycler_notice.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recycler_notice.setLayoutManager(layoutManager2);

        recycler_boast = findViewById(R.id.recycler_boast);
        recycler_boast.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(this);
        recycler_boast.setLayoutManager(layoutManager3);

        recycler_review = findViewById(R.id.recycler_review);
        recycler_review.setHasFixedSize(true);
        layoutManager4 = new LinearLayoutManager(this);
        recycler_review.setLayoutManager(layoutManager4);

        recycler_QA = findViewById(R.id.recycler_QA);
        recycler_QA.setHasFixedSize(true);
        layoutManager5 = new LinearLayoutManager(this);
        recycler_QA.setLayoutManager(layoutManager5);

        recycler_parcel_out = findViewById(R.id.recycler_parcel_out);
        recycler_parcel_out.setHasFixedSize(true);
        layoutManager6 = new LinearLayoutManager(this);
        recycler_parcel_out.setLayoutManager(layoutManager6);

        arrayList = new ArrayList<>();
        noticeList = new ArrayList<>();
        reviewList = new ArrayList<>();
        boastList = new ArrayList<>();
        QAList = new ArrayList<>();
        parcelOutList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("writing");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                noticeList.clear();
                reviewList.clear();
                boastList.clear();
                QAList.clear();
                parcelOutList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Writing writing = dataSnapshot.getValue(Writing.class);

                    arrayList.add(writing);

                    // 주제별로 리스트에 넣기
                    if (writing.getTab().equals("공지")) {
                        noticeList.add(0, writing);
                    } else if (writing.getTab().equals("후기")) {
                        reviewList.add(0, writing);
                    } else if (writing.getTab().equals("자랑")) {
                        boastList.add(0, writing);
                    } else if (writing.getTab().equals("질문")) {
                        QAList.add(0, writing);
                    } else if (writing.getTab().equals("분양")) {
                        parcelOutList.add(0, writing);
                    }
                }

                // 추천 수로 정렬하기
                Collections.sort(arrayList);
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();
                adapter_boast.notifyDataSetChanged();
                adapter_notice.notifyDataSetChanged();
                adapter_QA.notifyDataSetChanged();
                adapter_review.notifyDataSetChanged();
                adapter_parcelOut.notifyDataSetChanged();
                updateListItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new RecyclerAdapter(arrayList, this);
        adapter.setListener(this);
        recycler_rank.setAdapter(adapter);

        adapter_review = new RecyclerAdapter(reviewList, this);
        adapter_review.setListener(this);
        recycler_review.setAdapter(adapter_review);

        adapter_QA = new RecyclerAdapter(QAList, this);
        adapter_QA.setListener(this);
        recycler_QA.setAdapter(adapter_QA);

        adapter_notice = new RecyclerAdapter(noticeList, this);
        adapter_notice.setListener(this);
        recycler_notice.setAdapter(adapter_notice);

        adapter_boast = new RecyclerAdapter(boastList, this);
        adapter_boast.setListener(this);
        recycler_boast.setAdapter(adapter_boast);

        adapter_parcelOut = new RecyclerAdapter(parcelOutList, this);
        adapter_parcelOut.setListener(this);
        recycler_parcel_out.setAdapter(adapter_parcelOut);


        //Toolbar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myTabHost = (TabHost) findViewById(R.id.tabhost);
        myTabHost.setup();

        myTabSpec = myTabHost.newTabSpec("rank").setIndicator("랭킹").setContent(R.id.tab1);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("notice").setIndicator("공지").setContent(R.id.tab2);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("review").setIndicator("후기").setContent(R.id.tab3);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("boast").setIndicator("자랑").setContent(R.id.tab4);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("Q&A").setIndicator("질문").setContent(R.id.tab5);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("parcel_out").setIndicator("분양").setContent(R.id.tab6);
        myTabHost.addTab(myTabSpec);

        myTabHost.setCurrentTab(0);
    }

    private void updateListItems() {
    }

    @Override
    public void onItemClickedAt(Integer position) {
        Writing selectedItem = null;
        int tabWidget = myTabHost.getCurrentTab();

        if (tabWidget == 0) {
            selectedItem = arrayList.get(position);
        } else if (tabWidget == 1) {
            selectedItem = noticeList.get(position);
        } else if (tabWidget == 2) {
            selectedItem = reviewList.get(position);
        } else if (tabWidget == 3) {
            selectedItem = boastList.get(position);
        } else if (tabWidget == 4) {
            selectedItem = QAList.get(position);
        } else if (tabWidget == 5) {
            selectedItem = parcelOutList.get(position);
        }

        Intent intent = new Intent(this, WritingViewActivity.class);   //화면 넘겨주기
        intent.putExtra("title", selectedItem.getTitle());
        intent.putExtra("content", selectedItem.getContent());
        intent.putExtra("time", selectedItem.getTime());
        intent.putExtra("name", selectedItem.getNickname());
        intent.putExtra("views", selectedItem.getViews());
        intent.putExtra("tabs", selectedItem.getTab());
        intent.putExtra("suggestion", selectedItem.getSuggestion());

        if (selectedItem.getImage1() != null) {
            intent.putExtra("img1", selectedItem.getImage1());
        }
        if (selectedItem.getImage2() != null) {
            intent.putExtra("img2", selectedItem.getImage2());
        }
        if (selectedItem.getImage3() != null) {
            intent.putExtra("img3", selectedItem.getImage3());
        }
        if (selectedItem.getImage4() != null) {
            intent.putExtra("img4", selectedItem.getImage4());
        }
        if (selectedItem.getImage5() != null) {
            intent.putExtra("img5", selectedItem.getImage5());
        }

        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.write:
                Intent intent2 = new Intent(this, WriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.info:
                Intent intent3 = new Intent(this, MyInfoActivity.class);
                startActivity(intent3);
                break;
            case R.id.map:
                Intent intent4 = new Intent(this, GoogleMapsActivity.class);
                startActivity(intent4);
        }
        return super.onOptionsItemSelected(item);
    }
}