package acesso;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import modelo.Cartao;
import modelo.Questao;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_CLIENTE")
@PrimaryKeyJoinColumn(name = "ID_CLIENTE")
@DiscriminatorValue(value = "C")
public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;
    
    public Cliente(String nome, String login, String senha, String skin, Cartao cartao){
        super(nome,login,senha,skin); 
        this.questoes = new ArrayList<>();  
        this.cartao = cartao;
    }
    
    public Cliente(){
        this.questoes = new ArrayList<>();
    }
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CARTAO", referencedColumnName = "ID_CARTAO")
    private Cartao cartao;
    
    //Questões resolvidas pelos clientes
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TB_CLIENTES_QUESTOES")
    private List<Questao> questoes;
    
    @Column(name="CORRETAS")
    private int corretas;
    
    @Column(name="ERRADAS")
    private int erradas;
    
    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
    
    public void addQuestao(Questao questao){
        questoes.add(questao);
    }
    
    @Override
    public String getTipo(){
        return "Cliente";
    }
    
    @Override
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public int getCorretas() {
        return corretas;
    }

    public void setCorretas(int corretas) {
        this.corretas = corretas;
    }

    public int getErradas() {
        return erradas;
    }

    public void setErradas(int erradas) {
        this.erradas = erradas;
    }
    
    
    
}
