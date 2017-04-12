/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.ConexaoMySql;
import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Vacinacao;
import br.com.sicva.util.Mensagens;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

    public List<Vacinacao> buscarVacinacao(String cpf) {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select*from vacinacao\n"
                + "inner join vacina on vacina_id = vacinacao_vacina_id\n"
                + "inner join paciente on PAC_ID = VACINACAO_PAC_ID\n"
                + "where PAC_CPF = :cpf \n"
                + "order by VACINACAO_ID").addEntity(Vacinacao.class);
        query.setString("cpf", cpf);
        listaVacinacao = query.list();
        session.close();
        if (listaVacinacao.isEmpty()) {
            return null;
        } else {
            return listaVacinacao;
        }
    }

    public boolean CallGerarCartão(int pac_id) {
       Connection con  = ConexaoMySql.getConexaoMySQL();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL GERAR_CARTAO(?)}");
            cs.setInt(1, pac_id);
            cs.executeUpdate();           
            con.close();
            return true;            
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }finally{
            try {
                con.close();;
            } catch (SQLException e) {
                System.out.println("SQLExcption: "+e.getMessage());
            }
        }

    }
}
