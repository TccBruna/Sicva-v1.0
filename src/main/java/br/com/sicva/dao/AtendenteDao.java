/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Atendente;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class AtendenteDao {
   private Session session;
    private Transaction tx;
    List<Atendente> listarAtendente = new ArrayList<>();

    public boolean salvarAtendente(Atendente atendente) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.save(atendente);            
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

    public boolean alterarAtendente(Atendente atendente) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.update(atendente);           
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }   
}
