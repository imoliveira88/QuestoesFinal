/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import excecao.ExcecaoNegocio;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ADMINISTRADOR
 * @param <T> A classe do serviço
 */
public abstract class Servico<T> {

    @PersistenceContext(name = "QuestoesPU", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;

    @TransactionAttribute(SUPPORTS)
    public List<T> getEntidades(String nomeQuery) {
        List<T> resultado = new ArrayList<>();
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);
        resultado = query.getResultList();
        return resultado;
    }
    
    public void excluir(T entidade){
        entidade = entityManager.merge(entidade);
        entityManager.remove(entidade);
    }

    @TransactionAttribute(SUPPORTS)
    protected List<T> getEntidades(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }

    @TransactionAttribute(SUPPORTS)
    protected T getEntidade(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getSingleResult();
    }

    @TransactionAttribute(SUPPORTS)
    protected boolean checarExistencia(String nomeQuery, Object parametro) throws ExcecaoNegocio {
        T entidade;

        try {
            entidade = getEntidade(nomeQuery, new Object[]{parametro});
            if (entidade != null) {
                return true;
            }
        } catch (Exception e) {
                return false;
            }
        return false;
    }
    
    @TransactionAttribute(SUPPORTS)
    protected void checarNaoExistencia(String nomeQuery, Object[] parametros)
            throws ExcecaoNegocio {
        try {
            getEntidade(nomeQuery, parametros);
        } catch (NoResultException ex) {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
        }
    }
    
}
