/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Usuario;
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
public class UsuarioDao {

    private Session session;
    private Transaction tx;
    List<Usuario> listarUsuario = new ArrayList<>();

    public boolean salvarUsuario(Usuario usuario) {
        try {
            if (PesquisarUsuario(usuario.getUsuarioCpf()) == null) {
                session = new FabricaDeConexao().getSessionFactory().openSession();
                tx = session.beginTransaction();
                session.save(usuario);
                tx.commit();
                session.close();                
                return true;
            } else {
                new Mensagens().MensagensAviso("Cpf inválido", "Esse cpf já está em uso");
                return false;
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            tx.rollback();

            return false;
        }
    }

    public boolean alterarUsuario(Usuario usuario) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(usuario);
            tx.commit();
            session.close();           
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();

            return false;
        }
    }

    public boolean deletarUsuario(Usuario usuario) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(usuario);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }

    public List<Usuario> listarUsuario() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        listarUsuario = session.createCriteria(Usuario.class).list();
        session.close();
        return listarUsuario;
    }

    public Usuario PesquisarUsuario(String cpf) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from usuario where USUARIO_CPF = :cpf").addEntity(Usuario.class);
            query.setString("cpf", cpf);
            listarUsuario = query.list();
            session.close();
            if (listarUsuario.isEmpty()) {
                return null;
            } else {
                return listarUsuario.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }

    public Usuario PesquisarPorEnfermeiro(String coren) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from usuario inner join enfermeiro\n"
                    + "on USUARIO_ID = ENFERMEIRO_USUARIO_ID "
                    + "where ENFERMEIRO_COREN = :coren").addEntity(Usuario.class);
            query.setString("coren", coren);
            listarUsuario = query.list();
            session.close();
            if (listarUsuario.isEmpty()) {
                return null;
            } else {
                return listarUsuario.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
