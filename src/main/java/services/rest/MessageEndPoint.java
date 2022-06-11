package services.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.rest.model.MessageData;
import services.rest.model.MessageStore;

@Path("message")
public class MessageEndPoint {
	
	Logger logger = Logger.getLogger(MessageEndPoint.class.getName());
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(MessageData messageRequest) {
		
		logger.log(Level.INFO, "Saving new message {0}", messageRequest.getMessage());
		
		Long msgId = MessageStore.add(messageRequest.getMessage());
		
		MessageData response = new MessageData();
		response.setMessageId(msgId);
		
		// return 201 response code with message id
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrive(@PathParam("id") long msgId) {
		
		logger.log(Level.INFO, "Retrieving message using id {0}", msgId);
		
		String message = MessageStore.get(msgId);
		
		if ( message == null ) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} 
		
		MessageData response = new MessageData();
		response.setMessage(message);
		return Response.ok(response).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(MessageData msgReq) {
		
		logger.log(Level.INFO, "Updating message for id {0}", msgReq.getMessageId());
		
		boolean updated = MessageStore.update(msgReq.getMessageId(), msgReq.getMessage());
		
		if ( updated ) {
			return Response.ok().build();
		} else {
			
			// return 301 if update didn't happen
			return Response.status(Response.Status.NOT_MODIFIED).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long msgId) {
		
		logger.log(Level.INFO, "Deleting message for id {0}", msgId);
		
		boolean delete = MessageStore.delete(msgId);
		
		if ( delete ) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_MODIFIED).build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response retriveAll() {
		
		logger.info("Retrieving all messages");
		
		List<MessageData> messageList = MessageStore.getAll();
		
		if ( messageList.isEmpty() )
			return Response.status(Response.Status.NO_CONTENT).build();
		else
			return Response.ok(messageList.toArray(new MessageData[messageList.size()])).build();
	}
}
