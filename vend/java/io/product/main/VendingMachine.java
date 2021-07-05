package vend.java.io.product.main;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import vend.java.io.product.api.VendingMachineImpl;
import vend.java.io.product.entity.Bucket;
import vend.java.io.product.entity.Coin;
import vend.java.io.product.entity.Product;

public class VendingMachine {
	public static void main(String[] args) {
		VendingMachineImpl vendingMachine = new VendingMachineImpl();
		try {
			Product product = new Product("COCK");
			int itemPrice = vendingMachine.selectItemGetPrice(product);
			System.out.println("Selected Item:" + product.getItemName());
			System.out.println("Selected Item Price:" + itemPrice);
			if (itemPrice != 0) {
				Coin insertedCoin[] = new Coin[1];
				insertedCoin[0] = Coin.HUNDREDROOPEE;
//insertedCoin[1]=Coin.TENRUPEE;
//insertedCoin[2]=Coin.TWENTYROOPEE;
//insertedCoin[3]=Coin.FIFTYROOPEE;
//insertedCoin[4]=Coin.Coine;
				Optional<Bucket> bucket = vendingMachine.insertCoin(insertedCoin);
				vendingMachine.displayInsertedCoinValue(insertedCoin);
				if (bucket.isPresent()) {
					Bucket itemBucket = bucket.get();
					if (itemBucket.getProduct() != null) {
						System.out.println("Return Item: " + itemBucket.getProduct().getItemName());
						System.out.println("Item Price: " + itemBucket.getProduct().getItemPrice());
						displayInsertedCoinValue(itemBucket.getCoin());
					}
				}
			}
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void displayInsertedCoinValue(List<Coin> list) {
		Optional<Integer> insertedCoinValue = list.stream().map(e -> e.getCoinValue()).collect(Collectors.toList())
				.stream().reduce(Integer::sum);
		int insertedValue = insertedCoinValue.get().intValue();
		System.out.println("Return Coin value: " + insertedValue);
	}
}
