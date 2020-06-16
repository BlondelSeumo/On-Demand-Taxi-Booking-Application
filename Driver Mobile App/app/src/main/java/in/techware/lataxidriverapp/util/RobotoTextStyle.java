package in.techware.lataxidriverapp.util;


public enum RobotoTextStyle implements TextStyle {
	
	NORMAL("normal", "Roboto-Regular.ttf"),
	LIGHT("light", "Roboto-Light.ttf"),
	BOLD("bold", "Roboto-Bold.ttf"),
	BOLD_ITALIC("boldItalic", "Roboto-BoldItalic.ttf"),
	ITALIC("italic", "Roboto-Italic.ttf"),
	REGULAR("regular", "Roboto-Regular.ttf"),
	MONOSPACE("monospace", "Roboto-Light.ttf"),
	SERIF("serif", "Roboto-Regular.ttf"),
	SANSERIF("sansSerif", "Roboto-Regular.ttf"),
	SANS("sans", "Roboto-Regular.ttf");
	
	private String mName;
	private String mFontName;

	RobotoTextStyle(String name, String fontName) {
		mName = name;
		mFontName = fontName;
	}

	@Override
	public String getFontName() {
		return mFontName;
	}

	@Override
	public String getName() {
		return mName;
	}
}
