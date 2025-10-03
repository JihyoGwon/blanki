package com.blanki.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextView userInfo;
    private ImageButton happyButton, neutralButton, sadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();

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
        // Update UI when fragment is resumed
        updateUI(mAuth.getCurrentUser());
    }

    private void setupMoodTracker() {
        happyButton.setOnClickListener(v -> saveMood("happy"));
        neutralButton.setOnClickListener(v -> saveMood("neutral"));
        sadButton.setOnClickListener(v -> saveMood("sad"));
    }

    private void saveMood(String mood) {
        if (getActivity() == null) return;
        SharedPreferences prefs = getActivity().getSharedPreferences("mood_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        editor.putString(today, mood);
        editor.apply();

        Toast.makeText(getActivity(), "오늘의 기분이 '" + mood + "'(으)로 저장되었어요.", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userInfo.setText("안녕하세요, " + user.getDisplayName() + "님!");
        } else {
            // This case should not happen if the logic is correct,
            // but as a fallback, show a generic message.
            userInfo.setText("사용자 정보를 불러올 수 없습니다.");
        }
    }
}
