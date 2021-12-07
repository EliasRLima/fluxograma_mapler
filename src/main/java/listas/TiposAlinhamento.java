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
	
    public static TiposAlinhamento getPorValue(int tipo){
		
		for(Tipos t : Tipos.values()) {
			if(t.SAIDA.getValue() == tipo) {
				for(TiposAlinhamento tp : TiposAlinhamento.values()) {
					if(tp.name().toLowerCase().equals(t.toString().toLowerCase())) {
						return tp;
					}
				}
				break;
			}
		}
    	
		return null;
	}
}
