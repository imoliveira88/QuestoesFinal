package beans;

import acesso.Cliente;
import excecao.ExcecaoNegocio;
import java.text.ParseException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Bandeira;
import modelo.Cartao;
import servico.BandeiraServico;
import servico.ClienteServico;

@ManagedBean(name = "cadastroMB")
@SessionScoped
public class ClienteMB{
    private String bandeira;
    private String numeroCartao;
    private Date validade;
    private String nome;
    private String login;
    private String senha;
    
    @EJB
    private ClienteServico clienteServico;
    
    @EJB
    private BandeiraServico bandeiraServico;
    
    public ClienteMB() {
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
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
    
    public String cadastraCliente() throws ParseException, ExcecaoNegocio, EJBException{
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        try{
            Bandeira band = bandeiraServico.retornaBandeira(bandeira);

            Cartao cartao = new Cartao(band, numeroCartao, validade);
            Cliente cliente = new Cliente(nome, login, senha, cartao);

            if(clienteServico.salvar(cliente)){
                msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Cadastro feito com sucesso!");
                context.addMessage("destinoAviso", msg);
            }else{
                msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Já existe um usuário com o login escolhido! Tente outro!");
                context.addMessage("destinoAviso", msg);
            }
            
            
            return "login";
        }catch(Exception e){
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Houve um erro no cadastro! Atente para os formatos válidos dos campos e tente novamente!");
            context.addMessage("destinoAviso", msg);
            
            return "cliente";
        }
    }
}
