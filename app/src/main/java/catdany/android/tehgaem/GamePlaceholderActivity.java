package catdany.android.tehgaem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import catdany.android.tehgaem.net.TGClient;

public class GamePlaceholderActivity extends AppCompatActivity implements View.OnClickListener {

    public static GamePlaceholderActivity instance;

    public GameView viewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_placeholder);
        this.viewGame = (GameView)findViewById(R.id.viewGame);
        instance = this;

        ((Button)findViewById(R.id.buttonDisconnect)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonDisconnect) {
            try {
                TGClient.instance.close();
            } catch (IOException t) {
                LLog.e(t, "Couldn't close client socket.");
                AUtils.toastShort("I/O Error");
            }
        }
    }

    public static void finishOnUI() {
        if (instance != null) {
            instance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    instance.finish();
                }
            });
        }
    }
}
