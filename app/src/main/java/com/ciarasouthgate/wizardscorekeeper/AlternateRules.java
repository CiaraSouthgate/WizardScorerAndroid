package com.ciarasouthgate.wizardscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AlternateRules extends AppCompatActivity {
    private static final String RULES_PREF = "RULES_PREF";
    private static final String CDN_RULE = "CDN_RULE";
    private static final String NO_EVEN = "NO_EVEN";
    private static final String ONE_TO_X = "ONE_TO_X";

    Toolbar appBar;

    SwitchMaterial cdnRuleSwitch;
    SwitchMaterial noEvenSwitch;
    SwitchMaterial oneToXSwitch;
    SharedPreferences prefs;
    private boolean cdnRuleActive;
    private boolean noEvenActive;
    private boolean oneToXActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_rules);

        cdnRuleSwitch = findViewById(R.id.cdnRuleSwitch);
        noEvenSwitch = findViewById(R.id.noEvenSwitch);
        oneToXSwitch = findViewById(R.id.oneToXSwitch);

        appBar = findViewById(R.id.rulesAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == (R.id.backButton))
                    finish();
                return true;
            }
        });

        prefs = getSharedPreferences(RULES_PREF, Context.MODE_PRIVATE);

        cdnRuleActive = prefs.getBoolean(CDN_RULE, false);
        noEvenActive = prefs.getBoolean(NO_EVEN, false);
        oneToXActive = prefs.getBoolean(ONE_TO_X, false);

        cdnRuleSwitch.setChecked(cdnRuleActive);
        noEvenSwitch.setChecked(noEvenActive);
        oneToXSwitch.setChecked(oneToXActive);

        if (cdnRuleActive)
            noEvenSwitch.setEnabled(false);

        if (noEvenActive)
            cdnRuleSwitch.setEnabled(false);

        setListeners();
    }

    public void setListeners() {
        cdnRuleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                onCdnRuleSwitched(isChecked);
            }
        });

        noEvenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                onNoEvenSwitched(isChecked);
            }
        });

        oneToXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                onOneToXSwitched(isChecked);
            }
        });
    }

    public void onCdnRuleSwitched(boolean checked) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CDN_RULE, checked);

        if (editor.commit()) {
            noEvenSwitch.setEnabled(!checked);
            cdnRuleActive = checked;
        } else {
            cdnRuleSwitch.setChecked(!checked);
            Toast.makeText(this, getString(R.string.prefs_err), Toast.LENGTH_LONG).show();
        }
    }

    public void onNoEvenSwitched(boolean checked) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(NO_EVEN, checked);
        if (editor.commit()) {
            cdnRuleSwitch.setEnabled(!checked);
            noEvenActive = checked;
        } else {
            noEvenSwitch.setChecked(!checked);
            Toast.makeText(this, getString(R.string.prefs_err), Toast.LENGTH_LONG).show();
        }
    }

    public void onOneToXSwitched(boolean checked) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ONE_TO_X, checked);
        if (editor.commit()) {
            oneToXActive = checked;
        } else {
            oneToXSwitch.setChecked(!checked);
            Toast.makeText(this, getString(R.string.prefs_err), Toast.LENGTH_LONG).show();
        }
    }

    public void onContactDevClicked(View v) {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ciarasouthgate.dev@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Wizard Scorekeeper Rule Suggestion");
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }
    }

}