package beans;

import excecao.ExcecaoNegocio;
import java.text.ParseException;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.Organizadora;
import servico.OrganizadoraServico;

@ManagedBean(name = "organizadoraMB")
@RequestScoped
public class OrganizadoraMB extends BeanGeral{

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
        this.organizadora = new Organizadora(organizadora.getDescricao().toUpperCase());
    }

    public List<Organizadora> getOrganizadoras() {
        return orgServico.todasOrganizadoras();
    }

    public void setOrganizadoras(List<Organizadora> organizadoras) {
        this.organizadoras = organizadoras;
    }

    public String salvar() throws ExcecaoNegocio,ParseException,EJBException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try{
            if(orgServico.salvar(organizadora)){
                this.addMensagem("Cadastro feito com sucesso!");
                this.setOrganizadora(null);
            }else{
                this.addMensagem("Já existe uma organizadora com o mesmo nome!");
            }
            return "organizadora";
        }catch(Exception e){
            this.addMensagem("Houve uma falha! Tente novamente!");
            return "organizadora";
        }
     
    }
    
    public String excluir() throws ParseException,ExcecaoNegocio,EJBException{
        FacesContext context = FacesContext.getCurrentInstance();

        try{
            orgServico.excluir(organizadora);
            this.organizadoras.remove(organizadora);
            this.addMensagem("Organizadora excluída com sucesso!");
            this.setOrganizadora(null);
            return "organizadora";
        }catch(Exception e){
            this.addMensagem("Houve uma falha na exclusão da organizadora. Provavelmente o registro está relacionado a outros persistidos no banco.");

            return "organizadora";
        }
    }
}
