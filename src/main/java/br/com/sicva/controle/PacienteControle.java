/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.BairroDao;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.model.Bairro;
import br.com.sicva.model.Endereco;
import br.com.sicva.model.Fone;
import br.com.sicva.model.Paciente;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@SessionScoped
public class PacienteControle {

    private Paciente paciente;
    private PacienteDao pacienteDao;
    private String pacientePesquisado;
    private Fone fone;
    private Endereco endereco;
    private boolean AddNovo;
    private boolean MostrarForm;
    private boolean CampoCpf;

    public void pesquisar() {
        pacienteDao = new PacienteDao();
        try {
            paciente = pacienteDao.PesquisarPaciente(pacientePesquisado);
            if (paciente == null) {
                new Mensagens().MensagensAviso("Nenhum Paciente não encontrado com CPF: " + pacientePesquisado, null);
            } else {
                new Mensagens().MensagensSucesso("Paciente encontrado", null);
                MostrarForm = true;
                CampoCpf = true;
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public void salvar() {
        try {
            pacienteDao = new PacienteDao();
            paciente.setEndereco(endereco);
            fone.setPaciente(paciente);
            pacienteDao.salvarPaciente(paciente);
            new Mensagens().MensagensSucesso("Salvo com sucesso", null);
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public List<SelectItem> getBairros() {
        BairroDao funcaoDao = new BairroDao();
        List<Bairro> listaBairros = funcaoDao.listarBairro();
        final List<SelectItem> itens = new ArrayList<>(listaBairros.size());
        for (Bairro bairro : listaBairros) {
            itens.add(new SelectItem(bairro, bairro.getBairroNome()));
        }
        return itens;
    }

    public void novo() {
        MostrarForm = true;
        AddNovo = true;
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

    public String getPacientePesquisado() {
        return pacientePesquisado;
    }

    public void setPacientePesquisado(String pacientePesquisado) {
        this.pacientePesquisado = pacientePesquisado;
    }

    public Fone getFone() {
        if (fone == null) {
            fone = new Fone();
        }
        return fone;
    }

    public void setFone(Fone fone) {
        this.fone = fone;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isAddNovo() {
        return AddNovo;
    }

    public void setAddNovo(boolean AddNovo) {
        this.AddNovo = AddNovo;
    }

    public boolean isMostrarForm() {
        return MostrarForm;
    }

    public void setMostrarForm(boolean MostrarForm) {
        this.MostrarForm = MostrarForm;
    }

    public boolean isCampoCpf() {
        return CampoCpf;
    }

    public void setCampoCpf(boolean CampoCpf) {
        this.CampoCpf = CampoCpf;
    }

}
