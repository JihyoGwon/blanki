package com.blanki.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

public class FaceFragment extends Fragment {

    private TextView faceEyes, faceMouth;
    private Button eyeStyle1, eyeStyle2, eyeStyle3;
    private Button mouthStyle1, mouthStyle2, mouthStyle3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face, container, false);

        faceEyes = view.findViewById(R.id.face_eyes);
        faceMouth = view.findViewById(R.id.face_mouth);
        eyeStyle1 = view.findViewById(R.id.eye_style1);
        eyeStyle2 = view.findViewById(R.id.eye_style2);
        eyeStyle3 = view.findViewById(R.id.eye_style3);
        mouthStyle1 = view.findViewById(R.id.mouth_style1);
        mouthStyle2 = view.findViewById(R.id.mouth_style2);
        mouthStyle3 = view.findViewById(R.id.mouth_style3);

        setupFaceMaker();

        return view;
    }

    private void setupFaceMaker() {
        eyeStyle1.setOnClickListener(v -> faceEyes.setText(" o    o "));
        eyeStyle2.setOnClickListener(v -> faceEyes.setText(" >    < "));
        eyeStyle3.setOnClickListener(v -> faceEyes.setText(" -    - "));

        mouthStyle1.setOnClickListener(v -> faceMouth.setText(" ㅅ "));
        mouthStyle2.setOnClickListener(v -> faceMouth.setText("  o  "));
        mouthStyle3.setOnClickListener(v -> faceMouth.setText(" --- "));
    }
}
