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
import modelo.Organizadora;

@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class OrganizadoraServico extends Servico<Organizadora> {

    
    public boolean salvar(Organizadora organizadora) throws ExcecaoNegocio {
        if(!checarExistencia("Organizadora.ORGANIZADORA_POR_NOME", organizadora.getDescricao())){
            entityManager.persist(organizadora);
            return true;
        }
        else return false;
    }
    
    public List<Organizadora> todasOrganizadoras(){
        return super.getEntidades("Organizadora.TODAS");
    }
    
    public Organizadora retornaOrganizadora(String organizadora){
        TypedQuery<Organizadora> query = entityManager.createNamedQuery("Organizadora.ORGANIZADORA_POR_NOME", Organizadora.class);
        
        query.setParameter(1, organizadora);
        
       return query.getSingleResult();
    }
}
