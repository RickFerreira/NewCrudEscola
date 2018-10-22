package services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlunoDAO;
import entities.Aluno;
import util.TransacionalCdi;

@ApplicationScoped
@Named
public class AlunoService implements Serializable, Service<Aluno> {

	private static final long serialVersionUID = -7803325791425670859L;

	@Inject
	private AlunoDAO alunoDAO;

	@Override
	@TransacionalCdi
	public void save(Aluno aluno) {
		alunoDAO.save(aluno);
	}

	@Override
	@TransacionalCdi
	public void update(Aluno aluno) {
		alunoDAO.update(aluno);
	}

	@Override
	@TransacionalCdi
	public void remove(Aluno aluno) {
		alunoDAO.remove(aluno);
	}

	@Override
	public Aluno getByID(long alunoId) {
		return alunoDAO.getByID(alunoId);
	}

	@Override
	public List<Aluno> getAll() {
		return alunoDAO.getAll();
	}

}
