/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.EnfermeiroDao;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.dao.VacinaDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Vacina;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.DataUtil;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private int dia;
    private int mes;
    private int ano;

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

    public void inserir() {
        if (this.paciente.getPacCpf() == null) {
            new Mensagens().MensagensAviso("Por Favor selecione um cartão de Vacina"
                    + " na página cartão de vacina ", null);
        } else {
            try {
                int qtdDose = TotalDose(vacinacao.getVacina().getVacinaId(), paciente.getPacId());
                vacinacaoDao = new VacinacaoDao();
                if (vacinacao.getVacinacaoId() == null || vacinacao.getVacinacaoId() == 0) {
                    vacinacao.setVacinacaoStatus("IMUNIZADO");
                    vacinacao.setPaciente(paciente);
                    vacinacao.setVacinacaoDtAplicacao(new Date());
                    if (qtdDose <= 0) {
                        qtdDose = 1;
                    }
                    vacinacao.setVacinacaoDosagem("Dose  " + qtdDose);
                    if (vacinacaoDao.salvarVacinacao(vacinacao)) {
                        if (qtdDose < vacinacao.getVacina().getVacinaqdtedose()) {
                            gerarPróxima(vacinacao, qtdDose);
                        }
                        vacinacao = new Vacinacao();
                        new Mensagens().MensagensSucesso("Vacina aplicada com sucesso", null);
                    } else {
                        new Mensagens().MensagensAviso("Vacina não pode ser aplicada", null);
                    }
                } else {
                    vacinacao.setVacinacaoStatus("IMUNIZADO");
                    vacinacao.setVacinacaoDtAplicacao(new Date());
                    if (vacinacaoDao.alterarVacinacao(vacinacao)) {
                        if (qtdDose < vacinacao.getVacina().getVacinaqdtedose()) {
                            gerarPróxima(vacinacao, qtdDose);
                        }
                        vacinacao = new Vacinacao();
                        new Mensagens().MensagensSucesso("Vacina Atualizada com sucesso", null);
                    } else {
                        new Mensagens().MensagensAviso("Vacina não pode ser Atualizada", null);
                    }
                }
            } catch (Exception e) {
                System.out.println("erro na classe vacinacaoControle:" + e);
                new Mensagens().MensagensErroFatal("Aconteceu um erro", null);
            }
        }
        vacinacoes = vacinacaoDao.buscarVacinacao(paciente.getPacCpf());
    }

    public void gerarPróxima(Vacinacao vacinacao, int dose) {
        Vacinacao novaVacinacao = new Vacinacao();
        try {
            DataUtil util = new DataUtil();
            Date dataSomada = new Date();
            dataSomada = util.addDia(dataSomada, dia);
            dataSomada = util.addMes(dataSomada, mes);
            dataSomada = util.addAno(dataSomada, ano);
            novaVacinacao.setVacinacaoId(null);
            novaVacinacao.setVacinacaoProxDt(dataSomada);
            novaVacinacao.setVacinacaoStatus("PENDENTE");
            novaVacinacao.setPaciente(paciente);
            novaVacinacao.setVacina(vacinacao.getVacina());
            novaVacinacao.setVacinacaoDosagem("Dose " + (dose + 1));
            vacinacaoDao.salvarVacinacao(novaVacinacao);            
        } catch (Exception e) {
            System.err.println("erro ao gerar o cartão: " + e);
        }

    }

    public boolean habilitarBotão(String status) {        
        return status.equals("PENDENTE");
    }
    public boolean habilitarBotão() {
        if(vacinacao.getVacinacaoId() == null || vacinacao.getVacinacaoId() == 0){
            vacinacao.setVacinacaoStatus("");
        }
        return vacinacao.getVacinacaoStatus().equals("PENDENTE");
    }

    public boolean habilitarItem(Vacina vacina) {
        if (paciente.getPacId() != null) {
            int total = TotalDose(vacina.getVacinaId(), paciente.getPacId());
            return total >= vacina.getVacinaqdtedose();
        } else {
            return false;
        }
    }

    public int getIdade() {
        if (paciente.getPacDtnasc() != null) {
            return new DataUtil().calcularIdade(paciente.getPacDtnasc());
        }
        return 0;
    }

    public List<SelectItem> getVacinas() {
        VacinaDao funcaoDao = new VacinaDao();
        List<Vacina> listaVacinas = funcaoDao.listarVacina();
        final List<SelectItem> itens = new ArrayList<>(listaVacinas.size());
        for (Vacina vacina : listaVacinas) {
            itens.add(new SelectItem(vacina, vacina.getVacinaNome(), null, habilitarItem(vacina)));
        }
        return itens;
    }

    public List<SelectItem> getEnfermeiros() {
        EnfermeiroDao enfermeiroDao = new EnfermeiroDao();
        List<Enfermeiro> listaEnfermeiro = enfermeiroDao.listarEnfermeiro();
        final List<SelectItem> itens = new ArrayList<>(listaEnfermeiro.size());
        for (Enfermeiro e : listaEnfermeiro) {            
            itens.add(new SelectItem(e, e.getEnfermeiroCoren()));
        }
        return itens;
    }

    public String aplicar() {
        if (paciente.getPacCpf() == null) {
            new Mensagens().MensagensAviso("Pesquise um paciente primerio", null);
            return "/";
        } else {
            return "aplicar_vacina?faces-redirect=true";
        }
    }

    public Integer TotalDose(Integer vac_id, Integer pac_id) {
        return vacinacaoDao.QtdVacinacao(vac_id, pac_id);
    }

    public List<Vacinacao> getVacinacoes() {
        return vacinacoes;
    }

    public void setVacinacoes(List<Vacinacao> vacinacoes) {
        this.vacinacoes = vacinacoes;
    }

    public Vacinacao getVacinacao() {
        if (vacinacao == null) {
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

    public Map<String, Object> getDias() {
        return DataUtil.getDias();
    }

    public Map<String, Object> getMeses() {
        return DataUtil.getMeses();
    }

    public Map<String, Object> getAnos() {
        return DataUtil.getAnos();
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

}
