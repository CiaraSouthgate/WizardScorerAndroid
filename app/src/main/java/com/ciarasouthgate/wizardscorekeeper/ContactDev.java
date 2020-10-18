package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContactDev extends AppCompatActivity {
    Switch deviceInfo;
    Toolbar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_dev);

        appBar = findViewById(R.id.rulesAppBar);
        appBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == (R.id.backButton)) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
            return true;
        });

        deviceInfo = findViewById(R.id.deviceInfoSwitch);
    }

    public void sendEmail(View v) {
        String message = "\n\n" + getString(R.string.feedback_email_content);

        if (deviceInfo.isChecked()) {
            String details = "\nVERSION.RELEASE : " + Build.VERSION.RELEASE
                    + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
                    + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                    + "\nBRAND : " + Build.BRAND
                    + "\nHARDWARE : " + Build.HARDWARE
                    + "\nHOST : " + Build.HOST
                    + "\nID : " + Build.ID
                    + "\nMANUFACTURER : " + Build.MANUFACTURER
                    + "\nMODEL : " + Build.MODEL
                    + "\nPRODUCT : " + Build.PRODUCT
                    + "\nDISPLAY : " + Build.DISPLAY
                    + "\nTIME : " + Build.TIME;

            message += "\n\n" + details;
        }

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ciarasouthgate.dev@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Wizard Scorekeeper Feedback");
        email.putExtra(Intent.EXTRA_TEXT, message);
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }
    }

    public void openKoFi(View v) {
        String url = "https://ko-fi.com/ciarasouthgate";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}