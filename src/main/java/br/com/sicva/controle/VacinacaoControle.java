/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.PacienteDao;
import br.com.sicva.dao.VacinaDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Usuario;
import br.com.sicva.model.Vacina;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.DataUtil;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@SessionScoped
public class VacinacaoControle {

    private Vacinacao vacinacao;
    private VacinacaoDao vacinacaoDao;
    private Paciente paciente;
    private List<Vacinacao> vacinacoes;
   
    

    public void pesquisarCartao() {
        PacienteDao pacienteDao = new PacienteDao();
        try {
            paciente = pacienteDao.PesquisarPaciente(paciente.getPacCpf());  
            vacinacoes = null;
            if (paciente == null) {
                new Mensagens().MensagensAviso("Paciente não encontrado: ", null);                 
            } else {
                vacinacaoDao = new VacinacaoDao();
                vacinacoes = vacinacaoDao.buscarVacinacao(paciente.getPacCpf());
                new Mensagens().MensagensSucesso("Paciente encontrado", null);                    
            }
        } catch (Exception e) {
            System.out.println("" + e);
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }

    }

    public void inserir(){
        if(this.paciente.getPacCpf() == null){
            new Mensagens().MensagensAviso("Por Favor selecione um cartão de Vacina"
                    + " na página cartão de vacina ", null);           
        } else {
            try {
                vacinacaoDao = new VacinacaoDao(); 
                vacinacao.setVacinacaoStatus("PENDENTE");  
                vacinacao.setPaciente(paciente);
                if(vacinacaoDao.salvarVacinacao(vacinacao)){               
                new Mensagens().MensagensSucesso("Vacina Inserida com sucesso", null);
                }else{
                    new Mensagens().MensagensAviso("Vacina não pode ser Inserida", null);
                } 
            } catch (Exception e) {
                System.out.println("erro na classe vacinacaoControle:"+e);
                new Mensagens().MensagensErroFatal("Aconteceu um erro", null);
            }
        }
    }
    

    public boolean habilitarBotão(String status) {
        return status.equals("PENDENTE");
    }

    public int getIdade(){
        if(paciente.getPacDtnasc() != null)  {         
          return new DataUtil().calcularIdade(paciente.getPacDtnasc());
        }        
        return 0;
    }
       
    public List<SelectItem> getVacinas() {
        VacinaDao funcaoDao = new VacinaDao();
        List<Vacina> listaVacinas = funcaoDao.listarVacina();
        final List<SelectItem> itens = new ArrayList<>(listaVacinas.size());
        for (Vacina vacina : listaVacinas) {
            itens.add(new SelectItem(vacina, vacina.getVacinaNome()));
        }
        return itens;
    }  
   
    public String aplicacao() {
        
        vacinacaoDao = new VacinacaoDao();
        try {
           
            if (vacinacaoDao.alterarVacinacao(vacinacao)) {
                vacinacao.setVacinacaoStatus("OK");               
                
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
        

    public String aplicar() {
        return "registro_aplicacao?faces-redirect=true";
    }
    public String novo() {
        if(paciente.getPacCpf() == null){
            new Mensagens().MensagensAviso("Pesquise um paciente primerio", null);
            return "/";
        }else{
            return "inserir_vacina?faces-redirect=true";
        }
    }
    
    public Integer TotalDose(Integer id){
       return vacinacaoDao.QtdVacinacao(id);
    }
    
    public List<Vacinacao> getVacinacoes() {
        return vacinacoes;
    }

    public void setVacinacoes(List<Vacinacao> vacinacoes) {
        this.vacinacoes = vacinacoes;
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

    public Paciente getPaciente() {
        if (paciente == null) {
            paciente = new Paciente();
        }
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }    
    

}
