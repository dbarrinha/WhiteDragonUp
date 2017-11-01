package br.gov.pi.tce.whitedragon.util;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class JSFUtil {

	public void sendInfoMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message, "Aviso");
		addMessageToJsfContext(facesMessage);
	}

	public void sendErrorMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, message, "Erro");
		addMessageToJsfContext(facesMessage);
	}

	public void showMessageDialog(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, "Aviso", message);
		RequestContext.getCurrentInstance().showMessageInDialog(facesMessage);
	}

	public void showErrorDialog(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, "Erro", message);
		RequestContext.getCurrentInstance().showMessageInDialog(facesMessage);
	}

	public FacesMessage createInfoMessage(String message) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
	}

	public FacesMessage createErrorMessage(String message) {
		return new FacesMessage(FacesMessage.SEVERITY_WARN, message, "");
	}

	private FacesMessage createMessage(Severity severity, String message, String title) {
		return new FacesMessage(severity, message, title);
	}

	private void addMessageToJsfContext(FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public void cleanMessages() {
		Iterator<FacesMessage> msgIterator = FacesContext.getCurrentInstance().getMessages();
	    while(msgIterator.hasNext())
	    {
	        msgIterator.next();
	        msgIterator.remove();
	    }
	}

	public void showDialog(String dialogo) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('" + dialogo + "').show();");
	}

	public void hideDialog(String dialogo) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('" + dialogo + "').hide();");
	}

	public void update(String element) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.update(element);
	}

}
