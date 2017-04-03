/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.dao;

import br.com.sicva.conexao.FabricaDeConexao;
import br.com.sicva.model.Paciente;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rodrigo
 */
public class PacienteDao {

    private Session session;
    private Transaction tx;
    List<Paciente> listarPaciente = new ArrayList<>();

    public boolean salvarPaciente(Paciente paciente) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.save(paciente);            
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

    public boolean alterarPaciente(Paciente paciente) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.update(paciente);           
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("Erro no Banco!" + e.getMessage());
            tx.rollback();
            return false;
        }
    }
    
     public boolean deletarPaciente(Paciente paciente) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            tx = session.beginTransaction();            
            session.delete(paciente);            
            tx.commit();
            session.close();            
            return true;
        } catch (Exception e) {
            System.out.println("erro aqui" + e.getMessage());
            tx.rollback(); 
            session.close();
            return false;
        }
    }

    public List<Paciente> listarPaciente() {
        session = new FabricaDeConexao().getSessionFactory().openSession();
        listarPaciente = session.createCriteria(Paciente.class).list();
        session.close();
        return listarPaciente;
    }

    public Paciente PesquisarPaciente(String cpf) {
        try {
            session = new FabricaDeConexao().getSessionFactory().openSession();
            Query query = session.createSQLQuery("select * from paciente where PAC_CPF = :cpf").addEntity(Paciente.class);
            query.setString("cpf", cpf);
            listarPaciente = query.list();
            session.close();
            if (listarPaciente.isEmpty()) {
                return null;
            } else {
                return listarPaciente.get(0);
            }
        } catch (Exception e) {
            System.out.println("" + e.getCause().getMessage());
            return null;
        }
    }
}
