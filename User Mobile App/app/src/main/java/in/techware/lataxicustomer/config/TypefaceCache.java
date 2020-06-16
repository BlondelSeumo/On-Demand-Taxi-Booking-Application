package in.techware.lataxicustomer.config;

import android.graphics.Typeface;

import java.util.HashMap;

import in.techware.lataxicustomer.app.App;

/**
 * Created by Jemsheer K D on 18 February, 2017.
 * Package in.techware.gocourt.conf
 * Project GoCourt
 */

public class TypefaceCache {

    private static TypefaceCache instance = new TypefaceCache();

    private final HashMap<String, Typeface> typefaceList = new HashMap<>();

    public static TypefaceCache getInstance() {
        return instance;
    }

    private TypefaceCache() {
    }

    public Typeface getTypeface(String asset) throws Exception {

        synchronized (typefaceList) {
            Typeface typeface;
            if (!typefaceList.isEmpty() && typefaceList.containsKey(asset)) {
                typeface = typefaceList.get(asset);
            } else {
                typeface = Typeface.createFromAsset(App.getInstance().getAssets(), asset);
                typefaceList.put(asset, typeface);
            }
            return typeface;
        }
    }
}
