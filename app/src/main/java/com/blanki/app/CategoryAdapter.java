package com.blanki.app;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.lifecycle.ViewModelProvider;

public class CategoryAdapter extends FragmentStateAdapter {

    public CategoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return StyleListFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2; // Eyes and Mouth
    }

    public static class StyleListFragment extends Fragment {
        private static final String ARG_CATEGORY = "category";

        public static StyleListFragment newInstance(int category) {
            StyleListFragment fragment = new StyleListFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_CATEGORY, category);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RecyclerView recyclerView = new RecyclerView(requireContext());
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            
            Resources res = getResources();
            String[] styles = (getArguments().getInt(ARG_CATEGORY) == 0)
                ? res.getStringArray(R.array.eye_styles)
                : res.getStringArray(R.array.mouth_styles);

            StyleAdapter.OnStyleClickListener listener = style -> {
                CharacterViewModel viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
                if (getArguments().getInt(ARG_CATEGORY) == 0) {
                    viewModel.setEyes(style);
                } else {
                    viewModel.setMouth(style);
                }
            };

            recyclerView.setAdapter(new StyleAdapter(styles, listener));
            return recyclerView;
        }
    }
}
