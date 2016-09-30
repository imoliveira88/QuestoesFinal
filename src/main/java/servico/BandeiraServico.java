/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;

import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import modelo.Bandeira;

@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class BandeiraServico extends Servico<Bandeira> {

    public boolean salvar(Bandeira bandeira) throws ExcecaoNegocio {
        if(!checarExistencia("Bandeira.BANDEIRA_POR_NOME", bandeira.getNome())){
            entityManager.persist(bandeira);
            return true;
        }
        else return false;
    }
    
    public Bandeira retornaBandeira(String bandeira){
        TypedQuery<Bandeira> query = entityManager.createNamedQuery("Bandeira.BANDEIRA_POR_NOME", Bandeira.class);
        
        query.setParameter(1, bandeira);
        
       return query.getSingleResult();
    }
    
    public List<Bandeira> todasBandeiras(){
        return super.getEntidades("Bandeira.TODAS");
    }
}
