package vend.java.io.product.api;

import java.util.Optional;

import vend.java.io.product.entity.Bucket;
import vend.java.io.product.entity.Coin;
import vend.java.io.product.entity.Product;
import vend.java.io.product.exceptions.ItemNotSelectedException;
import vend.java.io.product.exceptions.NotAChangeException;
import vend.java.io.product.exceptions.NotFullPaidException;
import vend.java.io.product.exceptions.ProductNotFoundException;

public interface VendingMachine {

	public int selectItemGetPrice(Product product) throws ProductNotFoundException;
	
	public Optional<Bucket> insertCoin(Coin ...coins) throws ItemNotSelectedException,NotFullPaidException;
	
	public Bucket getItemAndChange(int coinValue) throws NotAChangeException;
}
