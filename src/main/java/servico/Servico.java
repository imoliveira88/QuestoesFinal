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

    @PersistenceContext(name = "QuestoesUP", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;
    
    public void excluir(T entidade){
        entidade = entityManager.merge(entidade);
        entityManager.remove(entidade);
    }

    @TransactionAttribute(SUPPORTS)
    protected List<T> getEntidades(String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        return query.getResultList();
    }
    
    @TransactionAttribute(SUPPORTS)
    public static boolean igualMaximaVerossimilhanca(String a, String b){
        int soma = 0;
        int tamanho = a.length();
        double razao;
        
        String a1 = a.toUpperCase();
        String b1 = b.toUpperCase();
        
        if(tamanho <= 5 && b.length() <= 5) return a.equals(b);
        
        if(Math.abs(a.length()-b.length()) < 2){
            for(int i=0; i< tamanho; i++){
                System.out.println("a: " + a1.charAt(i) + " b: " + b1.charAt(i));
                if(a1.charAt(i) == b1.charAt(i)){
                    System.out.println("caraceres iguais");
                    soma++;
                }
                else System.out.println("caracteres diferentes!");
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
    public boolean checarExistenciaVerossimilhanca(String nomeQuery, String parametro) throws ExcecaoNegocio {
        List<T> entidades;
        
        String maiusculo = parametro.toUpperCase();

        try {
            entidades = getEntidades(nomeQuery);
            for(int i=0; i<entidades.size(); i++){
                System.out.println("Disciplina: " + maiusculo + " atual: " + entidades.get(i).toString());
                if(Servico.igualMaximaVerossimilhanca(maiusculo, entidades.get(i).toString())) return true;
            }
        } catch (Exception e) {
            System.out.println("Teve um erro massa por aqui! ");
            e.printStackTrace();
                return false;
        }
        return false;
    }
    
}
