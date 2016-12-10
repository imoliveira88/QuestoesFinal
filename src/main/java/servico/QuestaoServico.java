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
import modelo.Organizadora;
import modelo.Questao;

/**
 *
 * @author Administrador
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class QuestaoServico extends Servico<Questao> {

    public boolean checarExistencia(Questao questao){
        List<Questao> questoes;
        questoes = getEntidades("Questao.TODAS");
        for(Questao q : questoes){
            if(q.equals(questao)) return true;
        }
        return false;
    }
    
    public Questao retornaQuestao(String enunciado){
        TypedQuery<Questao> query = entityManager.createNamedQuery("Questao.QUESTAO_POR_ENUNCIADO", Questao.class);
        
        query.setParameter(1, enunciado);
        
       return query.getSingleResult();
    }
    
    public Questao retornaQuestao(long id){
        TypedQuery<Questao> query = entityManager.createNamedQuery("Questao.QUESTAO_POR_ID", Questao.class);
        
        query.setParameter(1, id);
        
       return query.getSingleResult();
    }
    
    public long retornaIdMaximo(){
        TypedQuery<Questao> query = entityManager.createNamedQuery("Questao.IDMAXIMO", Questao.class);
        
       return ((Questao) query).getId();
    }
    
    public boolean salvar(Questao questao) throws ExcecaoNegocio {
        if(!checarExistencia(questao)){
            entityManager.persist(questao);
            return true;
        }
       else return false;
    }
    
    public List<Questao> todasQuestoes(){
        return super.getEntidades("Questao.TODAS");
    }

    public List<Questao> questoesCriterio(Disciplina disciplina, Organizadora organizadora) {
        TypedQuery<Questao> query = entityManager.createNamedQuery("Questao.QUESTOES_POR_DISC_ORG", Questao.class);

        query.setParameter(1, disciplina);
        query.setParameter(2, organizadora);
        
        return query.getResultList();
    }
}
