package com.example.relationshipapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;

public class GiftDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);

        // Xử lý nút back
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        int giftType = getIntent().getIntExtra("gift_type", 0);
        
        ImageView imageView = findViewById(R.id.detailImage);
        TextView titleView = findViewById(R.id.detailTitle);
        TextView descView = findViewById(R.id.detailDescription);

        switch (giftType) {
            case 0: // Gift
                imageView.setImageResource(R.drawable.ic_gift);
                titleView.setText("Perfect Gift Ideas");
                descView.setText("Find the perfect gift for your special someone:\n\n" +
                               "• Personalized jewelry\n" +
                               "• Custom photo album\n" +
                               "• Handwritten love letter\n" +
                               "• Their favorite hobby items");
                break;
            case 1: // Heart
                imageView.setImageResource(R.drawable.ic_gift_heart);
                titleView.setText("Express Your Love");
                descView.setText("Ways to show your affection:\n\n" +
                               "• Say 'I love you' every day\n" +
                               "• Give unexpected hugs\n" +
                               "• Leave sweet notes\n" +
                               "• Plan surprise dates");
                break;
            case 2: // Dinner
                imageView.setImageResource(R.drawable.ic_gift_flower);
                titleView.setText("Romantic Dinner Ideas");
                descView.setText("Create a memorable dining experience:\n\n" +
                               "• Candlelight dinner at home\n" +
                               "• Their favorite restaurant\n" +
                               "• Picnic under the stars\n" +
                               "• Cooking together");
                break;
            case 3: // Rose
                imageView.setImageResource(R.drawable.ic_gift_ring);
                titleView.setText("Romantic Gestures");
                descView.setText("Beautiful ways to romance:\n\n" +
                               "• Fresh flowers delivery\n" +
                               "• Morning coffee in bed\n" +
                               "• Romantic playlist\n" +
                               "• Weekend getaway");
                break;
        }
    }
} 