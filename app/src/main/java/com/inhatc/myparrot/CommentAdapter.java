package com.inhatc.myparrot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Comment> arrayList;
    private Context context;

    public CommentAdapter(ArrayList<Comment> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getNickname());
        holder.tv_time.setText(arrayList.get(position).getTime());
        holder.tv_comment.setText(arrayList.get(position).getComments());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_time;
        TextView tv_comment;

        public CommentViewHolder(@NonNull View itemView){
            super(itemView);

            this.tv_name = itemView.findViewById(R.id.textView13);
            this.tv_time = itemView.findViewById(R.id.textView14);
            this.tv_comment = itemView.findViewById(R.id.textView15);
        }
    }
}
