package br.com.sicva.model;
// Generated 01/04/2017 10:00:24 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Paciente generated by hbm2java
 */
@Entity
@Table(name="paciente"
    ,catalog="sicva"
    , uniqueConstraints = {@UniqueConstraint(columnNames="PAC_CPF"), @UniqueConstraint(columnNames="PAC_NOME_RESP"), @UniqueConstraint(columnNames="PAC_NOME")} 
)
public class Paciente  implements java.io.Serializable {


     private Integer pacId;
     private Endereco endereco;
     private String pacCpf;
     private String pacNomeResp;
     private Date pacDtnasc;
     private String pacNome;
     

    public Paciente() {
    }

	
    public Paciente(Endereco endereco, String pacCpf, String pacNomeResp, Date pacDtnasc, String pacNome) {
        this.endereco = endereco;
        this.pacCpf = pacCpf;
        this.pacNomeResp = pacNomeResp;
        this.pacDtnasc = pacDtnasc;
        this.pacNome = pacNome;
    }
    
   
    @Id @GeneratedValue(strategy=IDENTITY)
     @Column(name="PAC_ID", unique=true, nullable=false)
    public Integer getPacId() {
        return this.pacId;
    }
    
    public void setPacId(Integer pacId) {
        this.pacId = pacId;
    }

    @ManyToOne
    @JoinColumn(name="PAC_ENDERECO_ID", nullable=false)
    public Endereco getEndereco() {
        return this.endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    
    @Column(name="PAC_CPF", unique=true, nullable=false, length=14)
    public String getPacCpf() {
        return this.pacCpf;
    }
    
    public void setPacCpf(String pacCpf) {
        this.pacCpf = pacCpf;
    }

    
    @Column(name="PAC_NOME_RESP", unique=true, nullable=false, length=80)
    public String getPacNomeResp() {
        return this.pacNomeResp;
    }
    
    public void setPacNomeResp(String pacNomeResp) {
        this.pacNomeResp = pacNomeResp;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PAC_DTNASC", nullable=false, length=10)
    public Date getPacDtnasc() {
        return this.pacDtnasc;
    }
    
    public void setPacDtnasc(Date pacDtnasc) {
        this.pacDtnasc = pacDtnasc;
    }

    
    @Column(name="PAC_NOME", unique=true, nullable=false, length=80)
    public String getPacNome() {
        return this.pacNome;
    }
    
    public void setPacNome(String pacNome) {
        this.pacNome = pacNome;
    }


}


