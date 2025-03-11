package util.text.format.style;

public enum StyleEnum {
	OBFUSCATED("\u00A7k"), // obfuscated
	BOLD("\u00A7l"), // bold
	STRIKETHROUGH("\u00A7m"), // strikethrough
	UNDERLINE("\u00A7n"), // underline
	ITALIC("\u00A7o"), // italic
	RESET("\u00A7r"); // reset
	// il existe un autre format qui permet de renommer les items en non italic par les enclumes
	
	private String style;

	StyleEnum(String style) {
		this.setStyle(style);
	}
	
	@Override
	public String toString() {
		return this.style;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
