package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Parametros {
	//arquivo de parametros editaveis
		public static final String arquivoProp = "C:\\ZEBRA\\ALM\\SERVICE\\PROPERTIES\\local.properties";

		public Properties getPropt() throws IOException {
			Properties props = new Properties();
			FileInputStream file = new FileInputStream(arquivoProp);
			props.load(file);
			return props;
		}
		
		public String cnpj() {
			Properties props = new Properties();
			FileInputStream file = null;
			try {
				file = new FileInputStream(arquivoProp);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				props.load(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return props.getProperty("cnpj");
		}
		public String razaoSocial() {
			Properties props = new Properties();
			FileInputStream file = null;
			try {
				file = new FileInputStream(arquivoProp);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				props.load(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return props.getProperty("razao_social");
		}
		
		public String pathStatus() {
			Properties props = new Properties();
			FileInputStream file = null;
			try {
				file = new FileInputStream(arquivoProp);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				props.load(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return props.getProperty("path_status");
		}

		public String url() {
			Properties props = new Properties();
			FileInputStream file = null;
			try {
				file = new FileInputStream(arquivoProp);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				props.load(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return props.getProperty("url");
		}
		


}
