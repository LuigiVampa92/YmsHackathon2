package com.luigivampa92.hackathon2uncompressed;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class ListPreferenceWithValue extends ListPreference {

    private TextView textValue;

    public ListPreferenceWithValue(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preference_with_value);
    }

    public ListPreferenceWithValue(Context context) {
        super(context);
        setLayoutResource(R.layout.preference_with_value);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        textValue = (TextView) view.findViewById(R.id.preference_value);
        if (textValue != null) {
            textValue.setText(getValue());
        }
    }

}