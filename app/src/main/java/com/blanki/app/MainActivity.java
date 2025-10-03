package com.blanki.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ScrollView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    
    private com.google.android.gms.common.SignInButton signInButton;
    private Button signOutButton;
    private TextView userInfo;
    
    // Navigation elements
    private BottomNavigationView bottomNavigation;
    private ScrollView homeFragment, faceFragment, calendarFragment;
    private TextView faceEyes, faceMouth, currentDate;
    private Button eyeStyle1, eyeStyle2, eyeStyle3;
    private Button mouthStyle1, mouthStyle2, mouthStyle3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Auth 초기화
        mAuth = FirebaseAuth.getInstance();

        // Google Sign In 옵션 설정
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // UI 요소들 초기화
        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = findViewById(R.id.sign_out_button);
        userInfo = findViewById(R.id.user_info);
        
        // Navigation elements
        bottomNavigation = findViewById(R.id.bottom_navigation);
        homeFragment = findViewById(R.id.home_fragment);
        faceFragment = findViewById(R.id.face_fragment);
        calendarFragment = findViewById(R.id.calendar_fragment);
        
        // Face maker elements
        faceEyes = findViewById(R.id.face_eyes);
        faceMouth = findViewById(R.id.face_mouth);
        currentDate = findViewById(R.id.current_date);
        eyeStyle1 = findViewById(R.id.eye_style1);
        eyeStyle2 = findViewById(R.id.eye_style2);
        eyeStyle3 = findViewById(R.id.eye_style3);
        mouthStyle1 = findViewById(R.id.mouth_style1);
        mouthStyle2 = findViewById(R.id.mouth_style2);
        mouthStyle3 = findViewById(R.id.mouth_style3);

        // 로그인 버튼 클릭 이벤트
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // 로그아웃 버튼 클릭 이벤트
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        // 현재 사용자 상태 확인
        updateUI(mAuth.getCurrentUser());
        
        // Navigation setup
        setupNavigation();
        
        // Face maker setup
        setupFaceMaker();
        
        // Calendar setup
        setupCalendar();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "로그인 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MainActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        } else {
                            updateUI(null);
                            Toast.makeText(MainActivity.this, "인증 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
                Toast.makeText(MainActivity.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userInfo.setText("안녕하세요, " + user.getDisplayName() + "님!");
            signInButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            userInfo.setText("로그인이 필요합니다.");
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }
    }
    
    private void setupNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                showFragment(homeFragment);
                return true;
            } else if (itemId == R.id.nav_face) {
                showFragment(faceFragment);
                return true;
            } else if (itemId == R.id.nav_calendar) {
                showFragment(calendarFragment);
                return true;
            }
            return false;
        });
    }
    
    private void showFragment(ScrollView fragment) {
        homeFragment.setVisibility(fragment == homeFragment ? View.VISIBLE : View.GONE);
        faceFragment.setVisibility(fragment == faceFragment ? View.VISIBLE : View.GONE);
        calendarFragment.setVisibility(fragment == calendarFragment ? View.VISIBLE : View.GONE);
    }
    
    // Material 3 Bottom Navigation handles selection automatically
    
    private void setupFaceMaker() {
        eyeStyle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceEyes.setText(" o    o ");
            }
        });
        
        eyeStyle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceEyes.setText(" >    < ");
            }
        });
        
        eyeStyle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceEyes.setText(" -    - ");
            }
        });
        
        mouthStyle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceMouth.setText(" ㅅ ");
            }
        });
        
        mouthStyle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceMouth.setText("  o  ");
            }
        });
        
        mouthStyle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceMouth.setText(" --- ");
            }
        });
    }
    
    private void setupCalendar() {
        // Set current date
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.KOREAN);
        String currentDateStr = sdf.format(new java.util.Date());
        currentDate.setText(currentDateStr);
    }
}
