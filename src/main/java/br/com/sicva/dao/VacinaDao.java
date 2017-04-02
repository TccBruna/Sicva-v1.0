/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Vacina;
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
public class VacinaDao {
    private Session session;
    private Transaction tx;
    List<Vacina> listaVacina = new ArrayList<>();

    public boolean salvarVacina(Vacina vacina) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(vacina);
            tx.commit();
            session.close();
            new Mensagens().MensagensSucesso("Vacina salva com sucesso", null);
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public boolean alterarVacina(Vacina vacina) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(vacina);
            tx.commit();
            session.close();
            new Mensagens().MensagensSucesso("Vacina alterada com sucesso", null);
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }
    
    public List<Vacina> listarVacina() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select*from vacina").addEntity(Vacina.class);
        listaVacina = query.list();
        listaVacina = session.createCriteria(Vacina.class).list();
        session.close();
        return listaVacina;
    }

    public Vacina consultarPorNome(String nome) {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("SELECT * FROM vacina"
                + " WHERE vacina_nome = :nome").addEntity(Vacina.class);
        query.setString("nome", nome);
        listaVacina = query.list();
        session.close();
        if(listaVacina.isEmpty()){
           return null;
        }else{
           return listaVacina.get(0);
        }
    }

    public Vacina listarPorId(int codigo) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from vacina where vacina_id = :id").addEntity(Vacina.class);
            query.setInteger("id", codigo);
            listaVacina = query.list();
            session.close();
            return listaVacina.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
