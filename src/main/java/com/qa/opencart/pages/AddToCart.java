package com.qa.opencart.pages;

public class AddToCart {

	String productToAdd = "iphone 13";
	
	public void addProduct() {
		System.out.println("Product Added"+productToAdd);
	}
	
	public void addDeliveryAddress() {
		System.out.println("Delivery Address");
	}
	public void addPaymentMethod() {
		System.out.println("Payment Method");
	}
}
