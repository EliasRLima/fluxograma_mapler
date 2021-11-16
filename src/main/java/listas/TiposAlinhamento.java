package listas;

public enum TiposAlinhamento {

	DECISAO(30),
	ENTRADA(30),
	SAIDA(30),
	FIM(5),
	INICIO(5),
	PROCESSAMENTO(30);
	
	private double alinhamento;
	
	private TiposAlinhamento(double num) {
		this.alinhamento = num;
	}
	
	public double getAlinhamento() {
		return this.alinhamento;
	}
	
	public static TiposAlinhamento getPorNome(final String nome){
		
		for(TiposAlinhamento tp : TiposAlinhamento.values()) {
			if(tp.name().equals(nome)) {
				return tp;
			}
		}
		return null;
	}
}
