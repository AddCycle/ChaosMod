package util.guide.model.block;

public class GuideBlock {
	private String type;
	private String name;
	private String description;
	private String texture;
	
	public GuideBlock(String type, String name, String description, String texture) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.texture = texture;
	}

	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTexture() {
		return this.texture;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
}
