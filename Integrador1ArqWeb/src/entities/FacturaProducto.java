package entities;

public class FacturaProducto {
	
	private int idBill,idProduct, quantity;

	public FacturaProducto(int idBill, int idProduct, int quantity) {
		this.idBill = idBill;
		this.idProduct = idProduct;
		this.quantity = quantity;
	}

	public int getIdBill() {
		return idBill;
	}

	public int getIdProduct() {
		return idProduct;
	}

	public int getQuantity() {
		return quantity;
	}
}
