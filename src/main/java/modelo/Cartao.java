package modelo;

import beans.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Table(name = "TB_CARTAO")
public class Cartao implements Serializable, BaseEntity{
    private static final long serialVersionUID = 1L;
    
    public Cartao(Bandeira b, String num, Date valid){
        this.bandeira = b;
        this.numero = num;
        this.validade = valid;
    }
    
    public Cartao(){};
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARTAO")
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BANDEIRA", referencedColumnName = "ID_BANDEIRA")
    private Bandeira bandeira;
    
    @NotNull
    //@CreditCardNumber
    //@Size(max = 16, min = 16)
    @Column(name = "CARTAO_NUMERO")
    private String numero;
    
    @Future
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "CARTAO_VALIDADE")
    private Date validade;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cartao other = (Cartao) obj;
        if (!Objects.equals(this.bandeira, other.bandeira)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.validade, other.validade)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.bandeira);
        hash = 79 * hash + Objects.hashCode(this.numero);
        hash = 79 * hash + Objects.hashCode(this.validade);
        return hash;
    }
   
  
}

