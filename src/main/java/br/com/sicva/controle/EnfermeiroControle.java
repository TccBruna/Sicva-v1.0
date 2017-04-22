/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.EnfermeiroDao;
import br.com.sicva.dao.UsuarioDao;
import br.com.sicva.model.Enfermeiro;
import br.com.sicva.model.Usuario;
import br.com.sicva.util.Mensagens;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class EnfermeiroControle {

    private Enfermeiro enfermeiro;
    private EnfermeiroDao enfermeiroDao;
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private boolean showMsgErros;
    private String CorenPesquisado;

    public void salvar() {
        try {
            enfermeiroDao = new EnfermeiroDao();
            if (enfermeiroDao.PesquisarEnfermeiro(enfermeiro.getEnfermeiroCoren()) == null) {
                usuarioDao = new UsuarioDao();
                if (usuarioDao.salvarUsuario(usuario)) {
                    enfermeiro.setUsuario(usuario);
                    enfermeiroDao.salvarEnfermeiro(enfermeiro);
                    new Mensagens().MensagensSucesso("Dados salvos com sucesso", null);
                } else {
                    usuarioDao.deletarUsuario(usuario);
                    new Mensagens().MensagensErro("Não foi possivel salvar os dados", null);
                }
            } else {
                new Mensagens().MensagensAviso("O coren digitado já está em uso", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
        showMsgErros = true;
        usuario = new Usuario();
        enfermeiro = new Enfermeiro();
    }

    public void alterar() {
        try {
            enfermeiroDao = new EnfermeiroDao();
            usuarioDao = new UsuarioDao();
            if (usuarioDao.alterarUsuario(usuario)) {
                enfermeiro.setUsuario(usuario);
                enfermeiroDao.alterarEnfermeiro(enfermeiro);
                new Mensagens().MensagensSucesso("Dados alterados com sucesso", null);
            } else {
                usuarioDao.deletarUsuario(usuario);
                new Mensagens().MensagensErro("Não foi possivel salvar os dados", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
        showMsgErros = true;
        usuario = new Usuario();
        enfermeiro = new Enfermeiro();
    }
    
    public void pesquisar(){
        try{
            enfermeiroDao = new EnfermeiroDao();
            enfermeiro = enfermeiroDao.PesquisarEnfermeiro(CorenPesquisado);
            if (enfermeiro == null) {
                usuario = new Usuario();
                enfermeiro = new Enfermeiro();
                new Mensagens().MensagensAviso("Enfermeiro não encontrado", null);
            }else{
                usuarioDao = new UsuarioDao();
                usuario = usuarioDao.PesquisarPorEnfermeiro(enfermeiro.getEnfermeiroCoren());
                new Mensagens().MensagensSucesso("Dados encontrados", null);
            }
        }catch(Exception e){            
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
        showMsgErros = true;
    }

    public Enfermeiro getEnfermeiro() {
        if (enfermeiro == null) {
            enfermeiro = new Enfermeiro();
        }
        return enfermeiro;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isShowMsgErros() {
        return showMsgErros;
    }

    public void setShowMsgErros(boolean showMsgErros) {
        this.showMsgErros = showMsgErros;
    }

    public String getCorenPesquisado() {
        return CorenPesquisado;
    }

    public void setCorenPesquisado(String CorenPesquisado) {
        this.CorenPesquisado = CorenPesquisado;
    }
    
     

}
