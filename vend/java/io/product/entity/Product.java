package vend.java.io.product.entity;

public class Product {

	private String itemName;
	private long itemPrice;

	public Product(String itemName, long itemPrice) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public Product() {

	}
	
	


	public Product(String itemName) {
		super();
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(long itemPrice) {
		this.itemPrice = itemPrice;
	}

}
