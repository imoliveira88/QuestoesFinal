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
    public static boolean igualMaximaVerossimilhanca(String a, String b){
        int soma = 0;
        int tamanho = a.length();
        double razao;
        
        if(tamanho <= 5 && b.length() <= 5) return a.equals(b);
        
        if(Math.abs(a.length()-b.length()) < 2){
            for(int i=0; i< tamanho; i++){
                System.out.println("a: " + a.charAt(i) + " b: " + b.charAt(i));
                if(a.charAt(i) == b.charAt(i)){
                    System.out.println("caraceres iguais");
                    soma++;
                }
            }
        }else return false; //diferença de tamanho de dois ou mais
        
        razao = (double) soma/tamanho;
        
        System.out.println("Soma: " + soma + "divisão " + soma/tamanho);
        
        System.out.println("tamanho de a: " + a.length() + "  tamanho de b: " + b.length() + " soma/tamanho = " + razao + " soma-tamanho = " + Math.abs(soma-tamanho));
        
        return (razao >= 0.8 && Math.abs(soma - tamanho) <= 2);
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
    protected boolean checarExistenciaVerossimilhanca(String nomeQuery, String parametro) throws ExcecaoNegocio {
        List<T> entidades;
        
        String maiusculo = parametro.toUpperCase();

        try {
            entidades = getEntidades(nomeQuery, new Object[]{parametro});
            for(int i=0; i<entidades.size(); i++){
                if(this.igualMaximaVerossimilhanca(maiusculo, entidades.get(i).toString())) return true;
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
