package com.example.drew.messageonabottle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class UserLoginActivity extends ActionBarActivity {

    private EditText userNameEditText;
    private Button loginButton;
    private String _userName;
    private String _serverUri;

    Socket _socket;
    {
        try {
            _socket = IO.socket("https://thawing-island-7364.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Bundle extras = getIntent().getExtras();
        _serverUri = extras.getString("serverUri", "https://thawing-island-7364.herokuapp.com/");

        userNameEditText = (EditText)findViewById(R.id.userNameEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
    }

    public void doLogin(View view) {
        String userName = userNameEditText.getText().toString();

        if (userName == null || userName.isEmpty())
            userName = new String("DefaultUserMcGee");

        _socket.connect();
        _socket.emit("add user", userName);

        Intent messageBoardIntent = new Intent();
        messageBoardIntent.putExtra("userName", userName);
        messageBoardIntent.setClass(this, MessageBoardActivity.class);
        startActivity(messageBoardIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
