package com.example.testedittext.visual;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

// Графический элемент, который открывает выпадающий список сразу при касании
public class InstantAutoComplete extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    public InstantAutoComplete(Context context) {
        super(context);
        this.setListeners();
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setListeners();
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        this.setListeners();
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    public void setListeners(){
        InstantAutoComplete instantAutoComplete = this;
        instantAutoComplete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                instantAutoComplete.setFocusableInTouchMode(true);
                instantAutoComplete.setFocusable(true);
                instantAutoComplete.requestFocus();
                instantAutoComplete.showDropDown();
            }
        });
        instantAutoComplete.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                    instantAutoComplete.setText(instantAutoComplete.getText());
                    instantAutoComplete.showDropDown();
                }else {
                    instantAutoComplete.setFocusableInTouchMode(false);
                    instantAutoComplete.setFocusable(false);
                }
            }
        });
    }







}
