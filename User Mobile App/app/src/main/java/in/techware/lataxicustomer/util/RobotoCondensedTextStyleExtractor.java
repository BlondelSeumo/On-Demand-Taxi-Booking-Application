package in.techware.lataxicustomer.util;

public class RobotoCondensedTextStyleExtractor extends TextStyleExtractor {

    private static final RobotoCondensedTextStyleExtractor INSTANCE = new RobotoCondensedTextStyleExtractor();

    public static TextStyleExtractor getInstance() {
        return INSTANCE;
    }

    @Override
    public TextStyle[] getTextStyles() {
        return RobotoCondensedTextStyle.values();
    }
}
