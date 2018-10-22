package services;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.ProfessorDAO;
import entities.Professor;
import util.TransacionalCdi;
@ApplicationScoped
public class ProfessorService implements Service<Professor>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ProfessorDAO dao;

	@Override
	@TransacionalCdi
	public void save(Professor e) {
		e.setSenha(hash(e.getSenha()));
		dao.save(e);

	}

	@Override
	@TransacionalCdi

	public void update(Professor e) {
		dao.update(e);
	}

	@Override
	@TransacionalCdi

	public void remove(Professor e) {
		dao.remove(e);
	}

	@Override
	public Professor getByID(long userId) {
		return dao.getByID(userId);
	}

	@Override
	public List<Professor> getAll() {
		return dao.getAll();
	}

	private String hash(String password) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			String output = Base64.getEncoder().encodeToString(digest);
			return output;
		} catch (Exception e) {
			return password;
		}
	}

}
