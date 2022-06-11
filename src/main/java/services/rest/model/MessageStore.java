package services.rest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageStore {
	
	static Map<Long, String> store = new ConcurrentHashMap<Long, String>();
	
	public static Long add(String message) {
		
		// current millisecond time use for unique msgId. Don't try this at home & production
		Long msgId = System.currentTimeMillis();
		store.put(msgId, message);
		
		return msgId;
	}
	
	public static String get(Long msgId) {
		return store.get(msgId);
	}
	
	public static boolean update(Long msgId, String message) {
		
		boolean updated = false;
		if ( store.containsKey(msgId) ) {
			store.put(msgId, message);
			updated = true;
		}
		
		return updated;
	}
	
	public static boolean delete(Long msgId) {
		
		boolean deleted = false;
		if ( store.containsKey(msgId) ) {
			store.remove(msgId);
			deleted = true;
		}
		
		return deleted;
	}
	
	public static List<MessageData> getAll() {
		
		List<MessageData> messageList = new ArrayList<MessageData>();
		store.entrySet().forEach( e -> messageList.add(new MessageData(e.getKey(), e.getValue())));
		
		return messageList;
	}
}


