package com.blanki.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView userInfo;
    private ImageButton happyButton, neutralButton, sadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userInfo = view.findViewById(R.id.user_info);
        happyButton = view.findViewById(R.id.mood_happy_button);
        neutralButton = view.findViewById(R.id.mood_neutral_button);
        sadButton = view.findViewById(R.id.mood_sad_button);

        setupMoodTracker();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mAuth.getCurrentUser());
    }

    private void setupMoodTracker() {
        happyButton.setOnClickListener(v -> saveMood("happy"));
        neutralButton.setOnClickListener(v -> saveMood("neutral"));
        sadButton.setOnClickListener(v -> saveMood("sad"));
    }

    private void saveMood(String mood) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Map<String, Object> moodData = new HashMap<>();
        moodData.put("mood", mood);
        moodData.put("timestamp", new Date());

        db.collection("users").document(currentUser.getUid())
                .collection("moods").document(today)
                .set(moodData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Mood successfully written!");
                    Toast.makeText(getActivity(), "오늘의 기분이 '" + mood + "'(으)로 저장되었어요.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing mood", e);
                    Toast.makeText(getActivity(), "기분 저장에 실패했어요.", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userInfo.setText("안녕하세요, " + user.getDisplayName() + "님!");
        } else {
            userInfo.setText("사용자 정보를 불러올 수 없습니다.");
        }
    }
}
