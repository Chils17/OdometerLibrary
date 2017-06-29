package com.androidchils.odometer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
    private int odo_text_color;
    private float textSize;
    private String read, fontName;
    private int odo_edge_color;
    private int odo_center_color;

    public Odometer(Context context, Builder builder) {
        super(context);
        initViews(context, builder);
    }

    public Odometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public Odometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, Builder builder) {
        setOrientation(HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker, this, true);

        llParent = (LinearLayout) findViewById(R.id.llParent);

        createNumberPicker(context, builder);
    }

    private void initViews(Context context, AttributeSet attrs) {

        setOrientation(HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker, this, true);

        llParent = (LinearLayout) findViewById(R.id.llParent);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Odometer);

        try {
            fontName = typedArray.getString(R.styleable.Odometer_np_font);

            textSize = typedArray.getDimension(R.styleable.Odometer_np_textSize, spToPx(18f));

            odo_text_color = typedArray.getColor(R.styleable.Odometer_np_textColor, ContextCompat.getColor(context, R.color.white));

            slot = typedArray.getInt(R.styleable.Odometer_np_slots, 6);
            read = typedArray.getString(R.styleable.Odometer_np_reading);

            odo_edge_color = typedArray.getResourceId(R.styleable.Odometer_np_edgeColor, R.color.white);
            odo_center_color = typedArray.getResourceId(R.styleable.Odometer_np_centerColor, R.color.black);

        } finally {
            typedArray.recycle();
        }

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

    }

    private GradientDrawable makeGradientDrawable(GradientDrawable.Orientation orientation,
                                                  int startColor, int centerColor, int endColor) {
        int[] colors = new int[]{startColor, centerColor, endColor};
        GradientDrawable gd = new GradientDrawable(orientation, colors);
        gd.setCornerRadius(8);
        gd.setGradientRadius(90);
        return gd;
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private void createDynamicNumberPicker(Context context) {

        for (int i = 1; i <= slot; i++) {
            NumberPicker numberPicker = new NumberPicker(context);
            LayoutParams lp = new LayoutParams(90, LayoutParams.WRAP_CONTENT);
            lp.setMargins(2, 0, 2, 0);
            lp.gravity = Gravity.CENTER;
            numberPicker.setLayoutParams(lp);

            setDividerColor(numberPicker, Color.TRANSPARENT);

            setNumberPickerTextColor(numberPicker, odo_text_color, fontName, textSize);

            if (odo_edge_color!=0 && odo_center_color!=0) {
                numberPicker.setBackgroundDrawable(makeGradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        ContextCompat.getColor(context, odo_edge_color),
                        ContextCompat.getColor(context, odo_center_color),
                        ContextCompat.getColor(context, odo_edge_color)));
            }

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


    private void createNumberPicker(Context context, Builder builder) {

        for (int i = 1; i <= builder.slot; i++) {
            NumberPicker numberPicker = new NumberPicker(context);
            LayoutParams lp = new LayoutParams(90, LayoutParams.WRAP_CONTENT);
            lp.setMargins(2, 0, 2, 0);
            lp.gravity = Gravity.CENTER;
            numberPicker.setLayoutParams(lp);

            setDividerColor(numberPicker, Color.TRANSPARENT);

            setNumberPickerTextColor(numberPicker, builder.odo_text_color, builder.font, spToPx(builder.textSize));

            numberPicker.setBackgroundDrawable(makeGradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    builder.odo_edge_color, builder.odo_center_color, builder.odo_edge_color));

            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setId(i - 1);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(9);

            numberPicker.setWrapSelectorWheel(true);

            int read_val = Character.getNumericValue(builder.reading.charAt(i - 1));
            numberPicker.setValue(read_val);

            llParent.addView(numberPicker);
            Log.e("add", "add" + i);
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

                    if (!TextUtils.isEmpty(fontName))
                        ((Paint) selectorWheelPaintField.get(numberPicker)).setTypeface(Typeface.createFromAsset(getResources().getAssets(), fontName));

                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTextSize(textSize);

                    ((EditText) child).setTextColor(color);

                    if (!TextUtils.isEmpty(fontName))
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

    public void setDividerColor(NumberPicker picker, int color) {

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


    public String getFinalOdometerValue() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < llParent.getChildCount(); i++) {

            NumberPicker localNumberPicker = (NumberPicker) llParent.getChildAt(i);
            localNumberPicker.getValue();

            stringBuilder.append(localNumberPicker.getValue());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }


    public static class Builder {
        // default values
        private Context context;
        private String font = "";
        private float textSize = 0;
        private int odo_text_color = 0;
        private int odo_edge_color = 0;
        private int odo_center_color = 0;
        private int slot;
        private String reading;


        public Builder(Context context) {
            this.context = context;
            odo_text_color = ContextCompat.getColor(context, R.color.white);
            odo_edge_color = ContextCompat.getColor(context, R.color.startColor);
            odo_center_color = ContextCompat.getColor(context, R.color.centerColor);
            textSize = spToPx(14);
            slot = 4;
            reading = "0000";
        }

        public Builder background(int odo_edge_color, int odo_center_color) {
            this.odo_edge_color = odo_edge_color;
            this.odo_center_color = odo_center_color;
            return this;
        }


        public Builder textColor(int odo_text_color) {
            this.odo_text_color = odo_text_color;
            return this;
        }


        public Builder font(String font) {
            this.font = font;
            return this;
        }

        public Builder textSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder slot(int slot) {
            this.slot = slot;
            return this;
        }

        public Builder reading(String reading) {
            this.reading = reading;
            return this;
        }


        public Odometer build() {
            return new Odometer(context, this);
        }


        private float spToPx(float sp) {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        }

    }


}
