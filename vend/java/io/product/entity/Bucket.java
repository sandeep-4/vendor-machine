package vend.java.io.product.entity;

import java.util.List;

public class Bucket {

	private Product product;
	
	private List<Coin> coin;
	
	
	
	public Bucket() {
		
	}
	
	public Bucket( List<Coin> coin) {
		this.coin = coin;
	}

	public Bucket(Product product, List<Coin> coin) {
		super();
		this.product = product;
		this.coin = coin;
	}

	public Bucket(Product product) {
		this.product = product;
		
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Coin> getCoin() {
		return coin;
	}

	public void setCoin(List<Coin> coin) {
		this.coin = coin;
	}
	
	
}
