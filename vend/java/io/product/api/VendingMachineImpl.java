package vend.java.io.product.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import vend.java.io.product.entity.Bucket;
import vend.java.io.product.entity.Coin;
import vend.java.io.product.entity.Inventory;
import vend.java.io.product.entity.Product;
import vend.java.io.product.exceptions.ItemNotSelectedException;
import vend.java.io.product.exceptions.NotAChangeException;
import vend.java.io.product.exceptions.NotFullPaidException;
import vend.java.io.product.exceptions.ProductNotFoundException;

public class VendingMachineImpl implements VendingMachine {

	private Inventory<Product, Integer> itemInventory = new Inventory<Product, Integer>();

	private Inventory<Coin, Integer> cashInventory = new Inventory<Coin, Integer>();

	private Product currentItem;

	private int currentBalance;

	public VendingMachineImpl() {

		initilize();
	}

	public void initilize() {
//		this.itemInventory.putInventory(new Product("COCK", 66), new Integer(10));
		this.itemInventory.putInventory(new Product("COCK", 66), 10);
		this.itemInventory.putInventory(new Product("PEPSI", 66), 10);
		this.itemInventory.putInventory(new Product("LAYS", 35), 10);
		this.cashInventory.putInventory(Coin.Coine, 10);
		this.cashInventory.putInventory(Coin.FIFTYROOPEE, 5);
		this.cashInventory.putInventory(Coin.HUNDREDROOPEE, 5);
		this.cashInventory.putInventory(Coin.TENRUPEE, 10);
		this.cashInventory.putInventory(Coin.TWENTYROOPEE, 50);
		this.cashInventory.putInventory(Coin.FIVEROOPEE, 10);
		this.setCurrentBalance();
	}

	private void setCurrentBalance() {
		if (this.cashInventory.getInventory().size() > 0) {
			List<Integer> cashCoinList = this.cashInventory.getInventory().entrySet().stream()
					.map(e -> e.getKey().getCoinValue() * e.getValue()).collect(Collectors.toList());
			Optional<Integer> currentBalance = cashCoinList.stream().reduce(Integer::sum);
			this.currentBalance = currentBalance.get().intValue();
		}
	}

	@Override
	public int selectItemGetPrice(Product product) throws ProductNotFoundException {
		List<Entry<Product, Integer>> productPrice = this.itemInventory.getInventory().entrySet().stream()
				.filter(e -> e.getKey().getItemName().equals(product.getItemName())).collect(Collectors.toList());

		if (!productPrice.isEmpty()) {
			Product selectedProduct = productPrice.get(0).getKey();
			this.currentItem = selectedProduct;
			return (int) selectedProduct.getItemPrice();
		} else {
			throw new ProductNotFoundException("Product Not avialiable");
		}
	}

	public void displayInsertedCoinValue(Coin... coins) {
		Optional<Integer> insetredCoinValue = Arrays.asList(coins).stream().map(e -> e.getCoinValue())
				.collect(Collectors.toList()).stream().reduce(Integer::sum);

		int insertedValue = insetredCoinValue.get().intValue();
		System.out.println("Inserted coin : " + insertedValue);
	}

	@Override
	public Optional<Bucket> insertCoin(Coin... coins) throws ItemNotSelectedException, NotFullPaidException {

		Bucket bucket = null;
		if (currentItem != null) {
			Optional<Integer> insertedCoinValue = Arrays.asList(coins).stream().map(e -> e.getCoinValue())
					.collect(Collectors.toList()).stream().reduce(Integer::sum);
			int insertedValue = insertedCoinValue.get().intValue();
			if (insertedValue < this.currentItem.getItemPrice()) {
				bucket = new Bucket(new Product("No coins"), Arrays.asList(coins));
			} else {
				try {
					bucket = this.getItemAndChange(insertedValue);
				} catch (NotAChangeException e) {
					e.printStackTrace();
				}

			}

		} else {
			throw new ItemNotSelectedException("Select item");
		}
		Bucket returnbucket = bucket == null ? new Bucket(new Product("Item not found"), Arrays.asList(coins)) : bucket;
		Optional<Bucket> optBucket = Optional.ofNullable(returnbucket);
		return optBucket;
	}

	@Override
	public Bucket getItemAndChange(int insertedValue) throws NotAChangeException {
		this.addCashToInventory(insertedValue);
		this.setCurrentBalance();
		int changedValue = this.getChanged(insertedValue, (int) this.currentItem.getItemPrice());
		this.subtractChangeFromInventory(changedValue);
		this.currentBalance = this.currentBalance - changedValue;
		this.removeItemFromInventory();
//		ArrayList<Coin> coins = new ArrayList<Coin>();

		return new Bucket(this.currentItem, this.convertToCoin(new ArrayList<Coin>(), changedValue));
	}

