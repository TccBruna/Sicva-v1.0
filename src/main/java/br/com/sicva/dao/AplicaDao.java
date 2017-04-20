/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Aplica;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class AplicaDao {

    private Session session;
    private Transaction tx;
    List<Aplica> listarAplica = new ArrayList<>();

    public boolean salvarAplica(Aplica aplica) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(aplica);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
    }

    public boolean alterarAplica(Aplica aplica) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(aplica);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco não salvou o aplica!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public List<Aplica>  buscarAplicacoes(String cpf) {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select * from vacinacao inner join vacina "
                + "on vacina_id = vacinacao_vacina_id\n"
                + "inner join paciente on PAC_ID = VACINACAO_PAC_ID"
                + " left join aplica on  VACINACAO_ID = APLICA_VACINACAO_ID"
                + " where pac_cpf = :cpf").addEntity(Aplica.class);
        query.setString("cpf", cpf);        
        listarAplica = query.list();
        session.close();
        if (listarAplica.isEmpty()) {
            return null;
        } else {
            return listarAplica;
        }
    }

}
