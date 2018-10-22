package beans;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entities.Professor;
import services.ProfessorService;

@SessionScoped
@Named
public class ProfessorBean implements Serializable {
	public Collection<Professor> getProfs() {
		return profs;
	}

	public void setProfs(Collection<Professor> profs) {
		this.profs = profs;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Professor professor = new Professor();
	private Collection<Professor> profs;
	@Inject
	private ProfessorService service;
	private String confirmSenha;

	@PostConstruct
	public void init() {
		limpar();
	}

	public void limpar() {
		professor = new Professor();
		profs = getService().getAll();
	}

	public void onRowEdit(Professor p) {
		service.update(p);
		FacesMessage msg = new FacesMessage("Professor editado", p.getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		limpar();
	}

	public String getUserLogin() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Principal userPrincipal = externalContext.getUserPrincipal();
		if (userPrincipal == null) {
			return "Olá! Faça o login para usar o sistema";
		}
		return "Olá, "+userPrincipal.getName();
	}

	public void efetuarLogout() throws IOException, ServletException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.invalidate();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		request.logout();
		ec.redirect(ec.getApplicationContextPath());
	}

	public boolean isUserInRole(String role) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		return externalContext.isUserInRole(role);
	}

	public void removerProf(Professor p) {
		service.remove(p);
		limpar();
	}

	public void salvarProf() {
		if (!professor.getSenha().equals(confirmSenha)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR", "As senhas nao conferem!"));
		} else {
			boolean sameLogin = false;
			for (Professor p : profs) {
				if (professor.getLogin().equals(p.getLogin())) {
					sameLogin = true;
				}
			}
			if (sameLogin) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("ERROR", "Login ja esta cadastrado"));
			} else {
				service.save(professor);
				limpar();
			}
		}

	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setProfs(Set<Professor> profs) {
		this.profs = profs;
	}

	public ProfessorService getService() {
		return service;
	}

	public void setService(ProfessorService service) {
		this.service = service;
	}

	public String getConfirmSenha() {
		return confirmSenha;
	}

	public void setConfirmSenha(String confirmSenha) {
		this.confirmSenha = confirmSenha;
	}

}