	// suppoting methods
	public List<Coin> convertToCoin(List<Coin> returnCoinsArray, int changedValue) {
		int reminder = 0;
		if (changedValue >= Coin.HUNDREDROOPEE.getCoinValue()) {
			reminder = changedValue / Coin.HUNDREDROOPEE.getCoinValue();

			if (reminder > 0) {
				for (int i = 0; i <= reminder - 1; i++) {
					returnCoinsArray.add(Coin.HUNDREDROOPEE);
				}
			}

			int test = changedValue - (reminder * Coin.HUNDREDROOPEE.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		else if (changedValue >= Coin.FIFTYROOPEE.getCoinValue()) {
			reminder = changedValue / Coin.FIFTYROOPEE.getCoinValue();
			if (reminder > 0) {
				for (int i = 0; i < reminder - 1; i++) {
					returnCoinsArray.add(Coin.FIFTYROOPEE);
				}
			}
			int test = changedValue - (reminder * Coin.FIFTYROOPEE.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		else if (changedValue >= Coin.TWENTYROOPEE.getCoinValue()) {
			reminder = changedValue / Coin.TWENTYROOPEE.getCoinValue();
			if (reminder > 0) {
				for (int i = 0; i < reminder - 1; i++) {
					returnCoinsArray.add(Coin.TWENTYROOPEE);
				}
			}
			int test = changedValue - (reminder * Coin.TWENTYROOPEE.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		else if (changedValue >= Coin.TENRUPEE.getCoinValue()) {
			reminder = changedValue / Coin.TENRUPEE.getCoinValue();
			if (reminder > 0) {
				for (int i = 0; i < reminder - 1; i++) {
					returnCoinsArray.add(Coin.TENRUPEE);
				}
			}
			int test = changedValue - (reminder * Coin.TENRUPEE.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		else if (changedValue >= Coin.FIVEROOPEE.getCoinValue()) {
			reminder = changedValue / Coin.FIVEROOPEE.getCoinValue();
			if (reminder > 0) {
				for (int i = 0; i < reminder - 1; i++) {
					returnCoinsArray.add(Coin.FIVEROOPEE);
				}
			}
			int test = changedValue - (reminder * Coin.FIVEROOPEE.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		else if (changedValue >= Coin.Coine.getCoinValue()) {
			reminder = changedValue / Coin.Coine.getCoinValue();
			if (reminder > 0) {
				for (int i = 0; i < reminder - 1; i++) {
					returnCoinsArray.add(Coin.Coine);
				}
			}
			int test = changedValue - (reminder * Coin.Coine.getCoinValue());
			if (test != 0) {
				convertToCoin(returnCoinsArray, test);
			}
		}

		return returnCoinsArray;

	}

	public void removeItemFromInventory() {
		int itemCount = this.itemInventory.getInventory().get(currentItem);
		this.itemInventory.getInventory().put(currentItem, itemCount - 1);

	}

	private void subtractChangeFromInventory(int changedValue) {
//		int reminder = 0;
		if (changedValue >= Coin.HUNDREDROOPEE.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.HUNDREDROOPEE, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		} else if (changedValue >= Coin.FIFTYROOPEE.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.FIFTYROOPEE, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		} else if (changedValue >= Coin.TWENTYROOPEE.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.TWENTYROOPEE, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		} else if (changedValue >= Coin.TENRUPEE.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.TENRUPEE, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		} else if (changedValue >= Coin.FIVEROOPEE.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.FIVEROOPEE, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		} else if (changedValue >= Coin.Coine.getCoinValue()) {
			int test = this.putCoinAndDecrement(Coin.Coine, changedValue);
			if (test != 0) {
				subtractChangeFromInventory(test);
			}
		}
	}

	private int putCoinAndDecrement(Coin coin, int changedValue) {
		int reminder = changedValue / coin.getCoinValue();
		int numberOfCoin = this.cashInventory.getInventory().get(coin);
		if (numberOfCoin > reminder) {
			numberOfCoin = numberOfCoin - reminder;
		}
		this.cashInventory.getInventory().put(coin, numberOfCoin);
		int test = changedValue - (reminder * coin.getCoinValue());
		return test;
	}

	private int putCoinAndIncrement(Coin coin, int insertedCoinValue) {
		int reminder = insertedCoinValue / coin.getCoinValue();
		int numberOfCoin = this.cashInventory.getInventory().get(coin);

		this.cashInventory.getInventory().put(coin, numberOfCoin);
		int test = insertedCoinValue - (reminder * coin.getCoinValue());
		return test;
	}

	private void addCashToInventory(int insertedCoinValue) {
		if (insertedCoinValue >= Coin.HUNDREDROOPEE.getCoinValue()) {
			int test = this.putCoinAndIncrement(Coin.HUNDREDROOPEE, insertedCoinValue);
			if (test != 0) {
				addCashToInventory(test);
			}
		} else if (insertedCoinValue >= Coin.FIFTYROOPEE.getCoinValue()) {
			int test = this.putCoinAndIncrement(Coin.FIFTYROOPEE, insertedCoinValue);
			if (test != 0) {
				addCashToInventory(test);
			}
		} else if (insertedCoinValue >= Coin.TWENTYROOPEE.getCoinValue()) {
			int test = this.putCoinAndIncrement(Coin.TWENTYROOPEE, insertedCoinValue);
			if (test != 0) {
				addCashToInventory(test);
			}
		} else if (insertedCoinValue >= Coin.TENRUPEE.getCoinValue()) {
			int test = this.putCoinAndIncrement(Coin.TENRUPEE, insertedCoinValue);
			if (test != 0) {
				addCashToInventory(test);
			}
		} else if (insertedCoinValue >= Coin.Coine.getCoinValue()) {
			int test = this.putCoinAndIncrement(Coin.Coine, insertedCoinValue);
			if (test != 0) {
				addCashToInventory(test);
			}
		}
	}

	public int getChanged(int insertedValue, int itemPrice) {
		if (insertedValue > itemPrice) {
			return insertedValue - itemPrice;
		} else {
			return itemPrice - insertedValue;
		}
	}

	public void reset() {
		this.currentItem = null;
	}

}
