/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import acesso.Cliente;
import excecao.ExcecaoNegocio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Disciplina;
import modelo.Organizadora;
import modelo.Questao;
import servico.ClienteServico;
import servico.DisciplinaServico;
import servico.OrganizadoraServico;
import servico.QuestaoServico;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "questaoMB")
@RequestScoped
public class QuestaoMB extends BeanGeral{
    private Questao questao;
    private Cliente cliente;
    private String enunciado;
    private List<Questao> questoes;
    private String organizadora;
    private List<Organizadora> organizadoras;
    private String categoria;
    private String disciplina;
    private List<Disciplina> disciplinas;
    private boolean nada;
    private int ano;
    private char correta;
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaE;
    private String alt_escolhida;
    private String info;
    
    @EJB
    QuestaoServico questaoServico;
    
    @EJB
    OrganizadoraServico organizadoraServico;
    
    @EJB
    ClienteServico clienteServico;
    
    @EJB
    DisciplinaServico disciplinaServico;
    
    public QuestaoMB(){
        alt_escolhida = "";
        questoes = new ArrayList<>();
        organizadoras = new ArrayList<>();
        disciplinas = new ArrayList<>();
        questao = new Questao();
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    public boolean isNada() {
        return nada;
    }

    public void setNada(boolean nada) {
        this.nada = nada;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
        return organizadoraServico.todasOrganizadoras();
    }

    public void setOrganizadoras(List<Organizadora> organizadoras) {
        this.organizadoras = organizadoras;
    }

    public char alt_escolhida(int i) {
        return alt_escolhida.charAt(i);
    }

    public void setAlt_escolhida(char alt_escolhida, int i) {
        for(int j=0; j<=i; j++){
            if(j != i) this.alt_escolhida += 'f';
            else this.alt_escolhida += alt_escolhida;
        }
    }
    
    public List<Questao> getQuestoesCriterio(){
        return this.getQuestoes();
        //if(disciplina == null || organizadora == null || nada == true) return this.getQuestoes();
        //return questaoServico.questoesCriterio(disciplinaServico.retornaDisciplina(disciplina),organizadoraServico.retornaOrganizadora(organizadora));
    }
    
    public List<Disciplina> getDisciplinas(){
        return disciplinaServico.todasDisciplinas();
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
        return "questaoUsu";
    }
    
    public String verificaResposta(long id)throws EJBException{
        FacesContext context = FacesContext.getCurrentInstance();
        Cliente cl = new Cliente();
        try{
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            cliente = (Cliente) session.getAttribute("usuarioDaSessao");
            
            cl.setLogin(cliente.getLogin());
            questao = questaoServico.retornaQuestao(id);
                        
            if (alt_escolhida.charAt((int)id) == '\0'){
                this.addMensagem("Escolha uma alternativa!");
                return "questaoUsu";
            }
            
            if (alt_escolhida.charAt((int)id) == questao.getCorreta()) {
                this.addMensagem("Você acertou!");
                cl.setCorretas(cliente.getCorretas() + 1);
                cl.setErradas(cliente.getErradas());
                
            } else {
                this.addMensagem("Você errou!");
                cl.setErradas(cliente.getErradas() + 1);
                cl.setCorretas(cliente.getCorretas());
            }
            info = "Id da questão: " + id + "  Alternativa correta da questão: " + questao.getCorreta() + "Alternativa marcada: " + this.alt_escolhida;
            clienteServico.atualizar(cl);
            inicializar();
            return "questaoUsu";
        }catch(Exception e){
            this.addMensagem("Algo deu errado... tente novamente!");
            inicializar();
            return "questaoUsu";
        }
    } 
}