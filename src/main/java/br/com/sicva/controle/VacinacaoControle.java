/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.AplicaDao;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Aplica;
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Usuario;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.Mensagens;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@SessionScoped
public class VacinacaoControle {

    private List<Vacinacao> vacinacaos;
    private Vacinacao vacinacao;
    private VacinacaoDao vacinacaoDao;
    private Paciente paciente;
    private Aplica aplica;
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
            return  true;
        } else {
            return false;
        }
    }
    
    public String verificarStatus(String Status, String Faixa, Date DataNascimento){
        Calendar hj= Calendar.getInstance();
        Calendar dataNasci = Calendar.getInstance();
        dataNasci.setTime(DataNascimento);
        if(Status.equals("PENDENTE")){ 
           int IdadeAno = hj.get(Calendar.YEAR) - dataNasci.get(Calendar.YEAR);
           if(hj.get(Calendar.MONTH) < dataNasci.get(Calendar.MONDAY)){
               System.out.println("Ainda não completou ano");
           }
           int IdadeDias =  hj.get(Calendar.DAY_OF_MONTH) - dataNasci.get(Calendar.DAY_OF_MONTH);
            System.out.println(""+IdadeAno+IdadeDias);
            return "PENDENTE";
        }
        return Status;
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

}
