package com.example.testedittext.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testedittext.R;

import java.util.List;


public class ViewEditor {

    // Функция для изменения прозрачности каждого элемента внутри ScrollView
    public static void setViewsTransparency(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            Drawable background = viewGroup.getBackground();

            if (child instanceof TextView) {
                TextView textView = (TextView) child;
                background = textView.getBackground();
            }
            if (background != null) {
                viewGroup.setBackgroundColor(color);

            }

            if (child instanceof ViewGroup) {
                setViewsTransparency((ViewGroup) child, color);
            }
        }
    }


    public static void showButtons(List<View> viewList, View dimView) {
        Animation fadeIn = AnimationUtils.loadAnimation(dimView.getContext(), R.anim.fade_in);
        dimView.setVisibility(View.VISIBLE);
        dimView.startAnimation(fadeIn);

        for (View view :viewList) {
            view.setVisibility(View.VISIBLE);
            view.startAnimation(AnimationUtils.loadAnimation(dimView.getContext(), R.anim.slide_in));
        }

    }

    public static void hideButtons(List<View> viewList, View dimView) {
        Animation fadeOut = AnimationUtils.loadAnimation(dimView.getContext(), R.anim.fade_out);
        dimView.startAnimation(fadeOut);
        dimView.setVisibility(View.GONE);
        for (View view :viewList) {
            view.setVisibility(View.GONE);
            view.startAnimation(AnimationUtils.loadAnimation(dimView.getContext(), R.anim.slide_out));
        }

    }


}
