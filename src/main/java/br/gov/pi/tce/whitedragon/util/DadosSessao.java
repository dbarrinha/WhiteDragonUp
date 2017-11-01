package br.gov.pi.tce.whitedragon.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.gov.pi.tce.tcebaseexterno.security.TceUserPrincipal;

public class DadosSessao {

	private TceUserPrincipal principal = (TceUserPrincipal) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

	public TceUserPrincipal getPrincipal() {
		return principal;
	}

	public String getUsuarioLogado() {
		return principal.getName();
	}

	public String getNomeUsuarioLogado() {
		HttpSession sessao = Utils.getSession();
		return sessao.getAttribute("nomeUsuario").toString();
	}

	public String getCpfUsuarioLogado() {
		HttpSession sessao = Utils.getSession();
		return sessao.getAttribute("cpfUsuario").toString();
	}

	public String getEmailUsuarioLogado() {
		HttpSession sessao = Utils.getSession();
		return sessao.getAttribute("emailUsuario").toString();
	}

	public Long getIdAplicacao() {
		return principal.getIdAplicacao();
	}

	public String getNomeUg() {
		HttpSession sessao = Utils.getSession();
		return sessao.getAttribute("nomeUg").toString();
	}

	public Long getIdUg(){
		return principal.getIdUg();
	}

	public Date getDataLogin() {
		return principal.getDataLogin();
	}

	public String periodoValidadeUsuario() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MM-yy");
		return dataFormatada.format(principal.getDataInicialPermissao()) + " até " + dataFormatada.format(principal.getDataFinalPermissao());		
	}

	public Date getDataValidadeInicialUsuario() {
		return principal.getDataInicialPermissao();
	}

	public Date getDataValidadeFinalUsuario() {
		return principal.getDataFinalPermissao();
	}

	public boolean periodoUsuarioExpirou() {
		return this.getDataLogin().after(this.getDataValidadeFinalUsuario()) 
			   || 
			  this.getDataLogin().before(this.getDataValidadeInicialUsuario() );
	}

	public static Date dataHoje() {
		return Utils.dataHoje();
	}

}
