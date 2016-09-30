/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import acesso.Usuario;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.TransactionManagement;
import static javax.ejb.TransactionManagementType.CONTAINER;
import javax.persistence.TypedQuery;

/**
 *
 * @author Administrador
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class UsuarioServico extends Servico<Usuario>{
    
    public List<Usuario> todosUsuarios(){
        return super.getEntidades("Usuario.TODOS");
    }
    
    public Usuario retornaUsuario(String login){
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.USUARIO_POR_LOGIN", Usuario.class);
        
        query.setParameter(1, login);
        
       return query.getSingleResult();
    }
    
}
