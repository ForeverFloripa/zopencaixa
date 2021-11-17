package app;

public class Main {

	public static void main(String[] args) throws Exception {
		buscaStatus();
	}

	private static void buscaStatus() {
		Validator val = new Validator();
		val.iniciarSistema();
	}

}
