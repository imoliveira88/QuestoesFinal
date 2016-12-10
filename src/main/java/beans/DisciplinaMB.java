/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import excecao.ExcecaoNegocio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import modelo.Disciplina;
import servico.DisciplinaServico;

@ManagedBean(name = "disciplinaMB")
@RequestScoped
public class DisciplinaMB extends BeanGeral{

    private String disciplina;
    private String descricao;
    private List<Disciplina> disciplinas;
    
    @EJB
    DisciplinaServico discServico;

    public DisciplinaMB() {
        this.disciplinas = new ArrayList<>();
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina.toUpperCase();
    }

    public List<Disciplina> getDisciplinas() {
        return discServico.todasDisciplinas();
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String salvar() throws ParseException, ExcecaoNegocio, EJBException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try{
            
            if(discServico.salvar(new Disciplina(disciplina))){
                this.addMensagem("Cadastro feito com sucesso!");
                this.setDisciplina("");
            }else{
                this.addMensagem("Já existe uma disciplina com o mesmo nome!");
            }
          
            return "disciplina";
        }catch(Exception e){
            this.addMensagem("Houve uma falha! Tente novamente!");
            return "disciplina";
        }
    }
    
    public String excluir() throws ExcecaoNegocio,EJBException{
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Disciplina disc = discServico.retornaDisciplina(disciplina);
            discServico.excluir(disc);
            this.disciplinas.remove(disc);
            this.addMensagem("Disciplina excluída com sucesso!");
            this.setDisciplina("");
            return "disciplina";
        }catch(Exception e){
            this.addMensagem("Houve um erro na exclusão! Provavelmente a disciplina está relacionada a alguma questão salva no banco!");
            return "disciplina";
        }
    }
}
