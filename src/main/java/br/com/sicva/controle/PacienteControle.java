/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.BairroDao;
import br.com.sicva.dao.EnderecoDao;
import br.com.sicva.dao.FoneDao;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.model.Bairro;
import br.com.sicva.model.Endereco;
import br.com.sicva.model.Fone;
import br.com.sicva.model.Paciente;
import br.com.sicva.util.Mensagens;
import java.io.Serializable;
import java.util.ArrayList;
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
public class PacienteControle implements Serializable{

    private Paciente paciente;
    private PacienteDao pacienteDao;
    private String pacientePesquisado;
    private Fone fone;
    private FoneDao foneDao;
    private Endereco endereco;
    private EnderecoDao enderecoDao;
    private String dataNascimento;

    public void pesquisar() {
        pacienteDao = new PacienteDao();
        try {
            paciente = pacienteDao.PesquisarPaciente(pacientePesquisado);
            if (paciente == null) {
                endereco = new Endereco();
                fone = new Fone();
                new Mensagens().MensagensAviso("Nenhum Paciente não encontrado com CPF: " + pacientePesquisado, null);
            } else {
                enderecoDao = new EnderecoDao();
                endereco = enderecoDao.PesquisarEndereco(paciente.getPacId());
                fone = new FoneDao().PesquisarFone(paciente.getPacId());
                new Mensagens().MensagensSucesso("Paciente encontrado", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public void salvar() {
        try {
            pacienteDao = new PacienteDao();
            foneDao = new FoneDao();
            if (pacienteDao.PesquisarPaciente(paciente.getPacCpf()) == null) {
                endereco.setEndCep(endereco.getEndCep().replace(".", "").replace("-", ""));
                paciente.setEndereco(endereco);
                fone.setPaciente(paciente);

                if (pacienteDao.salvarPaciente(paciente) && foneDao.salvarFone(fone)) {                    
                    endereco = new Endereco();
                    fone = new Fone();
                    paciente = new Paciente();
                    new Mensagens().MensagensSucesso("Dados salvos com sucesso", null);
                } else {
                    new Mensagens().MensagensErro("Não foi possivel salvar os dados", null);
                }
            } else {
                new Mensagens().MensagensAviso("Este CPF informado já está em uso, caso queira alterar os dados vá "
                        + "para a pagina de alteraçao de paciente", null);
            }
        } catch (Exception e) {
            System.out.println("" + e);
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public void alterar() {
        try {
            pacienteDao = new PacienteDao();            
            foneDao = new FoneDao();
            endereco.setEndCep(endereco.getEndCep().replace(".", "").replace("-", ""));
            paciente.setEndereco(endereco);
            fone.setPaciente(paciente);
            if (pacienteDao.alterarPaciente(paciente) && foneDao.alterarFone(fone)) {                
                endereco = new Endereco();
                fone = new Fone();
                paciente = new Paciente();
                new Mensagens().MensagensSucesso("Dados alterados com sucesso", null);
            } else {
                new Mensagens().MensagensErro("Não foi possivel alterar os dados", null);
            }
        } catch (Exception e) {
            System.out.println("" + e);
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

    public String getDataNacimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    

}
