/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.converters;

import br.com.sicva.dao.EnfermeiroDao;
import br.com.sicva.model.Enfermeiro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rodrigo
 */
@FacesConverter(forClass = Enfermeiro.class,value = "enfermeiroConverter")
public class EnfermeiroConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        EnfermeiroDao enfermeiroDao = new EnfermeiroDao();
        Enfermeiro enfermeiro = enfermeiroDao.PesquisarEnfermeiro(string);
        return enfermeiro;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((Enfermeiro)o).getEnfermeiroCoren();
    }
    
}
