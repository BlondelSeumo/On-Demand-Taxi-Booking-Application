package in.techware.lataxidriverapp.util;

import android.graphics.Typeface;

import java.util.ArrayList;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.TypefaceCache;


/**
 * Created by Jemsheer K D on 06 September, 2018.
 * Package in.techware.lataxi.util
 * Project LaTaxi
 */
public class FontUtil {

    private static FontUtil instance = new FontUtil();

    private final ArrayList<String> fontFamilyList = new ArrayList<>();
    private final ArrayList<String> fontList = new ArrayList<>();

    public static FontUtil getInstance() {
        if (instance == null) {
            instance = new FontUtil();
        }
        return instance;
    }

    private FontUtil() {
        fontFamilyList.add(App.getInstance().getString(R.string.font_roboto));
        fontFamilyList.add(App.getInstance().getString(R.string.font_lato));
    }

    public static String getFontFamily(String fontName) {

        if (fontName != null && !fontName.equalsIgnoreCase("")) {
            for (String fontFamily : getInstance().fontFamilyList) {
                if (fontFamily.equalsIgnoreCase(fontName))
                    return fontFamily;
            }
            fontName = App.getInstance().getString(R.string.font_roboto);
        } else {
            fontName = App.getInstance().getString(R.string.font_roboto);
        }
        return fontName;
    }

    public static Typeface selectTypeface(String fontName, int textStyle) throws Exception {
        if (fontName.contentEquals(App.getInstance().getString(R.string.font_roboto))) {
            switch (textStyle) {
                case Typeface.BOLD: // bold
                    return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_regular));
                case Typeface.ITALIC: // italic
                    return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_italic));
                case Typeface.BOLD_ITALIC: // bold italic
                    return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_bold_italic));
                case Typeface.NORMAL: // regular
                    return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_regular));
                default:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_regular));
            }
        } /*else if (fontName.contentEquals(context.getString(R.string.font_lato))) {
            switch (textStyle) {
                case Typeface.BOLD:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance(), "Lato-Bold.ttf");
                case Typeface.ITALIC:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance(), "Lato-Italic.ttf");
                case Typeface.BOLD_ITALIC:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance(), "Lato-BoldItalic.ttf");
                case Typeface.NORMAL:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance(), "Lato-Regular.ttf");
                default:
                    return TypefaceCache.getInstance().getTypeface(App.getInstance(), "Lato-Regular.ttf");
            }
        }*/ else {
            return TypefaceCache.getInstance().getTypeface(App.getInstance().getString(R.string.font_roboto_regular));
        }
    }
}
