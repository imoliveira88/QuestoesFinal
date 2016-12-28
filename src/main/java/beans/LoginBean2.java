package beans;

import acesso.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import servico.ClienteServico;
import servico.UsuarioServico;

@ManagedBean(name = "loginBean2")
@SessionScoped
public class LoginBean2 implements Serializable {

    private String login;
    private String senha;
    private FacesContext facesContext;
    private Usuario usuarioSessao;
    
    @EJB
    UsuarioServico usuarioServico;
    @EJB
    ClienteServico clienteServico;

    public String login() throws ServletException,NullPointerException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Recaptcha recaptcha = new Recaptcha(fc);
        try {
            if (recaptcha.validar()) {
            if (valida(login, senha)) {
                usuarioSessao = usuarioServico.retornaUsuario(login);
                session.setAttribute("usuarioDaSessao", usuarioSessao);
                if (usuarioSessao.getTipo().equals("Cliente")) {
                    return "homeC";
                } else {
                    return "homeF";
                }
            } else {
                setLogin(null);
                setUsuarioSessao(null);
                session.setAttribute("usuarioDaSessao", usuarioSessao);
                adicionarMensagem("Usuário ou senha inválidos!");
                return "login";
            }
            }else{
                setLogin(null);
                setUsuarioSessao(null);
                session.setAttribute("usuarioDaSessao", usuarioSessao);
                adicionarMensagem("Captcha inválido!");
                return "login";    
            }
        } catch (Exception e) {
            setLogin(null);
            setUsuarioSessao(null);
            session.setAttribute("usuarioDaSessao", usuarioSessao);
            adicionarMensagem("Senha ou usuário inválidos!");
            return "login";
        }
    }
    

    
    
    private boolean valida(String login, String senha){
        Usuario usu = usuarioServico.retornaUsuario(login);
        
        return senha.equals(usu.getSenha());
    }

    private void adicionarMensagem(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.FACES_MESSAGES,mensagem);
        context.addMessage("destinoAviso", msg);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Usuario getUsuarioSessao() {
        return usuarioSessao;
    }

    public void setUsuarioSessao(Usuario usuarioSessao) {
        this.usuarioSessao = usuarioSessao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String configurar() {
        return "config";
    }
    
    public void skinPadrao() {  
	 Usuario user = getUsuarioSessao();
         user.setSkin("geral.css");
         clienteServico.atualizarSkin(user);
    }  
    public void skinVerde() {  
	 Usuario user = getUsuarioSessao();
         user.setSkin("verde.css");
         clienteServico.atualizarSkin(user);
    }  
    public void skinVioleta() {  
	 Usuario user = getUsuarioSessao();
         user.setSkin("violeta.css");
         clienteServico.atualizarSkin(user);
    }  
    public void skinPreto() {  
	 Usuario user = getUsuarioSessao();
         user.setSkin("preto.css");
         clienteServico.atualizarSkin(user);
    }  

}
