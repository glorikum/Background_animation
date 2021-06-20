package com.onix.internship.backgroundanimation.paws;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.onix.internship.backgroundanimation.R;

import java.util.ArrayList;


public class AnimationView extends View {

    /**
     * Class representing the state of a paw
     */
    private static class Paw {
        private int x;
        private int y;
    }

    private static final int SPEED = 3;


    private final ArrayList<Paw> arrayPaws = new ArrayList<>();

    private TimeAnimator timeAnimator;
    private Drawable drawable;

    private int drawableSize;

    /**
     * @see View#View(Context)
     */
    public AnimationView(Context context) {
        super(context);
        init();
    }

    /**
     * @see View#View(Context, AttributeSet)
     */
    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @see View#View(Context, AttributeSet, int)
     */
    public AnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        drawable = ContextCompat.getDrawable(getContext(), R.drawable.paw);
        drawableSize = (int) (Math.max(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()) / 2f);
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        initArrayPaws(width, height);
    }

    private void initArrayPaws(int width, int height) {
        for (int y = drawableSize; y < height; y += drawableSize * 3) {
            for (int x = 0; x < width; x += drawableSize * 3) {
                Paw paw = new Paw();
                initializePaw(paw, x, y);
                arrayPaws.add(paw);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int viewWidth = getWidth();
        for (final Paw paw : arrayPaws) {
            // Ignore the paw if it's outside of the view bounds
            if (paw.x + drawableSize < 0 || paw.x - drawableSize > viewWidth) {
                continue;
            }

            // Save the current canvas state
            final int save = canvas.save();

            // Move the canvas to the center of the paw
            canvas.translate(paw.x, paw.y);

            // Prepare the size and alpha of the drawable
            final int size = Math.round(drawableSize);
            drawable.setBounds(-size, -size, size, size);

            // Draw the paw to the canvas
            drawable.draw(canvas);

            // Restore the canvas to it's previous position and rotation
            canvas.restoreToCount(save);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timeAnimator = new TimeAnimator();
        timeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (!isLaidOut()) {
                    // Ignore all calls before the view has been measured and laid out.
                    return;
                }
                updateState();
                invalidate();
            }
        });
        timeAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timeAnimator.cancel();
        timeAnimator.setTimeListener(null);
        timeAnimator.removeAllListeners();
        timeAnimator = null;
    }

    private void updateState() {
        final int viewWidth = getWidth();

        for (Paw paw : arrayPaws) {
            paw.x += SPEED;

            if (paw.x - drawableSize > viewWidth) {
                paw.x = -drawableSize;
            }
        }
    }

    private void initializePaw(Paw paw, int xPosition, int yPosition) {
        paw.x = xPosition;
        paw.y = yPosition;
    }
}

