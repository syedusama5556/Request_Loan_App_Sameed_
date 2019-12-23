/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.infusiblecoder.loanappsameed.R;


public class SplashActivity extends AppCompatActivity {

    private TextView pear2pearTextView;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.splash_activity);
        this.init();
        startAnimationOne();
    }

    private void init() {

        // Configure Pear2Pear component
        pear2pearTextView = this.findViewById(R.id.pear2pear_text_view);
    }

    public void startAnimationOne() {

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(pear2pearTextView, PropertyValuesHolder.ofKeyframe(View.TRANSLATION_Y, Keyframe.ofFloat(0f, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3000f, this.getResources().getDisplayMetrics())), Keyframe.ofFloat(0.52f, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -25f, this.getResources().getDisplayMetrics())), Keyframe.ofFloat(0.65f, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, this.getResources().getDisplayMetrics())), Keyframe.ofFloat(0.78f, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5f, this.getResources().getDisplayMetrics())), Keyframe.ofFloat(1f, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, this.getResources().getDisplayMetrics()))));
        animator1.setDuration(3000);
        animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.61f, 1f));

        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(pear2pearTextView, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.8f, 1f), Keyframe.ofFloat(1f, 1f)));
        animator2.setDuration(2500);
        animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.61f, 1f));

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, animator2);
        animatorSet1.setTarget(pear2pearTextView);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet1);
        animatorSet2.start();
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
