package com.example.ct_native_3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public CleverTapAPI clevertapDefaultInstance;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;


    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
//        clevertapDefaultInstance.setDisplayUnitListener(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestNotificationPermission();
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

        Button Activity1 = findViewById(R.id.activity1);
        Button Activity2 = findViewById(R.id.activity2);

        Activity1.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
        });

        Activity2.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                        startActivity(intent);
        });

    }

    @Override
    protected void prepareDisplayView(CleverTapDisplayUnit unit) {
        List<String> imageUrls = new ArrayList<>();

        for (CleverTapDisplayUnitContent content : unit.getContents()) {
            if (content.getMedia() != null && !content.getMedia().isEmpty()) {
                imageUrls.add(content.getMedia());  // Add image URL
            }
        }

        if (!imageUrls.isEmpty()) {
            setupViewPager(imageUrls);
            clevertapDefaultInstance.pushDisplayUnitViewedEventForID(unit.getUnitID());
        }
    }

    private void setupViewPager(List<String> imageUrls) {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageCarouselAdapter adapter = new ImageCarouselAdapter(this, imageUrls);

        viewPager.setAdapter(adapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // 🔥 Enable Auto Slide Effect
        setupAutoScroll(viewPager, imageUrls.size());
    }

    private void setupAutoScroll(ViewPager2 viewPager, int itemCount) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int currentPage = 0;

            @Override
            public void run() {
                if (currentPage == itemCount) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 5000); // Change image every 5 seconds
            }
        };

        handler.postDelayed(runnable, 5000);
    }
}