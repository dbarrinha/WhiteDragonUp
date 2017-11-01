package br.gov.pi.tce.whitedragon.controller;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.gov.pi.tce.TceJsfUtil.FacesUtil;
import br.gov.pi.tce.tcebaseexterno.facade.LoginFacade;
import br.gov.pi.tce.tcebaseexterno.facade.UsuarioFacade;
import br.gov.pi.tce.tcebaseexterno.model.AplicacaoUG;
import br.gov.pi.tce.tcebaseexterno.model.Usuario;
import br.gov.pi.tce.tcebaseexterno.security.TceUserPrincipal;

@Named
@ViewScoped
public class LoginController extends AppController  {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioFacade usuarioFacade;

	@EJB
	private LoginFacade loginFacade;
	
	private String username;
	private String password;
	private AplicacaoUG aplicacaoUG;
	private List<AplicacaoUG> listaUgsUsuario;
	private boolean senhaValidada = false; 

	
	public void carregaUGs()  {
		setSenhaValidada(false);
		listaUgsUsuario = null;

		try {
			if (loginFacade.validaSenha(this.username, this.password)) {
				
				// usuário inativo
				Usuario usuario = usuarioFacade.buscaPorLogin(getUsername());
				if ( usuarioInativado(usuario) ) {
					FacesUtil.addErrorMessage("Este usuário encontra-se inativado");					
				} 
				else {					
					Long idAplicacao = loginFacade.recuperaIdAplicacao();
					listaUgsUsuario = usuarioFacade.listaUGsdoUsuario(getUsername(), idAplicacao);
					setSenhaValidada(true);
				}	
			} else {
				FacesUtil.addErrorMessage("Usuário ou senha inválidos");
			}
		}
		catch (Exception e) {
			this.showDialogErrorMessage("O sistema está apresentado problemas. Por favor tente mais tarde!");
		}

	}

	public String login() {

		String url = "/principal?faces-redirect=true";	

		FacesContext fc;
		ExternalContext ec;
		HttpSession sessao;

		if (this.aplicacaoUG == null) {
			this.sendErrorMessageToUser("Selecione a unidade gestora!");
			return null;
		}

		try {

			loginFacade.login(getUsername(), this.aplicacaoUG, getPassword());

			fc = FacesContext.getCurrentInstance();
			ec = fc.getExternalContext();

			sessao = (HttpSession) ec.getSession(false);

			TceUserPrincipal principal = (TceUserPrincipal) ec.getUserPrincipal();

			sessao.setAttribute("nomeUg", this.aplicacaoUG.getNmUg());

			Usuario usuario = usuarioFacade.buscaPorLogin(principal.getName());
			sessao.setAttribute("nomeUsuario",  usuario.getNome());			
			sessao.setAttribute("cpfUsuario",   usuario.getCpf());
			sessao.setAttribute("emailUsuario", usuario.getEmail());

		}
		catch (Exception e) {
			this.sendErrorMessageToUser("há um problema de acesso do usuário ao sistema");
			return null;
		}
		return url;
	}

	public void logout() {
		loginFacade.logout();;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSenhaValidada() {
		return senhaValidada;
	}

	public void setSenhaValidada(boolean senhaValidada) {
		this.senhaValidada = senhaValidada;
	}

	public AplicacaoUG getAplicacaoUG() {
		return aplicacaoUG;
	}

	public void setAplicacaoUG(AplicacaoUG aplicacaoUG) {
		this.aplicacaoUG = aplicacaoUG;
	}

	public List<AplicacaoUG> getListaUgsUsuario() {
		return listaUgsUsuario;
	}

	public void setListaUgsUsuario(List<AplicacaoUG> listaUgsUsuario) {
		this.listaUgsUsuario = listaUgsUsuario;
	}

	Boolean usuarioInativado(Usuario usuario) {
		return Arrays.asList("S", "s").contains(usuario.getInativo()); 
	}

}
