package catdany.android.tehgaem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import catdany.android.tehgaem.net.TGClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static LoginActivity activity;

    public static final String PREF_FILE = "tehgaem";
    public static final String PREF_LAST_SERVER = "server";
    public static final String PREF_ID = "id";
    public static final String PREF_PASSWORD = "password";

    public SharedPreferences pref;
    public UUID userID;

    public EditText textServer;
    public EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        pref = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        LLog.i("Loaded preferences.");

        textServer = (EditText)findViewById(R.id.editTextServer);
        textPassword = (EditText)findViewById(R.id.editTextPassword);
        textServer.setText(pref.getString(PREF_LAST_SERVER, ""));
        textPassword.setText(pref.getString(PREF_PASSWORD, ""));
        this.userID = UUID.randomUUID();
        if (pref.contains(PREF_ID)) {
            userID = UUID.fromString(pref.getString(PREF_ID, userID.toString()));
            LLog.i("UUID restored from preferences.");
        } else {
            pref.edit().putString(PREF_ID, userID.toString()).apply();
            LLog.i("UUID randomly generated.");
        }
        LLog.i("UUID: %s", userID);

        Button buttonConnect = (Button)findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(this);
    }

    public void connect() {
        pref.edit()
                .putString(PREF_LAST_SERVER, textServer.getText().toString())
                .putString(PREF_PASSWORD, textPassword.getText().toString())
                .apply();
        LLog.i("Saved sever and password.");
        TGClient.instance = new TGClient();
        TGClient.instance.connect(textServer.getText().toString(), userID, textPassword.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonConnect) {
            connect();
        }
    }

    public void transitionToGame() {
        Intent intent = new Intent(this, GamePlaceholderActivity.class);
        startActivity(intent);
    }
}
