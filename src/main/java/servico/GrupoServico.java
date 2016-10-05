/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionManagementType.CONTAINER;

import acesso.Grupo;
import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class GrupoServico extends Servico<Grupo> {
    @TransactionAttribute(SUPPORTS)       
    @PermitAll    
    public Grupo getGrupo(String nomeGrupo) {
        TypedQuery<Grupo> query = entityManager.createNamedQuery("Grupo.GRUPO_POR_NOME", Grupo.class);
        query.setParameter("nome", nomeGrupo);
        
        return query.getSingleResult();
    }
    
    public Grupo getGrupo(Long id){
        Grupo grupo;
        
        grupo = entityManager.find(Grupo.class, id);
        
        return grupo;
    }
}
