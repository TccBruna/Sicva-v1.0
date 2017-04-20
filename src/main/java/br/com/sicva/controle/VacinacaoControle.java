/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.AplicaDao;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.dao.VacinaDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Aplica;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Vacina;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.DataUtil;
import br.com.sicva.util.Mensagens;
import br.com.sicva.util.PaginationHelper;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
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
    private AplicaDao aplicaDao;
    private List<Aplica> aplicacoes;
    private List<Vacinacao> vacinacoes;
    private DataModel items = null;
    private PaginationHelper pagination;

    public void pesquisarCartao() {
        PacienteDao pacienteDao = new PacienteDao();
        try {
            paciente = pacienteDao.PesquisarPaciente(paciente.getPacCpf());            
            if (paciente == null) {
                new Mensagens().MensagensAviso("Paciente não encontrado: ", null);
                aplicacoes = null;
            } else {
                aplicaDao = new AplicaDao();
                new Mensagens().MensagensSucesso("Paciente encontrado", null);
                aplicacoes = aplicaDao.buscarAplicacoes(paciente.getPacCpf());
                recreatePagination();
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
                vacinacaoDao.salvarVacinacao(vacinacao);
                recreatePagination();;
                new Mensagens().MensagensSucesso("Vacina Inserida com sucesso", null);
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
   

    public String aplicar() {
        return "registro_aplicacao?faces-redirect=true";
    }
    public String novo() {
        if(paciente == null){
            new Mensagens().MensagensAviso("Pesquise um paciente primerio", null);
            return "/";
        }
        return "inserir_vacina?faces-redirect=true";
    }  
    
    
     public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(5) {

                @Override
                public int getItemsCount() {
                    return vacinacoes.size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(vacinacaoDao.buscarAlcance(paciente.getPacCpf(),
                            new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "cartao_paciente";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "cartao_paciente";
    }
  
    public List<Aplica> getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(List<Aplica> aplicacoes) {
        this.aplicacoes = aplicacoes;
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
