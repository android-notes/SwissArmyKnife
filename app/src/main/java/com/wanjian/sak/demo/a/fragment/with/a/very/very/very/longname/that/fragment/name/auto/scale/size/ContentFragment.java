package com.wanjian.sak.demo.a.fragment.with.a.very.very.very.longname.that.fragment.name.auto.scale.size;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        int color = ((int) (Math.random() * Integer.MAX_VALUE)) | 0xFF000000;
        textView.setBackgroundColor(color);
        textView.setText(Integer.toHexString(color));
        return textView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
