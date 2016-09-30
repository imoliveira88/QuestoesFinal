package beans;

import acesso.Usuario;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import org.hibernate.validator.constraints.NotBlank;
import servico.UsuarioServico;

@ManagedBean(name = "loginBean2")
@SessionScoped
public class LoginBean2 implements Serializable {

    @NotBlank
    private String login;
    @NotBlank
    private String senha;
    private FacesContext facesContext;
    private Usuario usuarioSessao;
    
    @EJB
    UsuarioServico usuarioServico;

    public String login() throws ServletException,NoSuchAlgorithmException{
        try {
            facesContext = FacesContext.getCurrentInstance();
            Recaptcha recaptcha = new Recaptcha(facesContext);

            if (recaptcha.validar()) {
                if(valida(login,senha)){
                    facesContext.getExternalContext().getSession(true);
                    usuarioSessao = usuarioServico.retornaUsuario(login);
                    if(usuarioSessao.getTipo().equals("Cliente")) return "homeC";
                    else return "homeF";
                }
                else{
                    adicionarMensagem("Usuário ou senha inválidos!");
                    return "login";
                }
            } else {
                setLogin(null);
                setUsuarioSessao(null);
                adicionarMensagem("Captcha inválido!");
                return "login";
            }

        } catch (Exception e) {
            setLogin(null);
            setUsuarioSessao(null);
            adicionarMensagem("Senha ou usuário inválidos!");
            return "login";
        }

    }
    
    private boolean valida(String login, String senha)throws NoSuchAlgorithmException{
        Usuario usu = usuarioServico.retornaUsuario(login);
        
        String pass = senha + usu.getSal().substring(0, 3);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(pass.getBytes(Charset.forName("UTF-8")));
        return Base64.getEncoder().encodeToString(digest.digest()).equals(usu.getSenha());
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
