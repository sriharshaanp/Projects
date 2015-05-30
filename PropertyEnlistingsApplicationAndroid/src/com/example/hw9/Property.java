package com.example.hw9;

public class Property {
	private String propertyDesc;
	private String propertyValue;
	private String image; 
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Property(String propertyDesc, String propertyValue,String image1) {
		super();
		this.propertyDesc = propertyDesc;
		this.propertyValue = propertyValue;
		this.image = image1;
	}

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

}
