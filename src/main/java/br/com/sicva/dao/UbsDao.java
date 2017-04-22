/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Ubs;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class UbsDao {
    private Session session;
    private Transaction tx;
    List<Ubs> listaUbs = new ArrayList<>();
    
    public List<Ubs> listarUbs() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        listaUbs = session.createCriteria(Ubs.class).list();
        session.close();
        return listaUbs;
    }

    public Ubs pesquisarPorId(Integer value) {
         try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from ubs where UBS_ID = :id")
                    .addEntity(Ubs.class);
            query.setInteger("id", value);
            listaUbs = query.list();
            session.close();
            if (listaUbs.isEmpty()) {
                return null;
            } else {
                return listaUbs.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
