package com.emlook.hospital;

import android.content.Context;
import android.graphics.Typeface;

public class CustomFontsLoader {
    public static final int NOTOSANSKR_BLACK = 0;
    public static final int NOTOSANSKR_BOLD = 1;
    public static final int NOTOSANSKR_LIGHT = 2;
    public static final int NOTOSANSKR_MEDIUM = 3;
    public static final int NOTOSANSKR_REGULAR = 4;
    public static final int NOTOSANSKR_THIN = 5;

    private static final int NUM_OF_CUSTOM_FONT = 6;
    private static boolean fontsLoaded = false;

    private static Typeface[] fonts = new Typeface[6];

    private static String[] fontPath = {
            "fonts/NotoSansKR-Black.otf",
            "fonts/NotoSansKR-Bold.otf",
            "fonts/NotoSansKR-Light.otf",
            "fonts/NotoSansKR-Medium.otf",
            "fonts/NotoSansKR-Regular.otf",
            "fonts/NotoSansKR-Thin.otf"
    };

    public static Typeface getTypeface(Context context, int fontIdentifier){
        if(!fontsLoaded){
            loadFonts(context);
        }
        return fonts[fontIdentifier];
    }

    private static void loadFonts(Context context){
        for(int i = 0; i < NUM_OF_CUSTOM_FONT; i++){
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
        }
        fontsLoaded = true;
    }
}
