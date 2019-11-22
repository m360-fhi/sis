package com.example.sergio.sistz.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.sergio.sistz.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class MoneyEditText extends EditText implements TextWatcher {
    DecimalFormatSymbols decimalFormatSymbols;
    DecimalFormat decimalFormat;
    String moneySymbol;
    String text;
    int numLength = 20;
    int numdecimal = 2;
    boolean hasDecimalPoint = false;
    boolean hasSymbol = true;
    boolean hasDecimal = true;

    public MoneyEditText(Context context) {
        super(context);
        this.initView();
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initParameters(context, attrs);
        this.initView();
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initParameters(context, attrs);
        this.initView();
    }

    private void initParameters(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoneyEditTextAttrs);
        this.numLength = typedArray.getInteger(R.styleable.MoneyEditTextAttrs_numlength, 15);
        this.numdecimal = typedArray.getInteger(R.styleable.MoneyEditTextAttrs_numdecimal, 2);
        this.hasSymbol = typedArray.getBoolean(R.styleable.MoneyEditTextAttrs_symbolshow, true);
        this.numdecimal = this.numdecimal < 0 ? 0 : this.numdecimal;
        this.hasDecimal = this.numdecimal > 0;
    }

    void initView() {
        short inputType;
        if (this.hasDecimal) {
            inputType = 8194;
        } else {
            inputType = 2;
        }

        this.setInputType(inputType);
        this.addTextChangedListener(this);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.numLength)});
        Configuration configuration = this.getResources().getConfiguration();
        this.decimalFormatSymbols = new DecimalFormatSymbols(configuration.locale);
        this.moneySymbol = "TSh";
        //this.moneySymbol = this.hasSymbol ? this.moneySymbol : "TSh";
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingSize(3);
    }

    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
        String content = s.toString();
        if (!TextUtils.isEmpty(content) && !TextUtils.equals(this.text, s)) {
            if (!content.contains(this.moneySymbol + ".") && !TextUtils.equals(s, ".")) {
                content = content.replace(this.moneySymbol, "");
                String decimals = "";
                String integer = "";
                String[] segments = content.split("\\.");
                switch(segments.length) {
                    case 0:
                        integer = content;
                        break;
                    case 1:
                        integer = segments[0];
                        if (content.indexOf(".") > 0) {
                            this.hasDecimalPoint = true;
                        } else {
                            this.hasDecimalPoint = false;
                        }
                        break;
                    case 2:
                        integer = segments[0];
                        decimals = segments[1];
                }

                integer = this.filterStringNum(integer);
                decimals = this.filterStringNum(decimals);
                if (this.hasDecimal && decimals.length() >= this.numdecimal) {
                    decimals = decimals.substring(0, this.numdecimal);
                }

                Long integerNum = this.str2Long(integer);
                //Long decimalsNum = this.str2Long(decimals);
                if (this.hasDecimalPoint && this.hasDecimal) {
                    content = this.moneySymbol + this.formatToCurrency(integerNum) + "." + decimals;
                } else {
                    content = this.moneySymbol + this.formatToCurrency(integerNum);
                }

                this.text = content;
                this.setText(this.text);
                this.setSelection(this.getText().length());
            } else {
                this.setText("");
            }
        }
    }

    private String filterStringNum(String str) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        StringBuilder builder = new StringBuilder();

        while(m.find()) {
            builder.append(m.group());
        }

        return builder == null ? "" : builder.toString();
    }

    private String filterSringFloat(String str) {
        Pattern p = Pattern.compile("\\-*\\d+(\\.\\d+)?");
        Matcher m = p.matcher(str);
        StringBuilder builder = new StringBuilder();

        while(m.find()) {
            builder.append(m.group());
        }

        return builder == null ? "" : builder.toString();
    }

    private Long str2Long(String str) {
        Long num = null;

        try {
            num = Long.valueOf(str);
        } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
        }

        return num;
    }

    private String formatToCurrency(Long lng) {
        if (lng == null) {
            return "";
        } else {
            String str = "";

            try {
                str = this.decimalFormat.format(lng);
            } catch (IllegalFormatException var4) {
                var4.printStackTrace();
            }

            return str;
        }
    }

    @SuppressLint("NewApi")
    public String getMoneyValue() {
        String content = this.getText().toString();
        //Double contentD = 0.0;
        if (TextUtils.isEmpty(content)) {

                return "";

        } else {
            content = this.filterSringFloat(content);
            //contentD = Double.valueOf(content);

                return content;

        }
    }
}
