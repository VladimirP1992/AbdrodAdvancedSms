package ru.geekbrains.androidbase.smsapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SmsReceiver extends BroadcastReceiver {

    int messageId;

    @Override
    public void onReceive(Context context, Intent intent) {
        //minimal check
        if(intent == null || intent.getAction() == null)
            return;
        Bundle bundle = intent.getExtras();

        //receive message
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i =0; i < pdus.length; i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
            }
            else {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
        }
        String phone = messages[0].getDisplayOriginatingAddress();
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < messages.length; i++){
            body.append(messages[i].getMessageBody());
        }
        String messageBody = body.toString();

        MessageListAdapter.getInstace().addMessage(new Message(phone, messageBody));
    }
}
