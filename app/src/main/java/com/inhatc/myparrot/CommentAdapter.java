package com.inhatc.myparrot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    interface Listener {
        void onItemClickedAt(Integer position);
    }

    private ArrayList<Comment> arrayList;
    private Context context;
    private OnItemClickListener mListener = null; //리스너 객체 참조 변수
    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface OnItemClickListener{
        void onDeleteClick(View v, int position);//삭제
    }

    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

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
        Button btn_delete;

        public CommentViewHolder(@NonNull View itemView){
            super(itemView);

            this.tv_name = itemView.findViewById(R.id.commentName);
            this.tv_time = itemView.findViewById(R.id.textView14);
            this.tv_comment = itemView.findViewById(R.id.commentContent);
            this.btn_delete = itemView.findViewById(R.id.commentDeleteBtn);

            btn_delete.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onDeleteClick(view,position);
                        }
                    }
                }
            });
        }
    }
}
