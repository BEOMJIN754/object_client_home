package aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class SessionManager {
	private static SessionManager sessionManager = null;
	private static Map<String, String> sessionMap = new HashMap<>();
	
	public static SessionManager getSessionManager() {
		if(sessionManager == null) {
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}
	public SessionManager() {
	}
	
	public String createSessionId(String userId) {
		String sessionId = UUID.randomUUID().toString();
		this.sessionMap.put(sessionId,userId);
		return sessionId;
	}
	public static boolean verifySession(String sessionId) {
		boolean isValid = sessionMap.containsKey(sessionId);
		if(!isValid) {
			LmsLoggingManager.getLogger().log(Level.WARNING, "Invalid session for user search.");
			System.exit(1);
		}
		return isValid;
	}
}
