/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import acesso.Cliente;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.PieChartModel;
import servico.ClienteServico;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private PieChartModel pieModel1;
    
    @EJB
    ClienteServico clienteServico;
 
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
     
     
    private void createPieModels() {
        createPieModel1();
    }
 
    private void createPieModel1() throws EJBException {
        pieModel1 = new PieChartModel();

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            Cliente cliente;

            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            cliente = (Cliente) session.getAttribute("usuarioDaSessao");
            
            cliente = clienteServico.retornaCliente(cliente.getLogin());

            pieModel1.set("Erros", cliente.getErradas());
            pieModel1.set("Acertos", cliente.getCorretas());

            pieModel1.setTitle("Estatística de Resolução do Usuário");
            pieModel1.setLegendPosition("w");
        } catch (Exception e) {

        }
    }
     
}
