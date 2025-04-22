package com.example.ct_native_3;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;

import java.util.HashMap;

public class ThirdActivity extends BaseActivity {

    public CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        clevertapDefaultInstance.pushEvent("ThirdActivity");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void prepareDisplayView(CleverTapDisplayUnit unit) {
        Log.v("Clevertap", "secondprepareDisplayView: " + unit.toString());

        // Extract campaign_url_source and trigger from the custom extras map
        HashMap<String, String> customExtras = unit.getCustomExtras();
        String campaignUrlSource = "";
        String trigger = "";

        if (customExtras != null) {
            campaignUrlSource = customExtras.get("campaign_url_source") != null ?
                    customExtras.get("campaign_url_source") : "";
            trigger = customExtras.get("trigger") != null ?
                    customExtras.get("trigger") : "";
        }

        Log.v("Clevertap", "Campaign URL Source: " + campaignUrlSource);
        Log.v("Clevertap", "Trigger: " + trigger);

        // Find TextView elements to display the data
        TextView campaignTextView = findViewById(R.id.campaign_text_view2);
        TextView triggerTextView = findViewById(R.id.trigger_text_view2);

        // Set the text to display the data
        if (campaignTextView != null) {
            campaignTextView.setText("Campaign URL Source: " + campaignUrlSource);
        }

        if (triggerTextView != null) {
            triggerTextView.setText("Trigger: " + trigger);
        }
    }
}