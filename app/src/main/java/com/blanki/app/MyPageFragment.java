package com.blanki.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPageFragment extends Fragment {

    private CharacterViewModel characterViewModel;
    private TextView characterEyes, characterMouth, userNameText;
    private Button signOutButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        characterViewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        characterEyes = view.findViewById(R.id.character_eyes);
        characterMouth = view.findViewById(R.id.character_mouth);
        userNameText = view.findViewById(R.id.user_name_text);
        signOutButton = view.findViewById(R.id.my_page_sign_out_button);

        // Observe character data
        characterViewModel.getEyes().observe(getViewLifecycleOwner(), eyes -> characterEyes.setText(eyes));
        characterViewModel.getMouth().observe(getViewLifecycleOwner(), mouth -> characterMouth.setText(mouth));

        // Display user info
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userNameText.setText(currentUser.getDisplayName());
        }

        // Setup Google Sign-In for sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // Sign out button
        signOutButton.setOnClickListener(v -> signOut());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when the fragment is resumed to ensure it's up-to-date
        characterViewModel.loadCharacterData();
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), task -> {
            Toast.makeText(getActivity(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
            // Go back to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });
    }
}
