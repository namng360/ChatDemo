package vn.mac.gnam.chatdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import vn.mac.gnam.chatdemo.adapter.ChatAdapter;
import vn.mac.gnam.chatdemo.model.Chat;

public class MainActivity extends AppCompatActivity {
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("https://socket-io-chat.now.sh");
        } catch (URISyntaxException e) {
        }
    }

    private RecyclerView lvList;

    private List<Chat> chatList;

    private LinearLayoutManager linearLayoutManager;

    private ChatAdapter chatAdapter;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvList = findViewById(R.id.lvList);
        chatList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        chatAdapter = new ChatAdapter(chatList, this);
        lvList.setAdapter(chatAdapter);
        lvList.setLayoutManager(linearLayoutManager);

        editText = findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                Log.e("EVENT", keyEvent.getAction() + "");
                if (keyEvent.getAction() == 0) {
                    String message = editText.getText().toString();

                    Chat chat = new Chat();
                    chat.name = "Nguyễn Giang Nam";
                    chat.message = message;
                    chatList.add(chat);
                    chatAdapter.notifyDataSetChanged();

                    mSocket.emit("new message", message);
                    editText.setText("");

                    lvList.smoothScrollToPosition(chatList.size() - 1);
                }

                return false;
            }
        });

        mSocket.emit("add user", "Nguyễn Giang Nam");

        mSocket.on("new message", onNewMessage);
        mSocket.on("login", onLogin);
        mSocket.connect();
    }
    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    int numUsers;
                    try {
                        numUsers = data.getInt("numUsers");
                        Toast.makeText(MainActivity.this, "Có " + numUsers + " trong phòng chat",
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        return;
                    }

                }
            });

        }
    };
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");

                        Chat chat = new Chat();
                        chat.name = username;
                        chat.message = message;
                        chatList.add(chat);
                        chatAdapter.notifyDataSetChanged();
                        lvList.smoothScrollToPosition(chatList.size() - 1);

                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    //addMessage(username, message);
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }



}
