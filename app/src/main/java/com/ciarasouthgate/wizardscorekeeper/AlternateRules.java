package com.ciarasouthgate.wizardscorekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;

import static com.ciarasouthgate.wizardscorekeeper.Constants.CDN_RULE;
import static com.ciarasouthgate.wizardscorekeeper.Constants.NO_EVEN;
import static com.ciarasouthgate.wizardscorekeeper.Constants.ONE_TO_X;

/**
 * An activity that allows the user to adjust their game settings.
 */
public class AlternateRules extends AppActivity {
    SwitchMaterial cdnRuleSwitch;
    SwitchMaterial noEvenSwitch;
    SwitchMaterial oneToXSwitch;

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

        appBar = findViewById(R.id.appBar);
        setBackOnlyAppBar();

        // Read current rules values from SharedPreferences to set switches to appropriate positions
        cdnRuleActive = rulesPrefs.getBoolean(CDN_RULE, false);
        noEvenActive = rulesPrefs.getBoolean(NO_EVEN, false);
        oneToXActive = rulesPrefs.getBoolean(ONE_TO_X, false);

        cdnRuleSwitch.setChecked(cdnRuleActive);
        noEvenSwitch.setChecked(noEvenActive);
        oneToXSwitch.setChecked(oneToXActive);

        // Canadian Rule and No Even rule cannot both be active at the same time
        if (cdnRuleActive)
            noEvenSwitch.setEnabled(false);

        if (noEvenActive)
            cdnRuleSwitch.setEnabled(false);

        setListeners();
    }

    /**
     * Add listeners to the rules switches
     */
    public void setListeners() {
        cdnRuleSwitch.setOnCheckedChangeListener((compoundButton, isChecked)
                -> onCdnRuleSwitched(isChecked));

        noEvenSwitch.setOnCheckedChangeListener((compoundButton, isChecked)
                -> onNoEvenSwitched(isChecked));

        oneToXSwitch.setOnCheckedChangeListener((compoundButton, isChecked)
                -> onOneToXSwitched(isChecked));
    }

    /**
     * Write a rule update to the SharedPreferences
     *
     * @param rule     the constant string representing the rule to write to
     * @param isActive whether the rule is active
     * @return return value of commit call
     */
    public boolean updateRule(String rule, boolean isActive) {
        SharedPreferences.Editor editor = rulesPrefs.edit();
        editor.putBoolean(rule, isActive);
        return editor.commit();
    }

    public void onCdnRuleSwitched(boolean checked) {
        if (updateRule(CDN_RULE, checked)) {
            noEvenSwitch.setEnabled(!checked);
            cdnRuleActive = checked;
        } else {
            cdnRuleSwitch.setChecked(!checked);
            displayError(getString(R.string.prefs_err));
        }
    }

    public void onNoEvenSwitched(boolean checked) {
        if (updateRule(NO_EVEN, checked)) {
            cdnRuleSwitch.setEnabled(!checked);
            noEvenActive = checked;
        } else {
            noEvenSwitch.setChecked(!checked);
            displayError(getString(R.string.prefs_err));
        }
    }

    public void onOneToXSwitched(boolean checked) {
        if (updateRule(ONE_TO_X, checked)) {
            oneToXActive = checked;
        } else {
            oneToXSwitch.setChecked(!checked);
            displayError(getString(R.string.prefs_err));
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