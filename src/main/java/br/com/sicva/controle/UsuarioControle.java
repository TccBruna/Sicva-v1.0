/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.UsuarioDao;
import br.com.sicva.model.Usuario;
import br.com.sicva.util.Mensagens;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class UsuarioControle {

    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private boolean showMsgErros;
    private String CpfPesquisado;

    public void salvar() {
        showMsgErros = true;
        try {
            usuarioDao = new UsuarioDao();
            if (usuarioDao.salvarUsuario(usuario)) {
                usuario = new Usuario();
                new Mensagens().MensagensSucesso("Dados salvos com sucesso", null);
            }else{
                new Mensagens().MensagensErro("Não foi possivel salvar os dados", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }

    }

    public void alterar() {
        showMsgErros = true;
        try {
            usuarioDao = new UsuarioDao();
            if (usuarioDao.alterarUsuario(usuario)) {
                usuario = new Usuario();
                new Mensagens().MensagensSucesso("Dados alterados com sucesso", null);
            }else{
                new Mensagens().MensagensErro("Não foi possivel alterar os dados", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public void pesquisar() {
        showMsgErros = true;
        try {
            usuarioDao = new UsuarioDao();
            usuario = usuarioDao.PesquisarUsuario(CpfPesquisado);
            if (usuario == null) {
                new Mensagens().MensagensAviso("Usuário não encontrado", null);
            } else {
                new Mensagens().MensagensSucesso("Usuario Encontrado", null);
            }
        } catch (Exception e) {
            new Mensagens().MensagensErroFatal("erro na transação", "" + e);
        }
    }

    public List<Usuario> getListarTodos() {
        return new UsuarioDao().listarUsuario();
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

    public String getCpfPesquisado() {
        return CpfPesquisado;
    }

    public void setCpfPesquisado(String CpfPesquisado) {
        this.CpfPesquisado = CpfPesquisado;
    }

}
