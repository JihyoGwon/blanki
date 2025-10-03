package com.blanki.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FaceFragment extends Fragment {

    private CharacterViewModel characterViewModel;
    private TextView faceEyes, faceMouth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face, container, false);

        // ViewModel setup
        characterViewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        // UI elements
        faceEyes = view.findViewById(R.id.face_eyes);
        faceMouth = view.findViewById(R.id.face_mouth);

        // Observe ViewModel changes and update UI
        characterViewModel.getEyes().observe(getViewLifecycleOwner(), eyes -> faceEyes.setText(eyes));
        characterViewModel.getMouth().observe(getViewLifecycleOwner(), mouth -> faceMouth.setText(mouth));

        // TabLayout and ViewPager2 setup
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(categoryAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "눈" : "입")
        ).attach();

        return view;
    }
}
