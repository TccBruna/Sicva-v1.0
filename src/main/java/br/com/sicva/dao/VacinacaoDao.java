/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Vacinacao;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class VacinacaoDao {

    private Session session;
    private Transaction tx;
    List<Vacinacao> listaVacinacao = new ArrayList<>();

    public boolean salvarVacinacao(Vacinacao vacinacao) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(vacinacao);
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public boolean alterarVacinacao(Vacinacao vacinacao) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(vacinacao);
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public List<Vacinacao> buscarVacinacao(String cpf) {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select*from vacinacao\n"
                + "inner join vacina on vacina_id = vacinacao_vacina_id\n"
                + "inner join paciente on PAC_ID = VACINACAO_PAC_ID "
                + "left join enfermeiro on VACINACAO_ENFERM_COREN = ENFERMEIRO_COREN "               
                + "where PAC_CPF = :cpf \n"
                + "order by VACINACAO_ID").addEntity(Vacinacao.class);
        query.setString("cpf", cpf);
        listaVacinacao = query.list();
        for(Vacinacao vaci: listaVacinacao) {
            Hibernate.initialize(vaci.getEnfermeiro());
            Hibernate.initialize(vaci.getVacina());       
            Hibernate.initialize(vaci.getUbs());
            Hibernate.initialize(vaci.getPaciente());  
        }
        session.close();
        if (listaVacinacao.isEmpty()) {
            return null;
        } else {
            return listaVacinacao;
        }
    }     
      public Integer QtdVacinacao(Integer vac_id,Integer pac_id) {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select count(*) from vacinacao where vacinacao_vacina_id"
                + " = :vac_id and VACINACAO_PAC_ID = :pac_id");
        query.setInteger("vac_id", vac_id);
        query.setInteger("pac_id", pac_id);
        Integer total  = ((BigInteger) query.uniqueResult()).intValue();
        session.close();        
        return total;        
    }     
    
}
