package com.blanki.app;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CharacterViewModel extends ViewModel {
    private static final String TAG = "CharacterViewModel";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private final MutableLiveData<String> eyes = new MutableLiveData<>(" o    o ");
    private final MutableLiveData<String> mouth = new MutableLiveData<>(" ㅅ ");

    public CharacterViewModel() {
        loadCharacterData();
    }

    public LiveData<String> getEyes() {
        return eyes;
    }

    public void setEyes(String newEyes) {
        if (newEyes != null && !newEyes.equals(eyes.getValue())) {
            eyes.setValue(newEyes);
            saveCharacterData();
        }
    }

    public LiveData<String> getMouth() {
        return mouth;
    }

    public void setMouth(String newMouth) {
        if (newMouth != null && !newMouth.equals(mouth.getValue())) {
            mouth.setValue(newMouth);
            saveCharacterData();
        }
    }

    private void saveCharacterData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Log.w(TAG, "User not logged in, cannot save character data.");
            return;
        }

        Map<String, Object> character = new HashMap<>();
        character.put("eyes", eyes.getValue());
        character.put("mouth", mouth.getValue());

        db.collection("users").document(currentUser.getUid())
                .collection("character").document("appearance")
                .set(character)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Character data successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing character data", e));
    }

    public void loadCharacterData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Log.w(TAG, "User not logged in, cannot load character data.");
            return;
        }

        db.collection("users").document(currentUser.getUid())
                .collection("character").document("appearance")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String loadedEyes = documentSnapshot.getString("eyes");
                        String loadedMouth = documentSnapshot.getString("mouth");
                        eyes.postValue(loadedEyes);
                        mouth.postValue(loadedMouth);
                        Log.d(TAG, "Character data loaded: " + documentSnapshot.getData());
                    } else {
                        Log.d(TAG, "No character data found for user, using default.");
                    }
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error getting character data", e));
    }
}
