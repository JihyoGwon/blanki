import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;
    private Button signOutButton;
    private TextView userInfo;
    private ImageButton happyButton, neutralButton, sadButton;

    @Nullable
    @Override
        signInButton = view.findViewById(R.id.sign_in_button);
        signOutButton = view.findViewById(R.id.sign_out_button);
        userInfo = view.findViewById(R.id.user_info);
        happyButton = view.findViewById(R.id.mood_happy_button);
        neutralButton = view.findViewById(R.id.mood_neutral_button);
        sadButton = view.findViewById(R.id.mood_sad_button);

        signInButton.setOnClickListener(v -> signIn());
        signOutButton.setOnClickListener(v -> signOut());

        setupMoodTracker();
        updateUI(mAuth.getCurrentUser());

        return view;
    }

    private void setupMoodTracker() {
        happyButton.setOnClickListener(v -> saveMood("happy"));
        neutralButton.setOnClickListener(v -> saveMood("neutral"));
        sadButton.setOnClickListener(v -> saveMood("sad"));
    }

    private void saveMood(String mood) {
        SharedPreferences prefs = getActivity().getSharedPreferences("mood_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        editor.putString(today, mood);
        editor.apply();

        Toast.makeText(getActivity(), "오늘의 기분이 '" + mood + "'(으)로 저장되었어요.", Toast.SHORT_SHOW).show();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
// ... existing code ...
