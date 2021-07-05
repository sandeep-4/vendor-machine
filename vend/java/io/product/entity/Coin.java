package vend.java.io.product.entity;

public enum Coin {

	NOCOINE(0), Coine(1),FIVEROOPEE(5),TENRUPEE(10),TWENTYROOPEE(20),FIFTYROOPEE(50),HUNDREDROOPEE(100);
	
	private int coinValue;
	
	

	private Coin(int coinValue) {
		this.coinValue = coinValue;
	}

	public int getCoinValue() {
		return coinValue;
	}

	public void setCoinValue(int coinValue) {
		this.coinValue = coinValue;
	}
	
	
	
	
}
