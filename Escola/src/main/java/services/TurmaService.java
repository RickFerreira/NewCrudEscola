
package services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.TurmaDAO;
import entities.Turma;
import util.TransacionalCdi;

@ApplicationScoped
@Named
public class TurmaService implements Serializable, Service<Turma> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private TurmaDAO dao;

	@Override
	@TransacionalCdi
	public void save(Turma e) {
		dao.save(e);
	}

	@Override
	@TransacionalCdi
	public void update(Turma aluno) {
		dao.update(aluno);
	}

	@Override
	@TransacionalCdi
	public void remove(Turma aluno) {
		dao.remove(aluno);
	}

	@Override
	public Turma getByID(long alunoId) {
		return dao.getByID(alunoId);
	}

	@Override
	public List<Turma> getAll() {
		return dao.getAll();
	}

}
