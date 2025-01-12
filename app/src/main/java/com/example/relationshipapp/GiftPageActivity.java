package com.example.relationshipapp;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class GiftPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        ViewPager2 giftPager = findViewById(R.id.giftPager);
        GiftPagerAdapter adapter = new GiftPagerAdapter(this);
        giftPager.setAdapter(adapter);
        
        // Start from middle to enable infinite scrolling both ways
        giftPager.setCurrentItem(Integer.MAX_VALUE / 2);
        
        // Optional: Add page transformation animation
        giftPager.setPageTransformer((page, position) -> {
            float absPosition = Math.abs(position);
            page.setAlpha(1 - absPosition * 0.5f);
            page.setScaleX(1 - absPosition * 0.2f);
            page.setScaleY(1 - absPosition * 0.2f);
        });

        // Animation for anniversary text
        TextView anniversaryText = findViewById(R.id.anniversaryText);
        Animation congratsAnim = AnimationUtils.loadAnimation(this, R.anim.congratulation_anim);
        anniversaryText.startAnimation(congratsAnim);
    }
} 