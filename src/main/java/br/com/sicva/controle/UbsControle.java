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
    private Ubs ubsSelecionado;
    private List<Ubs> allUbs;
    
    public List<Ubs> ListarUbs(String zona){
        if(!zona.equals("")){
           UbsDao ubsDao = new UbsDao();
           allUbs = ubsDao.ListarPorZona(Integer.valueOf(zona));
        }
        return  allUbs;
    }
    
    public List<SelectItem> getUbs() {
        UbsDao ubsDao = new UbsDao();
        List<Ubs> listaUbs = ubsDao.listarUbs();
        final List<SelectItem> itens = new ArrayList<>(listaUbs.size());
        for (Ubs ubs : listaUbs) {
            itens.add(new SelectItem(ubs, ubs.getUbsNome()));
        }
        return itens;
    }

    public Ubs getUbsSelecionado() {
        if(ubsSelecionado == null){
            ubsSelecionado = new Ubs();
        }
        return ubsSelecionado;
    }

    public void setUbsSelecionado(Ubs ubsSelecionado) {
        this.ubsSelecionado = ubsSelecionado;
    }

    public List<Ubs> getAllUbs() {
        return allUbs;
    }

    public void setAllUbs(List<Ubs> allUbs) {
        this.allUbs = allUbs;
    }
    
    
}
