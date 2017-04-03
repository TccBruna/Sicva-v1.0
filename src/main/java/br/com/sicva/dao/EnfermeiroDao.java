/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Enfermeiro;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodirgo
 */
public class EnfermeiroDao {
    private Session session;
    private Transaction tx;
    List<Enfermeiro> listarEnfermeiro = new ArrayList<>();

    public boolean salvarEnfermeiro(Enfermeiro enfermeiro) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.save(enfermeiro);            
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

    public boolean alterarEnfermeiro(Enfermeiro enfermeiro) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.update(enfermeiro);           
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco não salvou o enfermeiro!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }
       

    public List<Enfermeiro> listarEnfermeiro() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        listarEnfermeiro = session.createCriteria(Enfermeiro.class).list();
        session.close();
        return listarEnfermeiro;
    }

    public Enfermeiro PesquisarEnfermeiro(String coren) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from enfermeiro where ENFERMEIRO_COREN = :coren").addEntity(Enfermeiro.class);
            query.setString("coren", coren);
            listarEnfermeiro = query.list();
            session.close();
            if (listarEnfermeiro.isEmpty()) {
                return null;
            } else {
                return listarEnfermeiro.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
