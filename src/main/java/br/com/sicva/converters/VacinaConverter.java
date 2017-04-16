/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.converters;

import br.com.sicva.dao.VacinaDao;
import br.com.sicva.model.Vacina;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rodrigo
 */
@FacesConverter(forClass = Vacina.class, value = "vacinaConverter" )
public class VacinaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        VacinaDao vacinaDao = new VacinaDao();
        Vacina vacina = vacinaDao.consultarPorNome(value);
        return vacina;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o != null){
            return ((Vacina)o).getVacinaNome();
        }
        return null;
    }
    
}
