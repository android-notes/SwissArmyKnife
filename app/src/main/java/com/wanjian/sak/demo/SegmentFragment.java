package com.wanjian.sak.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wanjian.sak.demo.a.fragment.with.a.very.very.very.longname.that.fragment.name.auto.scale.size.ContentFragment;


public class SegmentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.segment_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(((int) (Math.random() * Integer.MAX_VALUE)) | 0xFF000000);
        getChildFragmentManager().beginTransaction().replace(R.id.container, new ContentFragment()).commit();

    }
}
