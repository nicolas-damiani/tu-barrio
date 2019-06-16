package com.engineers.tubarrio.widgets;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.engineers.tubarrio.R;

public class Loader {
    public RelativeLayout relativeLayout;
    private Activity activity;

    public Loader(Activity activity) {
        this.relativeLayout = (RelativeLayout) activity.findViewById(R.id.loader);
        this.activity = activity;
    }

    public void showLoader() {
        Animation fade_in = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                R.anim.fade_in);

        relativeLayout.startAnimation(fade_in);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public void hideLoader(){
      /*  Animation fadeOut = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
*/                relativeLayout.setVisibility(View.GONE);
  /*          }
        });
        relativeLayout.startAnimation(fadeOut);*/
    }
}