package br.gov.pi.tce.whitedragon.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import br.gov.pi.tce.whitedragon.util.Bundle;
import br.gov.pi.tce.whitedragon.util.DadosSessao;
import br.gov.pi.tce.whitedragon.util.JSFUtil;
import br.gov.pi.tce.whitedragon.util.Utils;

public class AppController implements Serializable {

	private static final long serialVersionUID = 1L;

	private DadosSessao dadosSessao = new DadosSessao();

	private JSFUtil jsfUtil = new JSFUtil();

	private Utils utils = new Utils();

	public AppController() {
		super();
	}

	public DadosSessao getDadosSessao() {
		return dadosSessao;
	}
	
	public void setDadosSessao(DadosSessao dadosSessao) {
		this.dadosSessao = dadosSessao;
	}

	public JSFUtil getJsfUtil() {
		return jsfUtil;
	}
	
	public Utils getUtils() {
		return utils;
	}
	
	public void jsfCleanMessages() {
		this.jsfUtil.cleanMessages();
	}
	
	protected void jsfShowDialog(String dialog){
		this.jsfUtil.showDialog(dialog);
	}
	
	protected void jsfHideDialog(String dialog){
		this.jsfUtil.hideDialog(dialog);
	}
	
	protected void JsfUpdate(String element) {
		this.jsfUtil.update(element);
	}
	
	protected RequestContext getRequestContext(){
		return RequestContext.getCurrentInstance();
	}

	public void setMessageUtil(JSFUtil messageUtil) {
		this.jsfUtil = messageUtil;
	}

	protected void sendInfoMessageToUser(String message) {
		jsfUtil.sendInfoMessageToUser(message);
	}

	protected void sendErrorMessageToUser(String message) {
		jsfUtil.sendErrorMessageToUser(message);
	}

	public void showDialogInfoMessage(String infoMessage) {
		jsfUtil.showMessageDialog(infoMessage);
	}

	public void showDialogErrorMessage(String errorMessage) {
		jsfUtil.showErrorDialog(errorMessage);
	}


	public String informacaoMemoria() {

		// get the total memory for my app
		long total = Runtime.getRuntime().totalMemory() / 1024;
		// get the free memory available
		long free = Runtime.getRuntime().freeMemory()  / 1024;

		// some simple arithmetic to see how much i use
		long used = (total - free)  / 1024;
		return String.format("Mem %6.6s | %6.6s | %6.6s\t(total | usada | livre)",total,free,used);

	}

	public int tamanhoMaximoUpload() {
		return Bundle.TAM_MAX_UPLOAD.intValue();
	}

    public void showMessage(String title, String message) {
        FacesMessage oMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, title, message);
        RequestContext.getCurrentInstance().showMessageInDialog(oMessage);
    }

	public boolean empty(final String s) {
		// Null-safe, short-circuit evaluation.
		return s == null || s.trim().isEmpty();
	}

	public String stringLimited(String nome, int tamanhoMaximo) {
		return nome.concat(StringUtils.repeat(" ",tamanhoMaximo)).substring(0, tamanhoMaximo);
	}

}
