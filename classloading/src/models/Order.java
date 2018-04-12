package models;

public class Order {
  
	private String id;
	private Product product;

	
	public Order(String id) {
		super();
		this.id = id;
	}	
	
	public String getId() {
		return id;
	}



	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}



	
	
	
}
