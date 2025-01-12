package com.example.relationshipapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.relationshipapp.database.NotesDbHelper;
import java.util.List;

public class GiftDetailActivity extends AppCompatActivity {
    private TextInputEditText noteEditText;
    private TextView savedNotesText;
    private NotesDbHelper dbHelper;
    private int giftType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);

        // Khởi tạo database helper
        dbHelper = new NotesDbHelper(this);
        giftType = getIntent().getIntExtra("gift_type", 0);

        // Ánh xạ các view
        ImageButton backButton = findViewById(R.id.backButton);
        MaterialButton saveButton = findViewById(R.id.saveButton);
        MaterialButton clearButton = findViewById(R.id.clearButton);
        noteEditText = findViewById(R.id.noteEditText);
        savedNotesText = findViewById(R.id.savedNotesText);
        
        ImageView imageView = findViewById(R.id.detailImage);
        TextView titleView = findViewById(R.id.detailTitle);
        TextView descView = findViewById(R.id.detailDescription);

        // Load saved notes
        loadSavedNotes();

        // Xử lý nút back
        backButton.setOnClickListener(v -> finish());

        // Xử lý nút Save
        saveButton.setOnClickListener(v -> {
            String note = noteEditText.getText().toString().trim();
            if (!note.isEmpty()) {
                dbHelper.addNote(giftType, note);
                noteEditText.setText("");
                loadSavedNotes();
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút Clear
        clearButton.setOnClickListener(v -> {
            dbHelper.deleteAllNotes(giftType);
            loadSavedNotes();
            Toast.makeText(this, "All notes cleared", Toast.LENGTH_SHORT).show();
        });

        // Set up content based on gift type
        switch (giftType) {
            case 0: // Gift
                imageView.setImageResource(R.drawable.ic_gift);
                titleView.setText("Perfect Gift Ideas");
                descView.setText("Find the perfect gift for your special someone:\n\n" +
                        "1. Personalized jewelry\n" +
                        "2. Custom photo album\n" +
                        "3. Handwritten love letter\n" +
                        "4. Couple's experience gift\n" +
                        "5. Meaningful keepsake");
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
                        "• Favorite restaurant\n" +
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

    private void loadSavedNotes() {
        List<String> notes = dbHelper.getNotes(giftType);
        if (notes.isEmpty()) {
            savedNotesText.setText("No saved notes");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Saved Notes:\n\n");
            for (int i = 0; i < notes.size(); i++) {
                sb.append(i + 1).append(". ").append(notes.get(i)).append("\n\n");
            }
            savedNotesText.setText(sb.toString());
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
} 