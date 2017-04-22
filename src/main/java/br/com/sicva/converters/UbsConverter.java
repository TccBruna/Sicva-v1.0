/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.converters;

import br.com.sicva.dao.UbsDao;
import br.com.sicva.model.Ubs;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rodrigo
 */
@FacesConverter(forClass = Ubs.class, value = "ubsConverter")
public class UbsConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try {
            UbsDao ubsDao = new UbsDao();
            Ubs ubs = ubsDao.pesquisarPorId(Integer.valueOf(value));
            return ubs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Ubs) o).getUbsId().toString();
        }
        return null;
    }
}
