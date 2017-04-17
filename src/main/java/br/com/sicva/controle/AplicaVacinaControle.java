/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.AplicaDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Aplica;
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Usuario;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.Mensagens;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mauricio
 */
@ManagedBean
@SessionScoped
public class AplicaVacinaControle {    
    private Aplica aplica;
    private AplicaDao aplicaDao;
    private Vacinacao vacinacao;
    private VacinacaoDao vacinacaoDao;
    
    public String Aplicacao() {
        aplicaDao = new AplicaDao();
        vacinacaoDao = new VacinacaoDao();
        try {
            aplica.setVacinacao(vacinacao);
            aplica.setAplicaData(new Date());
            aplica.setEnfermeiro(new Enfermeiro("123456", new Usuario()));
            if (aplicaDao.salvarAplica(aplica)) {
                vacinacao.setVacinacaoStatus("OK");
                vacinacaoDao.alterarVacinacao(vacinacao);
                new Mensagens().MensagensSucesso("Salvo com sucesso", null);
                return "cartao_paciente?faces-redirect=true";
            } else {
                return "registro_aplicacao?faces-redirect=true";
            }

        } catch (Exception e) {
            System.out.println(e);
            new Mensagens().MensagensErroFatal("Erro na transação", null);
            return "registro_aplicacao?faces-redirect=true";
        }
    }
    
    public String onload(){
        return "cartao_paciente?faces-redirect=true";
    }
    
    public Aplica getAplica() {
        if (aplica == null) {
            aplica = new Aplica();
        }
        return aplica;
    }

    public void setAplica(Aplica aplica) {
        this.aplica = aplica;
    }
    
    public Vacinacao getVacinacao() {  
        if(vacinacao == null){
            vacinacao = new Vacinacao();
        }
        return vacinacao;
    }

    public void setVacinacao(Vacinacao vacinacao) {
        this.vacinacao = vacinacao;
    }
}
