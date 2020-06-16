package in.techware.lataxicustomer.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.util.FontUtil;

/**
 * Created by Jemsheer K D on 07 August, 2017.
 * Package in.techware.lataxi.widgets
 * Project Carrefour
 */

public class CustomButton extends AppCompatButton {


    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            Typeface customFont = null;
            try {
                customFont = FontUtil.selectTypeface(App.getInstance().getString(R.string.font_roboto), Typeface.NORMAL);
                setTypeface(customFont);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView);

        String fontName = FontUtil.getFontFamily(attributeArray.getString(R.styleable.CustomTextView_customFont));

//        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        int textStyle = attributeArray.getInt(R.styleable.CustomTextView_textStyle, 0);
        // if nothing extra was used, fall back to regular android:textStyle parameter
        if (textStyle == 0) {
            textStyle = getTextStyle(attrs);
        }

        Typeface customFont = null;
        try {
            customFont = FontUtil.selectTypeface(fontName, textStyle);
            setTypeface(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        attributeArray.recycle();
    }

    private int getTextStyle(AttributeSet attrs) {
        String style = "0x0";
        try {
            style = attrs.getAttributeValue(ANDROID_SCHEMA, "textStyle");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int textStyle = Typeface.NORMAL;
        if (style != null) {
            switch (style) {
                case "0x0":
                case "normal":
                    textStyle = Typeface.NORMAL;
                    break;
                case "0x1":
                case "bold":
                    textStyle = Typeface.BOLD;
                    break;
                case "0x2":
                case "italic":
                    textStyle = Typeface.ITALIC;
                    break;
                case "0x3":
                case "bold|italic":
                    textStyle = Typeface.BOLD_ITALIC;
                    break;
                default:
                    textStyle = Typeface.NORMAL;
                    break;

            }
        }
        return textStyle;

    }
   /* private static final String TAG = "TextView";
    private static final int DEFAULT_TRIM_LENGTH = 200;
    private String ELLIPSIS = "... Read More";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;


    public CustomTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            TypefaceManager.getInstance().applyTypeface(this, context, null);
            setCustomFont(context, "lato_regular.ttf");
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypefaceManager.getInstance().applyTypeface(this, context, attrs);
            setCustomFont(context, attrs);
//            setCustomFont(context, "lato_regular.ttf");

        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            TypefaceManager.getInstance().applyTypeface(this, context, attrs);
            setCustomFont(context, attrs);
//            setCustomFont(context, "lato_regular.ttf");
        }
    }

    public void setTextStyle(TextStyle textStyle) {
        if (!isInEditMode()) {
            TypefaceManager.getInstance().applyTypeface(this, textStyle);
        }
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();

		*//*		TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        this.trimLength = typedArray.getInt(R.styleable.CustomTextView_trimLength, DEFAULT_TRIM_LENGTH);
		typedArray.recycle();

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				trim = !trim;
				setText();
				requestFocusFromTouch();
			}
		});*//*
    }
    *//*
    private void setText() {
		super.setText(getDisplayableText(), bufferType);
	}

	private CharSequence getDisplayableText() {
		return trim ? trimmedText : originalText;
	}
	 *//*
    *//*	@Override
    public void setText(CharSequence text, BufferType type) {
		originalText = text;
		trimmedText = getTrimmedText(text);
		bufferType = type;
		setText();
	}
	 *//*
    *//*	private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
			return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
		} else {
			return originalText;
		}
	}
	 *//*
    *//*	public CharSequence getOriginalText() {
        return originalText;
	}

	public void setTrimLength(int trimLength) {
		this.trimLength = trimLength;
		trimmedText = getTrimmedText(originalText);
		setText();
	}
	 *//*
    *//*	public int getTrimLength() {
        return trimLength;
	}*//*

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }*/

}
