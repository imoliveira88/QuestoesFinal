package beans;


import acesso.Administrador;
import excecao.ExcecaoNegocio;
import java.text.ParseException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import servico.AdministradorServico;

@ManagedBean(name = "cadastroMBF")
@SessionScoped
public class AdministradorMB{
    private String nome;
    private String login;
    private String senha;
    
    @EJB
    private AdministradorServico admServico;
    
    /**
     * Creates a new instance of cadastroCliente
     */
    public AdministradorMB() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }  
    
    public String cadastraFuncionario() throws EJBException, ParseException, ExcecaoNegocio{        
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        try{
            Administrador adm = new Administrador(nome, login, senha);
            admServico.salvar(adm);
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Cadastro feito com sucesso!");
            context.addMessage("destinoAviso", msg);
            return "funcionario";
        }catch(Exception e){
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Houve uma falha no cadastro! Atente para os formatos válidos dos campos e tente novamente!");
            context.addMessage("destinoAviso", msg);
            return "funcionario";
        }
        
    }
}
