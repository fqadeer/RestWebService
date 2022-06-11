package services.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageData {
	
	private Long 	messageId;
	private String 	message;
	
	public MessageData() {
	}
	
	public MessageData(Long msgId, String message) {
		this.messageId = msgId;
		this.message = message;
	}
	
	public Long getMessageId() {
		return messageId;
	}
	
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
