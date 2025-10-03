package com.blanki.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // This fragment is now just a placeholder.
        // The actual calendar will be launched from MainActivity.
        // We can add other calendar-related UI here in the future if needed.
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
