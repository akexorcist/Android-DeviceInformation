package app.akeorcist.deviceinformation.utility;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class AnimateUtils {

    public static View.OnTouchListener getTouchAnimateListener(final float scale) {
        return getTouchAnimateListener(scale, null);
    }

    public static View.OnTouchListener getTouchAnimateListener(final float scale, final View targetView) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    if(targetView != null)
                        AnimateUtils.scaleAnimate(targetView, scale);
                    else
                        AnimateUtils.scaleAnimate(v, scale);
                } else if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_CANCEL ||
                        action == MotionEvent.ACTION_OUTSIDE) {
                    if(targetView != null)
                        AnimateUtils.scaleAnimate(targetView, 1f);
                    else
                        AnimateUtils.scaleAnimate(v, 1f);
                }
                return false;
            }
        };
    }

    public static View.OnTouchListener touchAnimateListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN) {
                AnimateUtils.scaleAnimate(v, 0.7f);
            } else if(action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_CANCEL ||
                    action == MotionEvent.ACTION_OUTSIDE) {
                AnimateUtils.scaleAnimate(v, 1f);
            }
            return false;
        }
    };

    public static void scaleAnimate(View v, float scale) {
        scaleAnimate(v, scale, 500);
    }

    public static void scaleAnimate(View v, float scale, int duration) {
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(v, "scaleX", scale);
        animatorScaleX.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(v, "scaleY", scale);
        animatorScaleY.setInterpolator(new OvershootInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.start();
    }

    public static void scaleIn(View v) {
        scaleAnimateFadeIn(v, 300, null);
    }

    public static void scaleIn(View v, Animator.AnimatorListener listener) {
        scaleAnimateFadeIn(v, 300, listener);
    }

    public static void scaleAnimateFadeIn(View v, int duration, Animator.AnimatorListener listener) {
        v.setVisibility(View.VISIBLE);
        v.clearAnimation();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f);
        animatorScaleX.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(v, "scaleY", 0f, 1f);
        animatorScaleY.setInterpolator(new OvershootInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.start();
        if(listener != null)
            animatorSet.addListener(listener);
    }

    public static void scaleOutWithZero(View v) {
        scaleAnimateFadeOut(v, 1, null);
    }

    public static void scaleOut(View v) {
        scaleAnimateFadeOut(v, 300, null);
    }

    public static void scaleOut(View v, Animator.AnimatorListener listener) {
        scaleAnimateFadeOut(v, 300, listener);
    }

    public static void scaleAnimateFadeOut(View v, int duration, Animator.AnimatorListener listener) {
        v.setVisibility(View.VISIBLE);
        v.clearAnimation();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0f);
        animatorScaleX.setInterpolator(new AnticipateInterpolator());
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0f);
        animatorScaleY.setInterpolator(new AnticipateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.start();
        if(listener != null)
            animatorSet.addListener(listener);
    }

    public static void fadeInAnimate(View v) {
        v.clearAnimation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 1f);
        animator.setDuration(300);
        animator.start();
        v.setVisibility(View.VISIBLE);
    }

    public static void fadeInAnimateWithZero(View v) {
        v.clearAnimation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        animator.setDuration(300);
        animator.start();
        v.setVisibility(View.VISIBLE);
    }
    public static void fadeOutAnimate(View v) {
        fadeOutAnimate(v, null);
    }

    public static void fadeOutAnimate(final View v, final OnProgressGoneListener listener) {
        v.clearAnimation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f);
        animator.setDuration(300);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
                if(listener != null)
                    listener.onGone();
            }

            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }

    public interface OnProgressGoneListener {
        public void onGone();
    }
}
