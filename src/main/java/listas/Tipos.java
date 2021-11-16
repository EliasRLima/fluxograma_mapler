package listas;

public enum Tipos {
	
	DECISAO(1), //somente decisao aceita mais de uma ligacao //limite de tres ligacoes
	ENTRADA(0),
	SAIDA(0),
	FIM(0),
	INICIO(0),
	PROCESSAMENTO(0);
	
	private boolean decisao;
	
	private Tipos(int num) {
		this.decisao = num > 0 ? true : false;
	}
	
	public boolean isDecisao() {
		return this.decisao;
	}
}
