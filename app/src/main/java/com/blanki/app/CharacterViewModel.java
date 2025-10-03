package com.blanki.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CharacterViewModel extends ViewModel {

    // Using String for now, can be changed to Integer for drawable resource IDs later
    private final MutableLiveData<String> eyes = new MutableLiveData<>(" o    o ");
    private final MutableLiveData<String> mouth = new MutableLiveData<>(" ㅅ ");

    public LiveData<String> getEyes() {
        return eyes;
    }

    public void setEyes(String newEyes) {
        eyes.setValue(newEyes);
    }

    public LiveData<String> getMouth() {
        return mouth;
    }

    public void setMouth(String newMouth) {
        mouth.setValue(newMouth);
    }
}
