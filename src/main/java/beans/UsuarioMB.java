/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import acesso.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import servico.UsuarioServico;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB{
    private String nome;
    private String login;
    private String tipo;
    private List<Usuario> usuarios;
    
    @EJB
    private UsuarioServico usuarioServico;
    
    public UsuarioMB() {
        usuarios = new ArrayList<>();
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

    public String getTipo() {
        Usuario usuario = usuarioServico.retornaUsuario(login);
        
        return usuario.getTipo();
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Usuario> getUsuarios() {
        return usuarioServico.todosUsuarios();
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    

}

