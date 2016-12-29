/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import beans.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MPR
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Organizadora.TODAS", query = "SELECT o FROM Organizadora o ORDER BY o.descricao"),
    @NamedQuery(name = "Organizadora.ORGANIZADORA_POR_NOME", query = "SELECT o FROM Organizadora o WHERE o.descricao = ?1")
})
@Table(name = "TB_ORGANIZADORA")
public class Organizadora implements Serializable, BaseEntity {
    
    public Organizadora(String desc){
        this.descricao = desc;
    }
    
    public Organizadora(){
        
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_ORGANIZADORA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(((Organizadora) object).id == 0) return true;
        return ((Organizadora) object).getDescricao().equals(this.descricao);
    }

    @Override
    public String toString() {
        if(this.id == 0)  return "";
        return this.descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
