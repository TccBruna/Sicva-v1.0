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
import org.hibernate.Hibernate;
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

    public List<Ubs> ListarPorZona(Integer value) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("SELECT * FROM ubs\n"
                    + "inner join\n"
                    + "   endereco on UBS_ENDERECO_ID = END_ID\n"
                    + "inner join\n"
                    + "   bairro on END_BAIRRO_ID = BAIRRO_ID\n"
                    + "inner join zona on ZONA_ID = BAIRRO_ZONA_ID\n"
                    + "where ZONA_ID = :zona")
                    .addEntity(Ubs.class);
            query.setInteger("zona", value);            
            listaUbs = query.list();
            for(Ubs u: listaUbs){
                Hibernate.initialize(u.getEndereco());          
                Hibernate.initialize(u.getEndereco().getBairro());          
            }
            session.close();
            if (listaUbs.isEmpty()) {
                return null;
            } else {
                return listaUbs;
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
