/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.converters;

import br.com.sicva.dao.BairroDao;
import br.com.sicva.model.Bairro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rodrigo
 */
@FacesConverter(value = "bairroConverter", forClass = Bairro.class)
public class BairroConverter implements Converter{
     BairroDao bairroDao = new BairroDao();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Bairro bairro = bairroDao.PesquisarPorNome(string);        
        return bairro;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {     
            return ((Bairro) o).getBairroNome();
        }else{
            return null;
        }
        
    }
}
