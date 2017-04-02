/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rodrigo
 */
public class Mensagens {
    
    public void MensagensSucesso(String msg, String detalhe){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    msg, detalhe));
    }
    
    public void MensagensAviso(String msg, String detalhe){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msg, detalhe));
    }
    public void MensagensErro(String msg, String detalhe){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msg, detalhe));
    }
    public void MensagensErroFatal(String msg, String detalhe){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    msg, detalhe));
    }
}
