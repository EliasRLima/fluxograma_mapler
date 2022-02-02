package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import diagrama.FMX;
import javafx.stage.FileChooser;

public class ArquivoService {

	private File arquivo;
	private final FileChooser fileChooser;
	private boolean isArquivoAlterado = false;
	private static ArquivoService instance;

	private ArquivoService() {
		this.fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo fluxograma (*.mapler)", "*.mapler");
		fileChooser.getExtensionFilters().add(extFilter);
	}

	public static ArquivoService getInstance() {
		if (instance == null)
			instance = new ArquivoService();
		return instance;
	}
	
	public boolean isArquivoAlterado() {
		return isArquivoAlterado;
	}

	public void setArquivoAlterado() {
		this.isArquivoAlterado = true;
	}

	public boolean abrir() {		
		if(!checkAlteracoesNaoSalvas()) return false;
		fileChooser.setTitle("Abrir arquivo");
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			arquivo = file;
			isArquivoAlterado = false;
			return true;
		}
		return false;
	}

	public void salvar(FMX fluxograma) {
		if (arquivo != null) {
			salvarArquivo(fluxograma.getString());
		} else {
			salvarComo(fluxograma.getString());
		}
	}

	public void salvarComo(String conteudo) {
		fileChooser.setTitle("Salvar arquivo");
		arquivo = fileChooser.showSaveDialog(null);
		if(arquivo == null)return;
		salvarArquivo(conteudo);
	}

	public void fechar() {
		this.arquivo = null;
		isArquivoAlterado = false;
	}
	
	public String getConteudo() {
		return leArquivo();
	}

	private void salvarArquivo(String conteudo) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(arquivo);
			writer.write(conteudo);
			AlertaService.showAviso("O arquivo foi salvo!");
			isArquivoAlterado = false;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private String leArquivo() {
		if(arquivo == null) return null;
		String content = null;
		FileReader reader = null;
		try {
			reader = new FileReader(arquivo);
			char[] chars = new char[(int) arquivo.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content;
	}
	
	public boolean checkAlteracoesNaoSalvas() {
		if(this.isArquivoAlterado) {
			int resp = AlertaService.showConfirm("Há alterações não salvas no arquivo. Deseja sair sem salvar?");
			if(resp != 1) {
				return false;
			}			
		}
		return true;
	}
}
