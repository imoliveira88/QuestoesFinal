/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import excecao.ExcecaoNegocio;
import servico.DisciplinaServico;
import servico.Servico;

/**
 *
 * @author Administrador
 */
public class Main {
    
    public static void main(String[] args) throws ExcecaoNegocio{
        DisciplinaServico disc = new DisciplinaServico();
        
        //if(Servico.igualMaximaVerossimilhanca("HISTORIA DO BRASIL", "HISTORIA DO BRASKH")) System.out.println("igual");
        //else System.out.println("diferente");
        
        if(disc.checarExistenciaVerossimilhanca("raciocinio logire")) System.out.println("Teve igual vero");
    }
    
}
