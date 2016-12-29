/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import beans.BaseEntity;
import java.io.Serializable;
import java.util.Objects;
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
    @NamedQuery(name = "Disciplina.TODAS", query = "SELECT o FROM Disciplina o ORDER BY o.descricao"),
    @NamedQuery(name = "Disciplina.DISCIPLINA_POR_NOME", query = "SELECT o FROM Disciplina o WHERE o.descricao = ?1")
})
@Table(name = "TB_DISCIPLINA")
public class Disciplina implements Serializable, BaseEntity{
    
    public Disciplina(String desc){
        this.descricao = desc;
    }
    
    public Disciplina(){  
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_DISCIPLINA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 30)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescrição(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        if(this.id == 0)  return "";
        return this.descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.descricao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(((Disciplina) obj).id == 0) return true;
        return ((Disciplina) obj).getDescricao().equals(this.descricao);
    }
    
}
