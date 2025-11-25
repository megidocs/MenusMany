package com.example.menusmany;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CircularListView extends View {

    public interface OnItemClickListener {
        void onItemClick(int position, String item);
    }

    private OnItemClickListener listener;
    private List<String> items = new ArrayList<>();
    private Paint textPaint;
    private Paint selectedTextPaint;
    private float rotationAngle = 0;
    private float downX;
    private long downTime;
    private int selectedItemIndex = -1;

    public CircularListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        selectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedTextPaint.setColor(Color.RED);
        selectedTextPaint.setTextSize(40f);
        selectedTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setItems(List<String> items) {
        this.items = items;
        invalidate();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (items.isEmpty()) return;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 100;
        float angleStep = 360f / items.size();

        // Find the item closest to the bottom (90 degrees)
        float minAngleDiff = 360;
        selectedItemIndex = -1;

        for (int i = 0; i < items.size(); i++) {
            float currentAngle = (i * angleStep + rotationAngle) % 360;
            float angleDiff = Math.abs(currentAngle - 90);
            if (angleDiff < minAngleDiff) {
                minAngleDiff = angleDiff;
                selectedItemIndex = i;
            }
        }

        // Draw all items
        for (int i = 0; i < items.size(); i++) {
            float currentAngle = (i * angleStep + rotationAngle) % 360;
            float angleInRadians = (float) Math.toRadians(currentAngle - 90);

            float x = (float) (centerX + radius * Math.cos(angleInRadians));
            float y = (float) (centerY + radius * Math.sin(angleInRadians));

            float scale = (float) (0.5 + 0.5 * Math.sin(Math.toRadians(currentAngle)));
            Paint currentPaint = (i == selectedItemIndex) ? selectedTextPaint : textPaint;
            currentPaint.setTextSize(40f * scale);

            canvas.drawText(items.get(i), x, y, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downTime = event.getEventTime();
                return true;

            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float deltaX = currentX - downX;
                rotationAngle += deltaX * 0.2f;
                invalidate();
                downX = currentX;
                return true;

            case MotionEvent.ACTION_UP:
                // Check if it was a click (short duration, small movement)
                if (event.getEventTime() - downTime < 200 && Math.abs(event.getX() - downX) < 10) {
                    handleItemClick();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void handleItemClick() {
        if (listener != null && selectedItemIndex != -1) {
            listener.onItemClick(selectedItemIndex, items.get(selectedItemIndex));
        }
    }
}
