package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

public class Validator implements Valores {

	public static final String arquivoTime = "C:\\DBSAC\\ALM\\SERVICE\\PROPERTIES\\system.ini";
	public static final String serverValue = "C:\\DBSAC\\ALM\\SERVICE\\PROPERTIES\\serverValue.ini";
	public static final String arquivoProp = "C:\\DBSAC\\ALM\\SERVICE\\PROPERTIES\\local.properties";
	
	
	public void iniciarSistema() {
		String lido = getTxtServer();
		writeServerValue(lido);
		
		if (getStatusCnpj(lido)) {
			// indica que esta liberado
			resetControlDateFile();
			System.out.println("Abrindo o caixa");
			carregaSistema();
			
		} else {
			// sem internet ou bloqueado
			controlaDatasBloqueio(); 
		}
	}

	@Override
	public String getTxtServer() {
		try {
			Parametros p = new Parametros();
			final String cnpj = p.cnpj();
			final String url = p.url() + cnpj + ".txt";
			String inputLine;
			URL serverFile = new URL(url);
			URLConnection yc = serverFile.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			inputLine = in.readLine();
			in.close();

			String str = inputLine != null ? inputLine : "";
			str = str.trim();
			System.out.println("Retorno server"+str);
			return str;

//
		} catch (Exception e) {
			JOptionPane.showInputDialog("    		Erro de Ativação - Verifique sua conexao com a Internet  		    ");
			
			e.printStackTrace();
			return "false";
		}
	}
	


	@Override
	public void writeServerValue(String lido) {
		// TODO Auto-generated method stub

		File file = new File(serverValue);

		try {

			FileWriter fw = new FileWriter(file, false);
			fw.write(lido);
			fw.flush();
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void resetControlDateFile() {
		// TODO Auto-generated method stub
		
		File file = new File(arquivoTime);
		
		try {
			
			FileWriter fw = new FileWriter(file, false);
			fw.write("0");
			fw.flush();
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void writeControlDateFile() {
		// TODO Auto-generated method stub
		
		File file = new File(arquivoTime);
		long dataAtualMili = ZonedDateTime.now().toInstant().toEpochMilli();
		String now = String.valueOf(dataAtualMili);
		
		try {
			
			FileWriter fw = new FileWriter(file, false);
			fw.write(now);
			fw.flush();
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public Long getDateFile() {
		try {
			File file = new File(arquivoTime);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String inputLine = in.readLine();
			in.close();

			return Long.parseLong(inputLine);
		} catch (Exception e) {
			// TODO: handle exception
			return 0L;
		}
	}

	@Override
	public Long getDateNow() {
		Calendar calendar = Calendar.getInstance();
		long timeMilli = calendar.getTimeInMillis();
		return timeMilli;
	}

	@Override
	public void validateDate() { 
		long dateNow = getDateNow();
		long dateFile = getDateFile();

		System.out.println(dateNow);
		System.out.println(dateFile);
		long diferenca = dateNow - dateFile;
		long dias = diferenca/86400000;
		System.out.println(dias);
		Date dataArquivo = new Date(dateFile);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dataArquivoFormatada = df.format(dataArquivo);

		if(dias>=3) {
			String digitado = JOptionPane.showInputDialog(null,"Informe o código de Ativação Temporária","Falha em "+ dataArquivoFormatada+ "",JOptionPane.ERROR_MESSAGE);
			validaSenha(digitado);
		}else {
			String digitado = JOptionPane.showInputDialog(null,"Em caso de Erro na ativação online o sistema funciona por 3 dias ","Falha em "+ dataArquivoFormatada+ "",JOptionPane.ERROR_MESSAGE);
			entraCortesia(digitado);
		}

	}

	private void validaSenha(String digitado) {
		String senhaDoDia = String.valueOf(senhaDiaMes());
		if(digitado!=null) {
			
		if(digitado.contentEquals(senhaDoDia)) {
			resetControlDateFile();
			carregaSistema();
		}else {
			JOptionPane.showMessageDialog(null,"Código de ativação invalido");
		}
		}
		
	}
	private void entraCortesia(String digitado) {
		String senhaDoDia = String.valueOf(senhaDiaMes());
		if(digitado!=null) {
			
			if(digitado.contentEquals(senhaDoDia)) {
				resetControlDateFile();
			}else {
				JOptionPane.showMessageDialog(null,"Solicite o código de ativação");
			}
		}
		carregaSistema();
		
	}
	
	
	
	public static Integer senhaDiaMes() {

		Date suaData = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(suaData);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		int mes = calendario.get(Calendar.MONTH)+1;

		Integer senhaCalculada = (dia * mes * 999999);

		return senhaCalculada;

	};




	@Override
	public boolean getStatusCnpj() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void controlaDatasBloqueio() {
		 Long valorArquivoData= getDateFile();
		 if(valorArquivoData==0) {
			 System.out.println("zerado ");
			 writeControlDateFile();
		 }else {
			 System.out.println("diferente de zero, vou calcular");
			 validateDate();
			 
		 }
		
	}
	
	@Override
	public boolean getStatusCnpj(String str) {
		if (str.contentEquals("true")) {
			return true;

		} else {
			return false;
		}
	}
	
	public void carregaSistema() {
		try {
			//Runtime.getRuntime().exec("cmd /c \"c:\\zebra\\zpate\\atalhos\\caixa.lnk");
			//String comando = "cmd  \"msm.exe /autoconfig=TALI \"SAC,SAC:SERVIDOR\"";
			//System.out.println(comando);
			
			//Runtime.getRuntime().exec("cmd /c \"C:\\DBSAC\\");
			//Runtime.getRuntime().exec("cmd /c \"c:\\zebra\\zpate\\atalhos\\caixa.lnk");
			//Runtime.getRuntime().exec("cmd /c \"C:\\DBSAC\\msm.exe /autoconfig=TALI 'SAC,SAC:SERVIDOR"");
			
			
			Runtime.getRuntime().exec("cmd /c \"C:\\DBSAC\\msm.exe /autoconfig=TALI \"SAC,SAC:SERVIDOR\"");
		
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Falha ao abrir o caixa");
			e.printStackTrace();
			
		}
	}
	
	

}
