/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.relatorios;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Rodrigo
 */
public class CriadorDeRelatorios {

    public void CriarRelatorio(List lista, HashMap<String, Object> parametros, String Caminho) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(Caminho);
        response.setContentType("application/pdf");        
        response.setHeader("Content-disposition", "inline;filename=relatorio.pdf");

        try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
            JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(lista);
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream,
                    parametros, dados);
            servletOutputStream.flush();
        } catch (IOException | JRException ex) {
            System.out.println("ERRO AO GEERAR RELATÓRIO: " + ex);
        } finally {
            facesContext.responseComplete();
        }

    }
    public void CriarRelatorio(Connection con, HashMap<String, Object> parametros, String Caminho) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(Caminho);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;filename=relatorio.pdf");
        try (ServletOutputStream servletOutputStream = response.getOutputStream()) {            
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream,
                    parametros, con);
            servletOutputStream.flush();
        } catch (IOException | JRException ex) {
            System.out.println("ERRO AO GEERAR RELATÓRIO: " + ex);
        } finally {
            facesContext.responseComplete();
        }

    }
}
