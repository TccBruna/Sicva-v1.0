package br.com.sicva.model;
// Generated 05/05/2017 18:52:05 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Zona generated by hbm2java
 */
@Entity
@Table(name="zona"
    ,catalog="sicva"
)
public class Zona  implements java.io.Serializable {


     private Integer zonaId;
     private String zonaNome;
     

    public Zona() {
    }

	
    public Zona(String zonaNome) {
        this.zonaNome = zonaNome;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)    
    @Column(name="ZONA_ID", unique=true, nullable=false)
    public Integer getZonaId() {
        return this.zonaId;
    }
    
    public void setZonaId(Integer zonaId) {
        this.zonaId = zonaId;
    }

    
    @Column(name="ZONA_NOME", nullable=false, length=80)
    public String getZonaNome() {
        return this.zonaNome;
    }
    
    public void setZonaNome(String zonaNome) {
        this.zonaNome = zonaNome;
    }


}


