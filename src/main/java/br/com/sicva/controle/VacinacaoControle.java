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
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Usuario;
import br.com.sicva.model.Vacina;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.DataUtil;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class VacinacaoControle {

    private List<Vacinacao> vacinacaos;
    private Vacinacao vacinacao = new Vacinacao();
    private VacinacaoDao vacinacaoDao;
    private Paciente paciente;
    private Aplica aplica;
    private Vacina vacina;
    private AplicaDao aplicaDao;

    public void pesquisarCartao() {
        PacienteDao pacienteDao = new PacienteDao();
        try {
            paciente = pacienteDao.PesquisarPaciente(paciente.getPacCpf());
            if (paciente == null) {
                new Mensagens().MensagensAviso("Paciente não encontrado: ", null);
            } else {
                vacinacaoDao = new VacinacaoDao();
                vacinacaos = vacinacaoDao.buscarVacinacao(paciente.getPacCpf());
                new Mensagens().MensagensSucesso("Paciente encontrado", null);
            }
        } catch (Exception e) {
            System.out.println("" + e);
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }

    }

    public String realizarAplicacao() {
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

    public boolean habilitarBotão(String status) {
        if (status.equals("PENDENTE")) {
            return true;
        } else {
            return false;
        }
    }

    public int getIdade(){
        if(paciente.getPacDtnasc() != null)  {         
          return new DataUtil().calcularIdade(paciente.getPacDtnasc());
        }        
        return 0;
    }
    
    public void test(){
        if (vacinacaos == null) {
           vacinacaos = new ArrayList<>();
           
        }
        vacinacao.setVacina(vacina);
        vacinacao.setVacinacaoStatus("Pendente");        
        vacinacaos.add(vacinacao); 
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
    
    

    public List<Vacinacao> getVacinacaos() {
        return vacinacaos;
    }

    public void setVacinacaos(List<Vacinacao> vacinacaos) {
        this.vacinacaos = vacinacaos;
    }

    public Vacinacao getVacinacao() {        
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

    public Aplica getAplica() {
        if (aplica == null) {
            aplica = new Aplica();
        }
        return aplica;
    }

    public void setAplica(Aplica aplica) {
        this.aplica = aplica;
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
    

}
