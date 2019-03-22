package com.example.clockdigi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class NumericsSet extends View {


    Integer[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Integer> digits = Arrays.asList(num);
    int value;
    Paint paint;


    public NumericsSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        basicCall();
    }


    private void basicCall() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); // this is an empty call  no diff if yu make a call or not
        paint.setStrokeWidth(getWidth() / 8);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float width = paint.getStrokeWidth();
        switch (value) {
            case 8:
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(width / 2, getHeight() / 2, 0, getHeight() - width / 2, paint);  //left bottom checked
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            case 0: //checked
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(width / 2, getHeight() / 2, 0, getHeight() - width / 2, paint);  //left bottom checked
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom
                break;
            case 1:
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                break;
            case 2:
                canvas.drawLine(width / 2, getHeight() / 2, 0, getHeight() - width / 2, paint);  //left bottom checked
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            case 3:
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            case 4:
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                break;
            case 5:
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            case 6:
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(width / 2, getHeight() / 2, 0, getHeight() - width / 2, paint);  //left bottom checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            case 7:
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                break;
            case 9:
                canvas.drawLine(0, 0, width / 2, getHeight() / 2, paint);  //left top checked
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight() / 2, paint); // right top checked
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), getHeight() - width / 2, paint); // right bottom checked
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //top checked
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle  checked
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom checked
                break;
            default:
                canvas.drawLine(width / 2, 0, width / 2, getHeight(), paint); //top
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);//right bottom
                canvas.drawLine(getWidth(), getHeight() / 2, getWidth(), 0, paint);//right top
                canvas.drawLine(0, width / 2, getWidth(), 0, paint); //left top
                canvas.drawLine(getHeight() / 2, 0, getWidth(), 0, paint); //left bottom
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint); //middle
                canvas.drawLine(0, getHeight() - width / 2, getWidth(), getHeight() - width / 2, paint); //bottom
                break;
        }
    }

    public void setDigit(int digit) {
        this.value = digit;
        if (digits.contains(digit)) {
            invalidate();
        }
    }

     public void setColor(int color_choice){
        paint.setColor(color_choice);
    }

}





