package vn.mac.gnam.chatdemo.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.mac.gnam.chatdemo.R;

public class OrtherHolder extends RecyclerView.ViewHolder{
    public final TextView tvName;
    public final TextView tvChat;
    public OrtherHolder(@NonNull View itemView) {
        super(itemView);
        tvChat = itemView.findViewById(R.id.tvChat);
        tvName = itemView.findViewById(R.id.tvName);
    }
}
