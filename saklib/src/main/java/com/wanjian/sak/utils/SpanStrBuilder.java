package com.wanjian.sak.utils;

import android.text.SpannableString;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;


public class SpanStrBuilder {

    private List<Object> charSequences = new ArrayList<>();
    private List<Object[]> styles = new ArrayList<>();


    public SpanStrBuilder append(Object charSequence, Object... style) {
        charSequences.add(charSequence);
        styles.add(style);
        return this;
    }


    public SpanStrBuilder append(Object charSequence) {
        charSequences.add(charSequence);
        styles.add(null);
        return this;
    }

    public SpannableString build() {
        StringBuilder builder = new StringBuilder();
        for (Object chs : charSequences) {
            builder.append(chs);
        }

        SpannableString string = new SpannableString(builder);

        int start = 0;
        for (int i = 0; i < charSequences.size(); i++) {

            int len = String.valueOf(charSequences.get(i)).length();
            Object[] o = styles.get(i);
            if (o != null) {
                for (Object o1 : o) {
                    string.setSpan(o1, start, start + len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            start += len;
        }
        return string;

    }
}
