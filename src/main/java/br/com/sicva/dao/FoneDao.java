/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Fone;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class FoneDao {
    private Session session;
    private Transaction tx;
    List<Fone> listarFone = new ArrayList<>();

    public boolean salvarFone(Fone fone) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.save(fone);            
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

    public boolean alterarFone(Fone fone) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.update(fone);           
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }    

    public Fone PesquisarFone(Integer id) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from fone where FONE_PAC_ID = :id").addEntity(Fone.class);
            query.setInteger("id", id);
            listarFone = query.list();
            session.close();
            if (listarFone.isEmpty()) {
                return null;
            } else {
                return listarFone.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
    
}
