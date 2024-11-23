package aspect;

public class ExceptionManager {
	private static ExceptionManager instance = null;
	
	public static ExceptionManager getInstance() {
		if(instance == null) {
			instance = new ExceptionManager();
		}
		return instance;
	}
	private ExceptionManager() {}

	public void process(Exception e) {
		e.printStackTrace();
	}

}
