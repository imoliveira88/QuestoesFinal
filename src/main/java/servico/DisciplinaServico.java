/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.TransactionManagement;
import static javax.ejb.TransactionManagementType.CONTAINER;
import javax.persistence.TypedQuery;
import modelo.Disciplina;

/**
 *
 * @author Administrador
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class DisciplinaServico extends Servico<Disciplina>{
    
    public boolean salvar(Disciplina disciplina) throws ExcecaoNegocio {
        if(!checarExistenciaVerossimilhanca("Disciplina.TODAS", disciplina.getDescricao())){
            entityManager.persist(disciplina);
            return true;
        }
        return false;
    }
    
    public List<Disciplina> todasDisciplinas(){
        return super.getEntidades("Disciplina.TODAS");
    }
    
    public Disciplina retornaDisciplina(String disciplina){
        TypedQuery<Disciplina> query = entityManager.createNamedQuery("Disciplina.DISCIPLINA_POR_NOME", Disciplina.class);
        
        query.setParameter(1, disciplina);
        
       return query.getSingleResult();
    }
    
}
