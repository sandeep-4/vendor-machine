package vend.java.io.product.entity;

import java.util.HashMap;

public class Inventory<T, T2> {

	private HashMap<T, T2> inventory = new HashMap<T, T2>();

	public Inventory(HashMap<T, T2> inventory) {
		super();
		this.inventory = inventory;
	}

	public Inventory() {
		super();
	}

	public boolean hashItem(Product product) {

		return true;
	}

	public HashMap<T, T2> getInventory() {
		return inventory;
	}

	public void setInventory(HashMap<T, T2> inventory) {
		this.inventory = inventory;
	}
	
	public void putInventory(T t,T2 t2) {
		this.inventory.put(t, t2);
	}

}
