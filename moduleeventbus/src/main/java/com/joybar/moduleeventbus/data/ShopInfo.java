package com.joybar.moduleeventbus.data;

/**
 * Created by joybar on 2017/11/28.
 */

public class ShopInfo {
	private String name;
	private String address;

	public ShopInfo(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ShopInfo{" + "name='" + name + '\'' + ", address='" + address + '\'' + '}';
	}
}
