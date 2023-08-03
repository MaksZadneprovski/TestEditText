package com.example.testedittext.visual;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.testedittext.R;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {

    private static final int ANIMATION_DURATION = 200;
    private static final float SCALE_FACTOR = 0.8f;
    private static final float CORNER_RADIUS = 10f;

    private int backgroundColor;

    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backgroundColor =  getResources().getColor(R.color.clickText, getContext().getTheme());
        setCornerRadius(CORNER_RADIUS);
    }

    private void setCornerRadius(float cornerRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(cornerRadius);
        drawable.setColor(backgroundColor);
        setBackground(drawable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animateButton(true);
                break;
            case MotionEvent.ACTION_UP:
                animateButton(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void animateButton(boolean isPressed) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat("scaleX", isPressed ? SCALE_FACTOR : 1f),
                PropertyValuesHolder.ofFloat("scaleY", isPressed ? SCALE_FACTOR : 1f)
        );
        scaleDown.setDuration(ANIMATION_DURATION);
        scaleDown.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDown.start();
    }
}
