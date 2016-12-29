package beans;

import acesso.Cliente;
import excecao.ExcecaoNegocio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import modelo.Disciplina;
import modelo.Organizadora;
import modelo.Questao;
import servico.ClienteServico;
import servico.DisciplinaServico;
import servico.OrganizadoraServico;
import servico.QuestaoServico;

@ManagedBean(name = "questaoMB")
@SessionScoped
public class QuestaoMB extends BeanGeral{
    private Questao questao;
    private Cliente cliente;
    private String enunciado;
    private List<Questao> questoes;
    private String organizadora;
    private List<Organizadora> organizadoras;
    private char alt_escolhida;
    private String disciplina;
    private List<Disciplina> disciplinas;
    private int ano;
    private char correta;
    private int tamanho;
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaE;
    
    private int atual;
    
    @EJB
    QuestaoServico questaoServico;
    
    @EJB
    OrganizadoraServico organizadoraServico;
    
    @EJB
    ClienteServico clienteServico;
    
    @EJB
    DisciplinaServico disciplinaServico;
    
    public QuestaoMB(){
        questoes = new ArrayList<>();
        organizadoras = new ArrayList<>();
        disciplinas = new ArrayList<>();
        questao = new Questao();
        atual = 0;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public char getCorreta() {
        return correta;
    }

    public void setCorreta(char correta) {
        this.correta = correta;
    }

    public char getAlt_escolhida() {
        return alt_escolhida;
    }

    public void setAlt_escolhida(char alt_escolhida) {
        this.alt_escolhida = alt_escolhida;
    }
   
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getOrganizadora() {
        return organizadora;
    }

    public void setOrganizadora(String organizadora) {
        this.organizadora = organizadora;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public List<Questao> getQuestoes() {
        return questaoServico.todasQuestoes();
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    public List<Organizadora> getOrganizadoras() {
        List<Organizadora> orgs = new ArrayList<>();
        orgs.addAll(organizadoraServico.todasOrganizadoras());
        return orgs;
    }

    public void setOrganizadoras(List<Organizadora> organizadoras) {
        this.organizadoras = organizadoras;
    }
    
    public Questao getQuestoesCriterio() throws NoResultException, EJBException{
        List<Questao> todasQuestoes;
        List<Questao> selecao = new ArrayList<>();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        
        try {
             
            if(session.getAttribute("disciplinaSessao").equals("") && session.getAttribute("organizadoraSessao").equals("")) todasQuestoes = questaoServico.todasQuestoes();
            else todasQuestoes = questaoServico.questoesCriterio(disciplinaServico.retornaDisciplina((String) session.getAttribute("disciplinaSessao")), organizadoraServico.retornaOrganizadora((String) session.getAttribute("organizadoraSessao")));
            
            tamanho = todasQuestoes.size();
            
            return todasQuestoes.get(atual);
        } catch (Exception e) {
            //this.addMensagem("Não há questões que satisfaçam este critério!");
            return null;
        }
        
    }
    
    public List<Disciplina> getDisciplinas(){
        List<Disciplina> disc = new ArrayList<>();
        disc.addAll(disciplinaServico.todasDisciplinas());
        return disc;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }
    
    public void inicializar(){
        this.questao = null;
        this.correta = '\0';
        this.alt_escolhida = '\0';
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public String getAlternativaE() {
        return alternativaE;
    }

    public void setAlternativaE(String alternativaE) {
        this.alternativaE = alternativaE;
    }
    
    public void inicializa(){
        enunciado = "";
        alternativaA = "";
        alternativaB = "";
        alternativaC = "";
        alternativaD = "";
        alternativaE = "";
    }
    
    public String salvar() throws ExcecaoNegocio,ParseException,EJBException {
        
        try{
            questao.setAno(ano);
            questao.setCorreta(correta);
            Organizadora org = organizadoraServico.retornaOrganizadora(organizadora);
            questao.setOrganizadora(org);
            Disciplina disc = disciplinaServico.retornaDisciplina(disciplina);
            questao.setDisciplina(disc);
            questao.addAlternativa(alternativaA);
            questao.addAlternativa(alternativaB);
            questao.addAlternativa(alternativaC);
            questao.addAlternativa(alternativaD);
            questao.addAlternativa(alternativaE);
            questao.setEnunciado1(enunciado);
            
            inicializa();
            
            if(questaoServico.salvar(questao)) this.addMensagem("Cadastro feito com sucesso!");
            else this.addMensagem("Já existe uma questão com o mesmo enunciado!");
            
            return "questao";
        }catch(Exception e){
            this.addMensagem("Houve uma falha no cadastro! Atente para os formatos válidos dos campos e tente novamente!");
            return "questao";
        }
    }
    
    public String excluir() throws Exception,EJBException,ExcecaoNegocio{

        try{
            questaoServico.excluir(questao);
            this.questoes.remove(questao);
            this.addMensagem("Questão excluída com sucesso!");
            return "questao";
        }catch(Exception e){
            this.addMensagem("Houve uma falha na exclusão da disciplina! Provavelmente o registro está relacionado a outros persistidos no banco.");
            return "questao";
        }
    }
    
    public String filtrar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("disciplinaSessao", disciplina);
        session.setAttribute("organizadoraSessao", organizadora);
        
        atual = 0;
        
        return "questaoUsu";
    }
    
    public String verificaResposta(long id)throws EJBException{
        FacesContext context = FacesContext.getCurrentInstance();
        Cliente cl = new Cliente();
        try{
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            cliente = (Cliente) session.getAttribute("usuarioDaSessao");
            
            int erros = clienteServico.retornaCliente(cliente.getLogin()).getErradas();
            int acertos = clienteServico.retornaCliente(cliente.getLogin()).getCorretas();
                        
            cl.setLogin(cliente.getLogin());
            questao = questaoServico.retornaQuestao(id);
                                                                                                                                                                                                                                                                                                                                                                                                                                                          
            if (alt_escolhida == '\0'){
                this.addMensagem("Escolha uma alternativa!");
                return "questaoUsu";
            }
            
            if (alt_escolhida == questao.getCorreta()) {
                this.addMensagem("Você acertou!");
                cl.setErradas(erros);
                cl.setCorretas(acertos+1);
                clienteServico.atualizar(cl);
            } else {
                this.addMensagem("Você errou!");
                cl.setErradas(erros+1);
                cl.setCorretas(acertos);
                clienteServico.atualizar(cl);
            }
            
            atual = (atual+1)%tamanho;
            
            inicializar();
            return "questaoUsu";
            
        }catch(Exception e){
            this.addMensagem("Algo deu errado... tente novamente!");
            inicializar();
            return "questaoUsu";
        }
    }
    
    public String proxima() throws EJBException{
        try{
            
            inicializar();
            
            atual = (atual + 1)%tamanho;
            
            return "questaoUsu";
            
        }catch(Exception e){
            this.addMensagem("Algo deu errado... tente novamente!");
            inicializar();
            return "questaoUsu";
        }
    }
}