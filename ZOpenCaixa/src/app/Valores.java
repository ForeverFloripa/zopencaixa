package app;

public interface Valores {
	
	public String getTxtServer();
	public boolean getStatusCnpj();
	public Long getDateFile();
	public Long getDateNow();
	public void validateDate();
	public boolean getStatusCnpj(String str);
	void writeServerValue(String lido);
	void resetControlDateFile();
	void writeControlDateFile();
	void controlaDatasBloqueio();
	
	
	

}
