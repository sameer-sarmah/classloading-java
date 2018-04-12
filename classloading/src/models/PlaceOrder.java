package models;


public class PlaceOrder {
	public Order generateOrder() throws Exception {
		String uuid="12345";
		String name="Adidas T-Shirt";
		Product product=new Product(uuid, name);
		Order order=new Order(uuid);
		order.setProduct(product);
		Thread.sleep(3000);
		System.out.println("Order "+uuid+" created");
		return order;
	}

}
