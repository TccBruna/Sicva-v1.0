package br.com.sicva.model;
// Generated 05/05/2017 18:52:05 by Hibernate Tools 4.3.1


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Ubs generated by hbm2java
 */
@Entity
@Table(name="ubs"
    ,catalog="sicva"
    , uniqueConstraints = {@UniqueConstraint(columnNames="UBS_NOME"), @UniqueConstraint(columnNames="UBS_FONE")} 
)
public class Ubs  implements java.io.Serializable {


     private Integer ubsId;
     private Endereco endereco;
     private String ubsFone;
     private String ubsNome;
    

    public Ubs() {
    }

	
    public Ubs(Endereco endereco, String ubsFone, String ubsNome) {
        this.endereco = endereco;
        this.ubsFone = ubsFone;
        this.ubsNome = ubsNome;
    }
    
    @Id @GeneratedValue(strategy=IDENTITY)    
    @Column(name="UBS_ID", unique=true, nullable=false)
    public Integer getUbsId() {
        return this.ubsId;
    }
    
    public void setUbsId(Integer ubsId) {
        this.ubsId = ubsId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UBS_ENDERECO_ID", nullable=false)
    public Endereco getEndereco() {
        return this.endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    
    @Column(name="UBS_FONE", unique=true, nullable=false, length=15)
    public String getUbsFone() {
        return this.ubsFone;
    }
    
    public void setUbsFone(String ubsFone) {
        this.ubsFone = ubsFone;
    }

    
    @Column(name="UBS_NOME", unique=true, nullable=false, length=80)
    public String getUbsNome() {
        return this.ubsNome;
    }
    
    public void setUbsNome(String ubsNome) {
        this.ubsNome = ubsNome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.ubsId);
        hash = 79 * hash + Objects.hashCode(this.ubsFone);
        hash = 79 * hash + Objects.hashCode(this.ubsNome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ubs other = (Ubs) obj;
        if (!Objects.equals(this.ubsFone, other.ubsFone)) {
            return false;
        }
        if (!Objects.equals(this.ubsNome, other.ubsNome)) {
            return false;
        }
        if (!Objects.equals(this.ubsId, other.ubsId)) {
            return false;
        }
        return true;
    }
    
    


}


