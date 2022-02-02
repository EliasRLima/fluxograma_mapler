package figuras;

public class SequenceIdFigura {
	
	private static int id = 0;
	
	public static int getNextID() {
		return SequenceIdFigura.id++;
	}
	
	
}
