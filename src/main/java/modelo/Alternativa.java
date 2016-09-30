/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import beans.BaseEntity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author MPR
 */
@Entity
@Table(name = "TB_ALTERNATIVA")
public class Alternativa implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ALTERNATIVA")
    private Long id;
    
    public Alternativa(){};
    
    public Alternativa(String alt){
        this.texto = alt;
    }
    
    @Column(name = "TEXTO")
    private String texto;
    
    @Column(name = "FIGURA")
    private Byte[] figura;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_QUESTAO", referencedColumnName = "ID_QUESTAO")
    private Questao questao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Byte[] getFigura() {
        return figura;
    }

    public void setFigura(Byte[] figura) {
        this.figura = figura;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return ((Alternativa) object).getTexto().equals(this.texto);
    }

    @Override
    public String toString() {
        return this.texto;
    }
    
}
