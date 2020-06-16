package in.techware.lataxidriverapp.util;

public class RobotoTextStyleExtractor extends TextStyleExtractor {

    private static final RobotoTextStyleExtractor INSTANCE = new RobotoTextStyleExtractor();

    public static TextStyleExtractor getInstance() {
        return INSTANCE;
    }

    @Override
    public TextStyle[] getTextStyles() {
        return RobotoTextStyle.values();
    }
}
