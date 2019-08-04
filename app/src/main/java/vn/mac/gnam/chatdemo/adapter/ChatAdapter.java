package vn.mac.gnam.chatdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.mac.gnam.chatdemo.R;
import vn.mac.gnam.chatdemo.holder.MeHolder;
import vn.mac.gnam.chatdemo.holder.OrtherHolder;
import vn.mac.gnam.chatdemo.model.Chat;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Chat> chatList;
    private Context context;
    int ME = 1;
    int ORTHER = 2;

    public ChatAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ME) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.my_chat, parent, false);
            return new MeHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.orther_chat, parent, false);
            return new OrtherHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if (holder instanceof MeHolder) {
            ((MeHolder) holder).tvName.setText(chat.name);
            ((MeHolder) holder).tvChat.setText(chat.message);
        } else if (holder instanceof OrtherHolder) {
            ((OrtherHolder) holder).tvName.setText(chat.name);
            ((OrtherHolder) holder).tvChat.setText(chat.message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        if (chat.name.equals("Nguyễn Giang Nam")){
            return ME;
        }else {
            return ORTHER;
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
