package com.inhatc.myparrot;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    interface Listener {
        void onItemClickedAt(Integer position);
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void submitList(ArrayList<Writing> items) {
        this.arrayList = items;
        notifyDataSetChanged();
    }

    // adapter에 들어갈 list 입니다.
    private ArrayList<Writing> arrayList;
    private Context context;

    public RecyclerAdapter (ArrayList<Writing> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view, this.listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.textView1.setText(arrayList.get(position).getTitle());
        holder.textView3.setText(arrayList.get(position).getTime());
        holder.textView4.setText("조회수 " + String.valueOf(arrayList.get(position).getViews()));
        holder.textView5.setText("작성자 " + arrayList.get(position).getNickname());
        if(arrayList.get(position).getImage1() != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://myparrot-bfc92.appspot.com");
            StorageReference storageRef = storage.getReference();
            storageRef.child("images/" + arrayList.get(position).getImage1()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시
                    Glide.with(context.getApplicationContext())
                            .load(uri)
                            .into(holder.imageView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView, RecyclerAdapter.Listener itemListListener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer currentPos = getAbsoluteAdapterPosition();//각 어댑터의 위치 클릭된
                    itemListListener.onItemClickedAt(currentPos);
                }
            });

        }
    }
}
