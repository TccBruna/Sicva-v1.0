/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class IdadeUtil {
    public int calcularData(Date dataNascimento){        
        Calendar dtNasc = Calendar.getInstance();
        //cria um objeto calendar com a data atual
        Calendar dtAtual = Calendar.getInstance();
        //cria um objeto calendar com a data de Nascimento
        dtNasc.setTime(dataNascimento);
        //calcula a idade basedo no ano
        int idade = dtAtual.get(Calendar.YEAR) - dtNasc.get(Calendar.YEAR);
        System.out.println(dtNasc.getTime());
        dtNasc.add(Calendar.YEAR, idade);
        System.out.println(dtNasc.getTime());
        if(dtAtual.before(dtNasc)){
            idade--;
        }
        return idade;
    }
}
