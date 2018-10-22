package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;

import entities.Aluno;
import entities.Turma;
import services.AlunoService;
import services.TurmaService;

@SessionScoped
@Named
public class TurmaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Turma turmaSelecionada = new Turma();
	@Inject
	private AlunoService alunoService;
	private boolean renderPanelEdicao;
	private DualListModel<Aluno> pickListAluno;

	public ArrayList<String> getSelectIdAlunos() {
		return selectIdAlunos;
	}

	public void setSelectIdAlunos(ArrayList<String> selectIdAlunos) {
		this.selectIdAlunos = selectIdAlunos;
	}

	private Turma turma = new Turma();
	private ArrayList<String> selectIdAlunos;
	private Collection<Turma> turmas;
	@Inject
	private TurmaService service;
	private List<Aluno> alunos;

	public void limpar() {
		turma = new Turma();
		turmas = getService().getAll();
	}

	public Turma getTurma() {
		return turma;
	}

	public void concluirEdicao() {
		Set<Aluno> alunosTurmaSelecionada = new HashSet<Aluno>();
		alunosTurmaSelecionada.addAll(getPickListAluno().getTarget());
		turmaSelecionada.getAlunos().addAll(alunosTurmaSelecionada);
		service.update(turmaSelecionada);
		limpar();
		PrimeFaces.current().ajax().update("form");
		setPickListAluno(new DualListModel<Aluno>());
		setRenderPanelEdicao(false);
	}

	public void removerTurma(Turma t) {
		service.remove(t);
		limpar();
	}

	public void iniciarDualListAluno() {
		ArrayList<Aluno> alunosSource = new ArrayList<Aluno>();
		ArrayList<Aluno> alunosTarget = new ArrayList<Aluno>();
		alunosSource.addAll(getAlunoService().getAll());
		alunosSource.removeAll(turmaSelecionada.getAlunos());
		alunosTarget.addAll(turmaSelecionada.getAlunos());
		setPickListAluno(new DualListModel<Aluno>(alunosSource, alunosTarget));
	}

	public void salvarTurma() {
		List<Aluno> alunos = alunoService.getAll();
		if (selectIdAlunos.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("ERRO", "Selecione ao menos um aluno!"));
		} else {

			Set<Aluno> alunosC = new HashSet<Aluno>();
			for (String l : selectIdAlunos) {
				for (Aluno a : alunos) {
					if (Long.toString((a.getId())).equals(l)) {
						alunosC.add(a);
					}
				}
			}
			String alunosDuplicados = "Os alunos: ";
			boolean ehDuplicado = false;
			for (Aluno a : alunosC) {
				for (Turma t : service.getAll()) {
					for (Aluno b : t.getAlunos()) {
						if (a.getId().equals(b.getId())) {
							ehDuplicado = true;
							alunosDuplicados += a.getNome() + ", ";
						}
					}
				}
			}

			if (ehDuplicado) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("ERRO", alunosDuplicados + "já estao cadastrados!"));
				limpar();
			} else {
				turma.setAlunos(alunosC);
				service.save(turma);
				limpar();
			}

		}
		limpar();

	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	public TurmaService getService() {
		return service;
	}

	public void setService(TurmaService service) {
		this.service = service;
	}

	@PostConstruct
	private void init() {
		setAlunos(getAlunoService().getAll());
		setRenderPanelEdicao(false);
		limpar();
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}

	public AlunoService getAlunoService() {
		return alunoService;
	}

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public boolean isRenderPanelEdicao() {
		return renderPanelEdicao;
	}

	public void setRenderPanelEdicao(boolean renderPanelEdicao) {
		this.renderPanelEdicao = renderPanelEdicao;
	}

	public DualListModel<Aluno> getPickListAluno() {
		return pickListAluno;
	}

	public void setPickListAluno(DualListModel<Aluno> pickListAluno) {
		this.pickListAluno = pickListAluno;
	}

}
