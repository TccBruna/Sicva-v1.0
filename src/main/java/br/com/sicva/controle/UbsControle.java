/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.controle;

import br.com.sicva.dao.UbsDao;
import br.com.sicva.model.Ubs;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Rodrigo
 */
@ManagedBean
@ViewScoped
public class UbsControle {
    public List<SelectItem> getUbs() {
        UbsDao ubsDao = new UbsDao();
        List<Ubs> listaUbs = ubsDao.listarUbs();
        final List<SelectItem> itens = new ArrayList<>(listaUbs.size());
        for (Ubs ubs : listaUbs) {
            itens.add(new SelectItem(ubs, ubs.getUbsNome()));
        }
        return itens;
    }
}
