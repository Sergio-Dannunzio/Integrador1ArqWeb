package entities;

public class Factura {
	private int idBill,idClient;
	
	public Factura(int idBill, int idClient) {
		this.idBill = idBill;
		this.idClient = idClient;
	}
	
	public int getIdBill() {
		return idBill;
	}

	public int getIdClient() {
		return idClient;
	}
	    
}
