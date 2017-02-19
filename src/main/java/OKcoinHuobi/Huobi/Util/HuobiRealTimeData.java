package OKcoinHuobi.Huobi.Util;

public class HuobiRealTimeData {
	float last_price;
	public float total;
	public float net;
	public float free_cny;
	public float free_btc;
	public float free_ltc;
	public float frozen_cny;
	public float frozen_btc;
	public float frozen_ltc;
	
	public float getLast_price() {
		return last_price;
	}

	public void setLast_price(float last_price) {
		this.last_price = last_price;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getNet() {
		return net;
	}

	public void setNet(float net) {
		this.net = net;
	}

	public float getFree_cny() {
		return free_cny;
	}

	public void setFree_cny(float free_cny) {
		this.free_cny = free_cny;
	}

	public float getFree_btc() {
		return free_btc;
	}

	public void setFree_btc(float free_btc) {
		this.free_btc = free_btc;
	}

	public float getFree_ltc() {
		return free_ltc;
	}

	public void setFree_ltc(float free_ltc) {
		this.free_ltc = free_ltc;
	}

	public float getFrozen_cny() {
		return frozen_cny;
	}

	public void setFrozen_cny(float frozen_cny) {
		this.frozen_cny = frozen_cny;
	}

	public float getFrozen_btc() {
		return frozen_btc;
	}

	public void setFrozen_btc(float frozen_btc) {
		this.frozen_btc = frozen_btc;
	}

	public float getFrozen_ltc() {
		return frozen_ltc;
	}

	public void setFrozen_ltc(float frozen_ltc) {
		this.frozen_ltc = frozen_ltc;
	}

}
