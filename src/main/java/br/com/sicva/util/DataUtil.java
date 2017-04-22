/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicva.util;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Rodrigo
 */
public class DataUtil {

    
    public int calcularIdade(Date dataNascimento) {
        Calendar dtNasc = Calendar.getInstance();
        //cria um objeto calendar com a data atual
        Calendar dtAtual = Calendar.getInstance();
        //cria um objeto calendar com a data de Nascimento
        dtNasc.setTime(dataNascimento);
        //calcula a idade basedo no ano
        int idade = dtAtual.get(Calendar.YEAR) - dtNasc.get(Calendar.YEAR);        
        dtNasc.add(Calendar.YEAR, idade);        
        if (dtAtual.before(dtNasc)) {
            idade--;
        }
        return idade;
    }
    
    /**
	 * Adiciona quantidade de dias na data.
	 *
	 * @param data
	 * @param qtd
	 * @return
	 */
	public  Date addDia(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, qtd);
		return cal.getTime();
	}
 
	/**
	 * Adiciona quantidade de meses na data.
	 *
	 * @param data
	 * @param qtd
	 * @return
	 */
	public  Date addMes(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, qtd);
		return cal.getTime();
	}
 
	/**
	 * Adiciona quantidade de anos na data.
	 *
	 * @param data
	 * @param qtd
	 * @return
	 */
	public  Date addAno(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.YEAR, qtd);
		return cal.getTime();
	}     
        
    
    private static Map<String, Object> dias;
    static {
        dias = new LinkedHashMap<>();
        for (int i = 0; i <= 31; i++) {
            dias.put(String.valueOf(i), String.valueOf(i));
        }
    }
  
    private static Map<String, Object> meses;
    static {
        meses = new LinkedHashMap<>();
        for (int i = 0; i <= 12; i++) {
            meses.put(String.valueOf(i), String.valueOf(i));
        }
    }
  
    private static Map<String, Object> anos;
     static {
        anos = new LinkedHashMap<>();
        for (int i = 0; i <= 15; i++) {
            anos.put(String.valueOf(i), String.valueOf(i));
        }
    }
  
    public static Map<String, Object> getDias() {
        return dias;
    }
  
    public static Map<String, Object> getMeses() {
        return meses;
    }
  
    public static Map<String, Object> getAnos() {
        return anos;
    }   
 
    
}
