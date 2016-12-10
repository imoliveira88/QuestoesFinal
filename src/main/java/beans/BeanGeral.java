/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrador
 */
public abstract class BeanGeral {
    
    public void addMensagem(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        msg = new FacesMessage("",mensagem);
        context.addMessage("destinoAviso", msg);
    }
    
}
