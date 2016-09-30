package beans;

import acesso.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotBlank;
import servico.UsuarioServico;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    @NotBlank
    private String login;
    @NotBlank
    private String senha;
    private FacesContext facesContext;
    private Usuario usuarioSessao;
    
    @EJB
    UsuarioServico usuarioServico;

    public String login() {
        try {
            facesContext = FacesContext.getCurrentInstance();
            Recaptcha recaptcha = new Recaptcha(facesContext);

            if (recaptcha.validar()) {
                HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                request.login(login, senha);
                facesContext.getExternalContext().getSession(true);
                usuarioSessao = usuarioServico.retornaUsuario(login);
            } else {
                setLogin(null);
                setUsuarioSessao(null);
                adicionarMensagem("Captcha inválido!");
                return "/faces/publico/login.xhtml?faces-redirect=true";
            }

        } catch (ServletException ex) {
            setLogin(null);
            setUsuarioSessao(null);
            adicionarMensagem("Senha ou usuário inválidos!");
            return "/faces/publico/login.xhtml?faces-redirect=true";
        }

        return "/faces/publico/login.xhtml?faces-redirect=true";
    }

    private void adicionarMensagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
        facesContext.addMessage(null, message);
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

}
