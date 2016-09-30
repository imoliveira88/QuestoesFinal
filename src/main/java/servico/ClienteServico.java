/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import acesso.Cliente;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import acesso.Usuario;
import excecao.ExcecaoNegocio;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class ClienteServico extends Servico<Usuario> {
    @EJB
    private GrupoServico grupoService;

    public boolean salvar(Usuario usuario) throws ExcecaoNegocio {
        if(!checarExistencia("Usuario.USUARIO_POR_LOGIN", usuario.getLogin())){
            usuario.addGrupo(grupoService.getGrupo("COMUM"));
            entityManager.persist(usuario);
            return true;
        }
        else return false; //Já existe, portanto não salvou
    }
    
    public Cliente retornaCliente(String cliente){
        TypedQuery<Cliente> query = entityManager.createNamedQuery("Usuario.USUARIO_POR_LOGIN", Cliente.class);
        
        query.setParameter(1, cliente);
        
       return query.getSingleResult();
    }
}
