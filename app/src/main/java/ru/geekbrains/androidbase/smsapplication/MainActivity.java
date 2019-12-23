package ru.geekbrains.androidbase.smsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 123;
    private RecyclerView messageList;
    private TextInputLayout phoneInputLayout;
    private TextInputLayout messageInputLayout;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        createWeekWeatherList();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }

    private void sendSms(){
        String phone = phoneInputLayout.getEditText().getText().toString();
        String message = messageInputLayout.getEditText().getText().toString();

        if(phone.isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(message.isEmpty()){
            Toast.makeText(getApplicationContext(), "Message is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
                }
            }
        }
        SmsManager.getDefault().sendTextMessage(phone, null, message, null, null);
        MessageListAdapter.getInstace().addMessage(new Message(phone, message));
        messageInputLayout.getEditText().setText("");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            sendSms();
    }

    private void initViews(){
        messageList = findViewById(R.id.messageList);
        phoneInputLayout = findViewById(R.id.phoneInputLayout);
        messageInputLayout = findViewById(R.id.messageInputLayout);
        sendButton = findViewById(R.id.sendButton);
    }

    private void createWeekWeatherList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageList.setLayoutManager(linearLayoutManager);

        messageList.setAdapter(MessageListAdapter.getInstace());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getDrawable(R.drawable.separator));
        messageList.addItemDecoration(itemDecoration);
    }
}
