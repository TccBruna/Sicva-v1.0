/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.VacinaDao;
import br.com.sicva.model.Vacina;
import br.com.sicva.util.Mensagens;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class VacinaControle {
    private Vacina vacina;
    private VacinaDao vacinaDao; 
    private String VacinaPesquisada;
    
    public void salvar(){
      vacinaDao = new VacinaDao();
        try {
            vacinaDao.salvarVacina(vacina);
            vacina = new Vacina();
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }
    
    public void alterar(){
        vacinaDao = new VacinaDao();
        try {
            vacinaDao.alterarVacina(vacina);
            vacina = new Vacina();
            VacinaPesquisada = "";
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }
    
    public void pesquisar(){
        vacinaDao = new VacinaDao();
        try {
            vacina = vacinaDao.consultarPorNome(VacinaPesquisada);
            if (vacina == null) {
                 new Mensagens().MensagensAviso("Nenhuma encontrada com o nome: "+VacinaPesquisada,null);
            }else{
                new Mensagens().MensagensSucesso("Vacina Encontrada", null);                
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }        
    }

    public Vacina getVacina() {
        if(vacina == null){
            vacina = new Vacina();
        }
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }   

    public String getVacinaPesquisada() {
        return VacinaPesquisada;
    }

    public void setVacinaPesquisada(String VacinaPesquisada) {
        this.VacinaPesquisada = VacinaPesquisada;
    }   
    
            
}
