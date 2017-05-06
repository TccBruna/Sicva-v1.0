package br.com.sicva.model;
// Generated 05/05/2017 18:52:05 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Endereco generated by hbm2java
 */
@Entity
@Table(name="endereco"
    ,catalog="sicva"
)
public class Endereco  implements java.io.Serializable {


     private Integer endId;
     private Bairro bairro;
     private String endCep;
     private String endComplemento;
     private String endRua;
     private Integer endNumero;    

    public Endereco() {
    }

	
    public Endereco(Bairro bairro, String endCep, String endRua) {
        this.bairro = bairro;
        this.endCep = endCep;
        this.endRua = endRua;
    }
    public Endereco(Bairro bairro, String endCep, String endComplemento, String endRua, Integer endNumero) {
       this.bairro = bairro;
       this.endCep = endCep;
       this.endComplemento = endComplemento;
       this.endRua = endRua;
       this.endNumero = endNumero;       
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)    
    @Column(name="END_ID", unique=true, nullable=false)
    public Integer getEndId() {
        return this.endId;
    }
    
    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="END_BAIRRO_ID", nullable=false)
    public Bairro getBairro() {
        return this.bairro;
    }
    
    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    
    @Column(name="END_CEP", nullable=false, length=9)
    public String getEndCep() {
        return this.endCep;
    }
    
    public void setEndCep(String endCep) {
        this.endCep = endCep;
    }

    
    @Column(name="END_COMPLEMENTO", length=50)
    public String getEndComplemento() {
        return this.endComplemento;
    }
    
    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    
    @Column(name="END_RUA", nullable=false, length=80)
    public String getEndRua() {
        return this.endRua;
    }
    
    public void setEndRua(String endRua) {
        this.endRua = endRua;
    }

    
    @Column(name="END_NUMERO")
    public Integer getEndNumero() {
        return this.endNumero;
    }
    
    public void setEndNumero(Integer endNumero) {
        this.endNumero = endNumero;
    }


}


