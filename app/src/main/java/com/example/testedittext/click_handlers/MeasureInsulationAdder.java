package com.example.testedittext.click_handlers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.testedittext.MainActivity;
import com.example.testedittext.R;
import com.example.testedittext.activities.InsulationActivity;

import java.util.List;

public class MeasureInsulationAdder implements View.OnClickListener {
    private ConstraintLayout constraintLayout;
    private Context context;

    ScrollView scrollView;

    List<ConstraintLayout> cLList;

    public MeasureInsulationAdder(ConstraintLayout constraintLayout, List<ConstraintLayout> cLList) {
        this.constraintLayout = constraintLayout;
        this.cLList = cLList;
        this.scrollView = (ScrollView) constraintLayout.getParent();
    }

    @Override
    public void onClick(View view) {
        this.context = view.getContext();

        ConstraintLayout cL = new ConstraintLayout(context);
        cL.setId(View.generateViewId());

        EditText editText1 = new EditText(context);
        editText1.setText("1");
        editText1.setId(View.generateViewId());

        EditText editText2 = new EditText(context);
        editText2.setText("2");
        editText2.setId(View.generateViewId());

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams layoutParamsForEt1 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams layoutParamsForEt2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsForEt2.startToEnd = editText1.getId();

        if (cLList.size() == 0){
            layoutParams.topToTop = R.id.constraint2;
        }else {
            layoutParams.topToBottom = cLList.get(cLList.size() - 1).getId();

        }

        cL.addView(editText1, layoutParamsForEt1);
        cL.addView(editText2, layoutParamsForEt2);

        cLList.add(cL);

        constraintLayout.addView(cL,layoutParams);

        // Автоматическая прокрутка scrollView
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        },10);

    }
}
