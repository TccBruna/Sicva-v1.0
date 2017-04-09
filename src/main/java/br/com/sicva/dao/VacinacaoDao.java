/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Vacinacao;
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
            new Mensagens().MensagensSucesso("Vacinacao salva com sucesso", null);
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
            new Mensagens().MensagensSucesso("Vacinacao alterada com sucesso", null);
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }
    
    public List<Vacinacao> listarModelo() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select*from vacinacao inner join vacina "
                + "on vacina_id = vacinacao_vacina_id order by VACINACAO_FAIXA_ETERIA").addEntity(Vacinacao.class);
        listaVacinacao = query.list();
        listaVacinacao = session.createCriteria(Vacinacao.class).list();
        session.close();
        return listaVacinacao;
    }
}
