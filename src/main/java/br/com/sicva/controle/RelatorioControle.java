/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.conexao.ConexaoMySql;
import br.com.sicva.dao.PacienteDao;
import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Paciente;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.relatorios.CriadorDeRelatorios;
import br.com.sicva.util.Mensagens;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
public class RelatorioControle {

    private String cpfPaciente;
    private CriadorDeRelatorios criadorDeRelatorios;
    private HashMap<String, Object> parametros;
    private Date DataIninial;
    private Date DataFinal;

    public void gerarCartaoPdf() throws IOException, JRException {
        criadorDeRelatorios = new CriadorDeRelatorios();
        parametros = new HashMap<>();
        parametros.put("cpf", cpfPaciente);
        String Caminho = "/relatorios/CartaoDeVacina.jasper";        
        PacienteDao pacienteDao = new PacienteDao();
        Paciente paciente = pacienteDao.PesquisarPaciente(cpfPaciente);
        if (paciente == null) {
            new Mensagens().MensagensAviso("Paciente não encontrado: ", null);
        } else {
            VacinacaoDao vacinacaoDao = new VacinacaoDao();
            List<Vacinacao> vacinacoes = vacinacaoDao.buscarVacinacao(paciente.getPacCpf());
            criadorDeRelatorios.CriarRelatorio(vacinacoes, parametros, Caminho);
        }       

    }
    
    public void qtdVacinasUbs(){
        criadorDeRelatorios = new CriadorDeRelatorios();
        parametros = new HashMap<>();
        parametros.put("DataInicial", DataIninial);
        parametros.put("DataFinal", DataFinal);
        String Caminho = "/relatorios/QtdVacinasUbs.jasper";       
        try {
            criadorDeRelatorios.CriarRelatorio(ConexaoMySql.getConexaoMySQL(), parametros, Caminho);
        } catch (Exception e) {
            System.out.println(e);
        }
    }    
       

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public Date getDataIninial() {
        return DataIninial;
    }

    public void setDataIninial(Date DataIninial) {
        this.DataIninial = DataIninial;
    }

    public Date getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(Date DataFinal) {
        this.DataFinal = DataFinal;
    }
    
    

}
