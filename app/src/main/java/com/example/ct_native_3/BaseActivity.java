package com.example.ct_native_3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity implements DisplayUnitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        for (CleverTapDisplayUnit unit : units) {
            prepareDisplayView(unit);  // Delegate to abstract method for flexibility
        }
    }

    // Let child activities define how they want to render the display view
    protected abstract void prepareDisplayView(CleverTapDisplayUnit unit);
}
