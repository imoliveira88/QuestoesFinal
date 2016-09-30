/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import acesso.Usuario;
import excecao.ExcecaoNegocio;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class AdministradorServico extends Servico<Usuario> {
    @EJB
    private GrupoServico grupoService;

    public boolean salvar(Usuario usuario) throws ExcecaoNegocio {
        if(!checarExistencia("Usuario.USUARIO_POR_LOGIN", usuario.getLogin())){
            usuario.addGrupo(grupoService.getGrupo("ADMIN"));
            entityManager.persist(usuario);
            return true;
        }
        else return false;
    }
}
