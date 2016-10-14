/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import acesso.Cliente;
import beans.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import validacao.ValidaAno;

/**
 *
 * @author MPR
 */

@Entity
@NamedQueries({
    @NamedQuery(name = "Questao.TODAS", query = "SELECT o FROM Questao o ORDER BY o.id"),
    @NamedQuery(name = "Questao.QUESTOES_POR_DISCIPLINA", query = "SELECT o FROM Questao o WHERE o.disciplina = ?1"),
    @NamedQuery(name = "Questao.QUESTOES_POR_ORGANIZADORA", query = "SELECT o FROM Questao o WHERE o.organizadora = ?1"),
    @NamedQuery(name = "Questao.QUESTOES_POR_DISC_ORG", query = "SELECT o FROM Questao o WHERE o.disciplina = ?1 AND o.organizadora = ?2"),
    @NamedQuery(name = "Questao.QUESTAO_POR_ENUNCIADO", query = "SELECT o FROM Questao o WHERE o.enunciado1 = ?1"),
    @NamedQuery(name = "Questao.QUESTAO_POR_ID", query = "SELECT o FROM Questao o WHERE o.id = ?1")
})
@Table(name = "TB_QUESTAO")        
public class Questao implements Serializable, BaseEntity{
    
    public Questao(){
        this.clientes = new ArrayList<>();
        this.alternativas = new ArrayList<>();
    }

    @Id
    @Column(name = "ID_QUESTAO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(mappedBy = "questoes")
    private List<Cliente> clientes;
    
    @Size(max = 500)
    @Column(name = "ENUNCIADO1")
    private String enunciado1;
    
    @OneToMany(mappedBy = "questao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Alternativa> alternativas;
    
    @NotNull
    @Column(name = "ALTERNATIVA_CORRETA")
    private char correta;
    
    @ValidaAno
    @Column(name = "ANO")
    private int ano;
    
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORGANIZADORA", referencedColumnName = "ID_ORGANIZADORA")
    private Organizadora organizadora;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DISCIPLINA", referencedColumnName = "ID_DISCIPLINA")
    private Disciplina disciplina;
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getCorreta() {
        return correta;
    }

    public void setCorreta(char correta) {
        this.correta = correta;
    }
    

    public String getEnunciado1() {
        return enunciado1;
    }

    public void setEnunciado1(String enunciado1) {
        this.enunciado1 = enunciado1;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }    

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }
    
    public void addAlternativa(Alternativa alt){
        this.alternativas.add(alt);
        alt.setQuestao(this);
    }
    
    public void addAlternativa(String alt){
        Alternativa alternativa = new Alternativa(alt);
        this.alternativas.add(alternativa);
        alternativa.setQuestao(this);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Organizadora getOrganizadora() {
        return organizadora;
    }

    public void setOrganizadora(Organizadora organizadora) {
        this.organizadora = organizadora;
    }    

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public void addCliente(Cliente cliente){
        clientes.add(cliente);
    }
    
    public int quantidadeAlternativas(){
        return this.alternativas.size();
    }
    
    public String imprimeQuestao(){
        String texto = this.enunciado1 + "\n\n";
            for(int i=0; i < this.quantidadeAlternativas(); i++){
                texto += (char)('A'+i) + ") " + this.alternativas.get(i).getTexto() + "\n";
            }
        return texto;
    }
    
    public boolean equals(Questao questao){
        return this.enunciado1.equals(questao.getEnunciado1());
    }
    
    public char retornaLetra(Alternativa a){
        for(int i = 0; i < alternativas.size(); i++){
            if(alternativas.get(i).equals(a)) return (char) ('A' + i);
        }
        
        return 'A';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.clientes);
        hash = 17 * hash + Objects.hashCode(this.enunciado1);
        hash = 17 * hash + Objects.hashCode(this.alternativas);
        hash = 17 * hash + this.correta;
        hash = 17 * hash + this.ano;
        hash = 17 * hash + Objects.hashCode(this.organizadora);
        hash = 17 * hash + Objects.hashCode(this.disciplina);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Questao other = (Questao) obj;
        return true;
    }
    
    
    
}
