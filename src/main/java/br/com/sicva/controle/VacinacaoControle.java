/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.VacinacaoDao;
import br.com.sicva.model.Vacinacao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class VacinacaoControle {
    private VacinacaoDao vacinacaoDao;
    
    public List<Vacinacao> getListarModelo(){
        vacinacaoDao = new VacinacaoDao();
        return vacinacaoDao.listarModelo();
    }
}
