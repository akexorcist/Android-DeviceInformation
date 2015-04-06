package app.akeorcist.deviceinformation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 3/8/15 AD.
 */
public class FinishImageView extends ImageView {
    public static final int STATE_IDLE = 0;
    public static final int STATE_FINISH = 1;
    public static final int STATE_ERROR = 2;

    private int state = STATE_IDLE;

    private int srcFinishResId = 0;
    private int bgFinishResId = 0;
    private int srcErrorResId = 0;
    private int bgErrorResId = 0;

    public FinishImageView(Context context) {
        super(context);
    }

    public FinishImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FinishImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFinishImageResource(int srcFinishResId) {
        this.srcFinishResId = srcFinishResId;
    }

    public void setFinishBackgroundResource(int bgFinishResId) {
        this.bgFinishResId = bgFinishResId;
    }

    public void setErrorImageResource(int srcErrorResId) {
        this.srcErrorResId = srcErrorResId;
    }

    public void setErrorBackgroundResource(int bgErrorResId) {
        this.bgErrorResId = bgErrorResId;
    }

    public void setState(int state) {
        switch(state) {
            case STATE_IDLE:
                clear();
                break;
            case STATE_FINISH:
                finish();
                break;
            case STATE_ERROR:
                error();
                break;
        }
    }

    public int getState() {
        return state;
    }

    public void clear() {
        state = STATE_IDLE;
        this.setBackgroundResource(R.drawable.shape_oval_light_gray);
        this.setImageResource(0);
        pushAnimate();
    }

    public void finish() {
        state = STATE_FINISH;
        this.setImageResource(srcFinishResId);
        this.setBackgroundResource(bgFinishResId);
        pushAnimate();
    }

    public void error() {
        state = STATE_ERROR;
        this.setImageResource(srcErrorResId);
        this.setBackgroundResource(bgErrorResId);
        pushAnimate();
    }

    private void pushAnimate() {
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.7f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.7f);
        animSet.playTogether(animScaleX, animScaleY);
        animSet.setDuration(300);
        animSet.setInterpolator(new CycleInterpolator(0.5f));
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                pullAnimate();
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animSet.start();
    }

    private void pullAnimate() {
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.2f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.2f);
        animSet.playTogether(animScaleX, animScaleY);
        animSet.setDuration(300);
        animSet.setInterpolator(new CycleInterpolator(0.5f));
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) { }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animSet.start();
    }

}
