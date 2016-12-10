package beans;

import acesso.Cliente;
import excecao.ExcecaoNegocio;
import java.text.ParseException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Bandeira;
import modelo.Cartao;
import servico.BandeiraServico;
import servico.ClienteServico;

@ManagedBean(name = "cadastroMB")
@RequestScoped
public class ClienteMB extends BeanGeral{
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
        
        try{
            Bandeira band = bandeiraServico.retornaBandeira(bandeira);

            Cartao cartao = new Cartao(band, numeroCartao, validade);
            Cliente cliente = new Cliente(nome, login, senha, cartao);

            if(clienteServico.salvar(cliente)){
                this.addMensagem("Cadastro feito com sucesso!");
            }else{
                this.addMensagem("Já existe um usuário com o login escolhido! Tente outro");
            }
            
            
            return "login";
        }catch(Exception e){
            this.addMensagem("Houve um erro no cadastro! Atente para os formatos válidos dos campos e tente novamente!");
            
            return "cliente";
        }
    }
}
