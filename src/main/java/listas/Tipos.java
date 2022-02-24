package listas;

public enum Tipos {
	
	/*Todos os tipos podem receber varias ligaçoes, porem somente decisoes podem apontar para varios*/
	
	DECISAO(1), //somente decisao aceita apontar mais de uma ligacao //limite de apontar para duas
	ENTRADA(2),
	SAIDA(3),
	FIM(4),
	INICIO(5),
	PROCESSAMENTO(6);
	
	private boolean decisao;
	private int num;
	
	private Tipos(int num) {
		this.decisao = (num == 1 ? true : false);
		this.num = num;
	}
	
	public boolean isDecisao() {
		return this.decisao;
	}
	
	public int getValue() {
		return this.num;
	}
}
