package util;

public enum Colors {
	WHITE(255, 255, 255),
	BLACK(0, 0, 0),
	RED(255, 0, 0),
	GREEN(0, 255, 0),
	BLUE(0, 0, 255),
	YELLOW(255, 255, 0),
	ORANGE(255, 165, 0),
	PURPLE(128, 0, 128),
	CYAN(0, 255, 255),
	MAGENTA(255, 0, 255),
	BROWN(139, 69, 19);

	private final int r, g, b;
	private final int rgb;
	private final float[] rgbFloat;

	Colors(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.rgb = (r << 16) | (g << 8) | b;
		this.rgbFloat = new float[]{r / 255f, g / 255f, b / 255f};
	}

	public int getRGB() {
		return rgb;
	}

	public float[] getRGBFloat() {
		return rgbFloat;
	}

	public float getR() { return r / 255f; }
	public float getG() { return g / 255f; }
	public float getB() { return b / 255f; }
}