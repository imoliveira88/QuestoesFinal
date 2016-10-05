package acesso;

import javax.persistence.*;

@Entity
@Table(name = "TB_ADMINISTRADOR")
@PrimaryKeyJoinColumn(name = "ID_ADMINISTRADOR")
@DiscriminatorValue(value = "A")
public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;
    
    public Administrador(String nome, String login, String senha){
        super(nome,login,senha); 
    }
    
    public Administrador(){};
    
    @Override
    public String getTipo(){
        return "Admin";
    }
    
    @Override
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
}
