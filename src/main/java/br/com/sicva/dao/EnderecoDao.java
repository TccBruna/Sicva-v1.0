/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Endereco;
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
public class EnderecoDao {

    private Session session;
    private Transaction tx;
    List<Endereco> listarEndereco = new ArrayList<>();

    public boolean salvarEndereco(Endereco endereco) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(endereco);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("erro" + e.getMessage() + "causa" + e.getCause().getMessage());
            tx.rollback();
            session.close();
            return false;
        }
    }

    public boolean alterarEndereco(Endereco endereco) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(endereco);
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public boolean deletarEndereco(Endereco endereco) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(endereco);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("erro" + e.getMessage() + "causa" + e.getCause().getMessage());
            tx.rollback();
            session.close();
            return false;
        }
    }

    public Endereco PesquisarEndereco(Integer id) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("SELECT * \n"
                    + "FROM endereco inner join paciente on PAC_ENDERECO_ID = END_ID \n"                    
                    + "where PAC_ID = :id").addEntity(Endereco.class);
            query.setInteger("id", id);
            listarEndereco = query.list();
            session.close();
            if (listarEndereco.isEmpty()) {
                return null;
            } else {
                return listarEndereco.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
