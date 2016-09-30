package beans;

import excecao.ExcecaoNegocio;
import java.text.ParseException;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.Organizadora;
import servico.OrganizadoraServico;

@ManagedBean(name = "organizadoraMB")
@RequestScoped
public class OrganizadoraMB{

    private Organizadora organizadora;
    private List<Organizadora> organizadoras;
    
    @EJB
    OrganizadoraServico orgServico;

    public OrganizadoraMB() {
        this.organizadora = new Organizadora();
        this.organizadoras = new ArrayList<>();
    }

    public Organizadora getOrganizadora() {
        return organizadora;
    }

    public void setOrganizadora(Organizadora organizadora) {
        this.organizadora = organizadora;
    }

    public List<Organizadora> getOrganizadoras() {
        return orgServico.todasOrganizadoras();
    }

    public void setOrganizadoras(List<Organizadora> organizadoras) {
        this.organizadoras = organizadoras;
    }

    public String salvar() throws ExcecaoNegocio,ParseException,EJBException {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        try{
            if(orgServico.salvar(organizadora)){
                msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Cadastro feito com sucesso!");
                context.addMessage("destinoAviso", msg);
            }else{
                msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Já existe uma organizadora com o nome escolhido!");
                context.addMessage("destinoAviso", msg);
            }
            return "organizadora";
        }catch(Exception e){
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Houve uma falha no cadastro! Atente para os formatos válidos dos campos e tente novamente!");
            context.addMessage("destinoAviso", msg);
            return "organizadora";
        }
        
        
        
        
    }
    
    public String excluir() throws ParseException,ExcecaoNegocio,EJBException{
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try{
            orgServico.excluir(organizadora);
            this.organizadoras.remove(organizadora);
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Organizadora excluída com sucesso!");
            context.addMessage("destinoAviso", msg);
            return "organizadora";
        }catch(Exception e){
            msg = new FacesMessage(FacesMessage.FACES_MESSAGES,"Houve uma falha na exclusão da organizadora. Provavelmente o registro está relacionado a outros persistidos no banco.");
            context.addMessage("destinoAviso", msg);
            return "organizadora";
        }
    }
}
