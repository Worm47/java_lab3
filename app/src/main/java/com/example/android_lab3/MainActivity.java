package com.example.android_lab3;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_NAME_LENGTH = 6;
    private static final int MAX_MESSAGE_LENGTH = 20;
    private static final int RANDOM_MESSAGE_INTERVAL = 10000;

    private TextView chatView;
    private EditText inputName;
    private EditText inputMessage;
    private Button sendButton;
    private ScrollView scrollView;
    private Handler handler;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatView = findViewById(R.id.chatView);
        inputName = findViewById(R.id.inputName);
        inputMessage = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);
        scrollView = findViewById(R.id.scrollView);

        handler = new Handler();
        random = new Random();
        for (int i = 0; i < 10; i++) {
            addMessage(getRandomMessage());
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addMessage(getRandomMessage());
                handler.postDelayed(this, RANDOM_MESSAGE_INTERVAL);
            }
        }, RANDOM_MESSAGE_INTERVAL);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String message = inputMessage.getText().toString();

                if (!name.isEmpty() && !message.isEmpty()) {
                    addMessage(name + ": " + message);
                    inputMessage.setText("");
                }
            }
        });
    }

    private void addMessage(String message) {
        chatView.append(message + "\n");
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private String getRandomMessage() {
        String name = generateRandomName();
        String message = generateRandomMessage();
        return name + ": " + message;
    }

    private String generateRandomName() {
        int length = random.nextInt(MAX_NAME_LENGTH) + 1;
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            name.append((char) (random.nextInt(26) + 'a'));
        }
        return name.toString();
    }

    private String generateRandomMessage() {
        int length = random.nextInt(MAX_MESSAGE_LENGTH) + 1;
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < length; i++) {
            message.append((char) (random.nextInt(26) + 'a'));
        }
        return message.toString();
    }
}
