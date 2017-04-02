/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Bairro;
import br.com.sicva.model.Bairro;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class BairroDao {
    private Session session;
    private Transaction tx;
    List<Bairro> listaBairro = new ArrayList<>();
    
    public List<Bairro> listarBairro() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        listaBairro = session.createCriteria(Bairro.class).list();
        session.close();
        return listaBairro;
    }
    
    public Bairro PesquisarPorNome(String nome) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from bairro where BAIRRO_NOME = :nome")
                    .addEntity(Bairro.class);
            query.setString("nome", nome);
            listaBairro = query.list();
            session.close();
            if (listaBairro.isEmpty()) {
                return null;
            } else {
                return listaBairro.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
    
}
