package modelo;

import beans.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import servico.BandeiraServico;

@Entity
@ManagedBean(name = "bandeiraMB")
@SessionScoped
@Table(name="TB_BANDEIRA")
@NamedQueries({
    @NamedQuery(name = "Bandeira.TODAS", query = "SELECT e FROM Bandeira e ORDER BY e.nome"),
    @NamedQuery(name = "Bandeira.BANDEIRA_POR_NOME", query = "SELECT u FROM Bandeira u WHERE u.nome = ?1")
})
public class Bandeira implements Serializable, BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    public Bandeira(){
        this.cartoes = new ArrayList<>();
    }
    
    public Bandeira(String n){
        this.cartoes = new ArrayList<>();
        this.nome = n;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BANDEIRA")
    private Long id;
    
    @OneToMany(mappedBy = "bandeira", fetch = FetchType.LAZY)
    private final List<Cartao> cartoes;
    
    @NotBlank
    @Size(min = 3, max = 15)
    @Column(name = "BANDEIRA_NOME")
    private String nome;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
  
    public void addCartao(Cartao c){
        this.cartoes.add(c);
    }
    
    @EJB
    @Transient
    BandeiraServico bServico;
    
    public List<Bandeira> getListaBandeiras(){
        return bServico.todasBandeiras();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Bandeira) obj).getNome().equals(this.nome);
    }
    
    @Override
    public String toString(){
        return this.nome;
    }
    
}
