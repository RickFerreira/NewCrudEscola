package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import entities.Aluno;
import entities.Disciplina;
import services.AlunoService;
import services.DisciplinaService;
import services.ProfessorService;

@ApplicationScoped
@Named
public class DisciplinaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Disciplina disciplina = new Disciplina();
	private Collection<Disciplina> disciplinas;
	@Inject
	private ProfessorService profService;
	@Inject
	private DisciplinaService service;
	@Inject
	@ManagedProperty(value = "#{professorBean}")
	private ProfessorBean profBean;
	@Inject
	private AlunoService alunoService;
	private Disciplina discMatriculaAluno;
	private boolean renderPanelCadastro;
	private DualListModel<Aluno> pickListAluno;

	@PostConstruct
	public void init() {
		limpar();
		disciplina.getProf().setId(0L);
		setRenderPanelCadastro(false);
	}

	public void limpar() {
		disciplina = new Disciplina();
		disciplinas = service.getAll();
		discMatriculaAluno = new Disciplina();
		setPickListAluno(new DualListModel<Aluno>(new ArrayList<Aluno>(), new ArrayList<Aluno>()));
	}
	
	public void removerDisciplina(Disciplina d) {
		service.remove(d);
		limpar();
	}

	public void iniciarPickListAluno() {
		ArrayList<Aluno> alunosSource = new ArrayList<Aluno>();
		ArrayList<Aluno> alunosTarget = new ArrayList<Aluno>();
		alunosSource.addAll(getAlunoService().getAll());
		alunosSource.removeAll(discMatriculaAluno.getAlunos());
		if (alunosSource.equals(getAlunoService().getAll())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("DEU ERRO CACHORRO", "AS LISTAAS SAO IGUAIS"));
		}
		alunosTarget.addAll(discMatriculaAluno.getAlunos());
		setPickListAluno(new DualListModel<Aluno>(alunosSource, alunosTarget));
	}

	public void salvarPickListAluno() {
		Set<Aluno> alunosDiscSelecionada = new HashSet<Aluno>();
		alunosDiscSelecionada.addAll(getPickListAluno().getTarget());
		discMatriculaAluno.getAlunos().addAll(alunosDiscSelecionada);
		service.update(discMatriculaAluno);
		limpar();
		PrimeFaces.current().ajax().update("form");
		setPickListAluno(new DualListModel<Aluno>());
		setRenderPanelCadastro(false);
	}

	public void salvarDisc() {
		disciplina.setProf(profService.getByID(disciplina.getProf().getId()));
		service.save(disciplina);
		limpar();
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public DisciplinaService getService() {
		return service;
	}

	public void setService(DisciplinaService service) {
		this.service = service;
	}

	public Collection<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Collection<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public ProfessorService getProfService() {
		return profService;
	}

	public void setProfService(ProfessorService profService) {
		this.profService = profService;
	}

	public Disciplina getDiscSelecionada() {
		return discMatriculaAluno;
	}

	public void setDiscSelecionada(Disciplina discSelecionada) {
		this.discMatriculaAluno = discSelecionada;
	}

	public boolean isRenderPanelCadastro() {
		return renderPanelCadastro;
	}

	public void setRenderPanelCadastro(boolean renderPanelCadastro) {
		this.renderPanelCadastro = renderPanelCadastro;
	}

	public DualListModel<Aluno> getPickListAluno() {
		return pickListAluno;
	}

	public void setPickListAluno(DualListModel<Aluno> pickListAluno) {
		this.pickListAluno = pickListAluno;
	}

	public AlunoService getAlunoService() {
		return alunoService;
	}

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public Disciplina getDiscMatriculaAluno() {
		return discMatriculaAluno;
	}

	public void setDiscMatriculaAluno(Disciplina discMatriculaAluno) {
		this.discMatriculaAluno = discMatriculaAluno;
	}
}
