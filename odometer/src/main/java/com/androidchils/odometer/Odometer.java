package com.androidchils.odometer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by chiragpatel on 15-05-2017.
 */

public class Odometer extends LinearLayout {

    private LinearLayout llParent;
    private int slot;
    private int odo_bg_color;
    private int odo_bg_color2 = -1;
    private int bg_color;
    private int odo_text_color;
    private float textSize;
    private String read, fontName;
    private String odo_bg_color3;

    public Odometer(Context context) {
        super(context);
    }

    public Odometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public Odometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs)
    {

        setOrientation(HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker, this, true);

        llParent = (LinearLayout) findViewById(R.id.llParent);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Odometer);

        try {
            fontName = typedArray.getString(R.styleable.Odometer_np_font);

            textSize = typedArray.getDimension(R.styleable.Odometer_np_textSize, spToPx(18f));

            bg_color = typedArray.getColor(R.styleable.Odometer_np_background, ContextCompat.getColor(context, R.color.color_transparent));

            /*background = typedArray.getResourceId(R.styleable.Odometer_main_background, R.drawable.gradient);
            background2 = typedArray.getColor(R.styleable.Odometer_main_background, ContextCompat.getColor(context, R.color.black));
            background1 = typedArray.getString(R.styleable.Odometer_main_background);*/

            odo_bg_color = typedArray.getResourceId(R.styleable.Odometer_np_backgroundColor, R.drawable.gradient);
            //odo_bg_color2 = typedArray.getColor(R.styleable.Odometer_odo_bg_color, ContextCompat.getColor(context, R.color.black));
            odo_bg_color3 = typedArray.getString(R.styleable.Odometer_np_backgroundColor);

            odo_text_color = typedArray.getColor(R.styleable.Odometer_np_textColor, ContextCompat.getColor(context, R.color.white));
            slot = typedArray.getInt(R.styleable.Odometer_np_slots, 6);
            read = typedArray.getString(R.styleable.Odometer_np_reading);

        } finally {
            typedArray.recycle();
        }

        llParent.setBackgroundColor(bg_color);


        if (TextUtils.isEmpty(fontName)) {
            fontName = "Lato-Regular.ttf";
        }


        if (TextUtils.isEmpty(read)) {
            read = "000000";
        }


        if (TextUtils.isEmpty(read) || read.length() != slot) {
            TextView textView = new TextView(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            textView.setLayoutParams(lp);
            textView.setTextColor(ContextCompat.getColor(context, R.color.black));
            textView.setText("Invalid Values");
            llParent.addView(textView);

        } else {
            createDynamicNumberPicker(context);
        }

        /*final int N = array.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Odometer_slots:
                    slot = array.getInt(attr, 0);
                    setNumberPicker(context);
                    break;

                case R.styleable.Odometer_reading:
                    read = array.getString(attr);
                    setReading(context);
                    break;
            }
        }*/

    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private void createDynamicNumberPicker(Context context) {

        for (int i = 1; i <= slot; i++) {
            NumberPicker numberPicker = new NumberPicker(context);
            LayoutParams lp = new LayoutParams(120, LayoutParams.WRAP_CONTENT);
            lp.setMargins(4, 0, 4, 0);
            lp.gravity = Gravity.CENTER;
            numberPicker.setLayoutParams(lp);

            setDividerColor(numberPicker, Color.TRANSPARENT);

            setNumberPickerTextColor(numberPicker, odo_text_color, fontName, textSize);

           /* if (!TextUtils.isEmpty(odo_bg_color3)) {
                numberPicker.setBackgroundColor(Color.parseColor(odo_bg_color3));
            } else if (odo_bg_color != null) {
                numberPicker.setBackgroundDrawable(odo_bg_color);
            } else if (odo_bg_color2 == -1) {
                numberPicker.setBackgroundColor(odo_bg_color2);
            }*/

            numberPicker.setBackgroundResource(odo_bg_color);

            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setId(i - 1);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(9);

            numberPicker.setWrapSelectorWheel(true);

            int read_val = Character.getNumericValue(read.charAt(i - 1));
            numberPicker.setValue(read_val);

            llParent.addView(numberPicker);
        }

    }

    public void setNumberPickerTextColor(NumberPicker numberPicker, int color, String fontName, float textSize) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTypeface(Typeface.createFromAsset(getResources().getAssets(), fontName));
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTextSize(textSize);
                    ((EditText) child).setTextColor(color);
                    ((EditText) child).setTypeface(Typeface.createFromAsset(getResources().getAssets(), fontName));
                    ((EditText) child).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                    numberPicker.invalidate();
                } catch (NoSuchFieldException e) {
                    Log.w("NumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Log.w("NumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Log.w("NumberPickerTextColor", e);
                }
            }
        }
    }

    private void setDividerColor(NumberPicker picker, int color) {

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public String getFinalOdoMiterValue() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < llParent.getChildCount(); i++) {

            NumberPicker localNumberPicker = (NumberPicker) llParent.getChildAt(i);
            localNumberPicker.getValue();

            stringBuilder.append(localNumberPicker.getValue());
            stringBuilder.append(" ");

        }

        return stringBuilder.toString();
    }

}
