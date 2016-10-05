package acesso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.USUARIO_POR_LOGIN", query = "SELECT u FROM Usuario u WHERE u.login = ?1"),
    @NamedQuery(name = "Usuario.TODOS", query = "SELECT u FROM Usuario u ORDER BY u.nome")
})
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC_USUARIO", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Usuario(String nome, String login, String senha){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.grupos = new ArrayList<>();
    }
    
    public Usuario(){
        this.nome = "";
        this.grupos = new ArrayList<>();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;
    
    @Transient
    protected String tipo;
    
    @NotBlank
    @Size(max = 30)
    //@Pattern(regexp = "\\p{Upper}{1}", message = "O nome deve ter inicial maiúscula!")
    @Column(name = "NOME")
    private String nome;
    
    @NotBlank
    @Size(max=45)
    //@Pattern(regexp = "((?=.*\\p{Digit})(?=.*\\p{Lower}))", message = "A senha deve conter, ao menos, um dígito e uma letra maiúscula!")
    @Column(name = "SENHA")
    private String senha;
    
    @NotBlank
    @Size(max = 35)
    @Column(name = "LOGIN")
    private String login;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_USUARIO_GRUPO")
    private List<Grupo> grupos;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public abstract String getTipo();

    public abstract void setTipo(String tipo);
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    @Override
    public String toString(){
        return this.getNome();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nome != null ? nome.hashCode() : 0);
        return hash;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
    public void addGrupo(Grupo g){
        grupos.add(g);
        g.addUsuario(this);
    }

    public boolean equals(Usuario usu) {
        return this.login.equals(usu.login);
    }
    
    
    
}
