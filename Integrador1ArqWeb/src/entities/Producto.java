package entities;

public class Producto {

    private int idProduct;
    private String name;
    private float value;
    
    public Producto(int idProduct, String name, float value) {
    	this.idProduct = idProduct;
    	this.name = name;
    	this.value = value;
    }
    
	public int getIdProduct() {
		return idProduct;
	}
	
	public String getName() {
		return name;
	}
	
	public float getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Product [idProduct=" + idProduct + ", name=" + name + ", value=" + value + "]";
	}
}
