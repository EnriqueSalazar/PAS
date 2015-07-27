package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;
import java.util.List;

public class CheckProductoPSA implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5521987295840853784L;

    private Long id;
    private String productoPAS;
    private String productosPSA;
    private String departamento;
    private String municipio;
    private Integer divipola;
    private String intervencion;
    private Integer vigencia;
    private Long contrato;
    private List<Key<ProductoPSA>> keys;

    private Double enero;
    private Double febrero;
    private Double marzo;
    private Double abril;
    private Double mayo;
    private Double junio;
    private Double julio;
    private Double agosto;
    private Double septiembre;
    private Double octubre;
    private Double noviembre;
    private Double diciembre;

    
    private Double enero0;
    private Double febrero0;
    private Double marzo0;
    private Double abril0;
    private Double mayo0;
    private Double junio0;
    private Double julio0;
    private Double agosto0;
    private Double septiembre0;
    private Double octubre0;
    private Double noviembre0;
    private Double diciembre0;

    
    private Double eneroDelta;
    private Double febreroDelta;
    private Double marzoDelta;
    private Double abrilDelta;
    private Double mayoDelta;
    private Double junioDelta;
    private Double julioDelta;
    private Double agostoDelta;
    private Double septiembreDelta;
    private Double octubreDelta;
    private Double noviembreDelta;
    private Double diciembreDelta;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductoPAS() {
        return productoPAS;
    }

    public void setProductoPAS(String productoPAS) {
        this.productoPAS = productoPAS;
    }

    public String getProductosPSA() {
        return productosPSA;
    }

    public void setProductosPSA(String productosPSA) {
        this.productosPSA = productosPSA;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getDivipola() {
        return divipola;
    }

    public void setDivipola(Integer divipola) {
        this.divipola = divipola;
    }

    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Long getContrato() {
        return contrato;
    }

    public void setContrato(Long contrato) {
        this.contrato = contrato;
    }

    public Double getEnero() {
        return enero;
    }

    public void setEnero(Double enero) {
        this.enero = enero;
    }

    public Double getFebrero() {
        return febrero;
    }

    public void setFebrero(Double febrero) {
        this.febrero = febrero;
    }

    public Double getMarzo() {
        return marzo;
    }

    public void setMarzo(Double marzo) {
        this.marzo = marzo;
    }

    public Double getAbril() {
        return abril;
    }

    public void setAbril(Double abril) {
        this.abril = abril;
    }

    public Double getMayo() {
        return mayo;
    }

    public void setMayo(Double mayo) {
        this.mayo = mayo;
    }

    public Double getJunio() {
        return junio;
    }

    public void setJunio(Double junio) {
        this.junio = junio;
    }

    public Double getJulio() {
        return julio;
    }

    public void setJulio(Double julio) {
        this.julio = julio;
    }

    public Double getAgosto() {
        return agosto;
    }

    public void setAgosto(Double agosto) {
        this.agosto = agosto;
    }

    public Double getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(Double septiembre) {
        this.septiembre = septiembre;
    }

    public Double getOctubre() {
        return octubre;
    }

    public void setOctubre(Double octubre) {
        this.octubre = octubre;
    }

    public Double getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(Double noviembre) {
        this.noviembre = noviembre;
    }

    public Double getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(Double diciembre) {
        this.diciembre = diciembre;
    }

    public Double getEnero0() {
        return enero0;
    }

    public void setEnero0(Double enero0) {
        this.enero0 = enero0;
    }

    public Double getFebrero0() {
        return febrero0;
    }

    public void setFebrero0(Double febrero0) {
        this.febrero0 = febrero0;
    }

    public Double getMarzo0() {
        return marzo0;
    }

    public void setMarzo0(Double marzo0) {
        this.marzo0 = marzo0;
    }

    public Double getAbril0() {
        return abril0;
    }

    public void setAbril0(Double abril0) {
        this.abril0 = abril0;
    }

    public Double getMayo0() {
        return mayo0;
    }

    public void setMayo0(Double mayo0) {
        this.mayo0 = mayo0;
    }

    public Double getJunio0() {
        return junio0;
    }

    public void setJunio0(Double junio0) {
        this.junio0 = junio0;
    }

    public Double getJulio0() {
        return julio0;
    }

    public void setJulio0(Double julio0) {
        this.julio0 = julio0;
    }

    public Double getAgosto0() {
        return agosto0;
    }

    public void setAgosto0(Double agosto0) {
        this.agosto0 = agosto0;
    }

    public Double getSeptiembre0() {
        return septiembre0;
    }

    public void setSeptiembre0(Double septiembre0) {
        this.septiembre0 = septiembre0;
    }

    public Double getOctubre0() {
        return octubre0;
    }

    public void setOctubre0(Double octubre0) {
        this.octubre0 = octubre0;
    }

    public Double getNoviembre0() {
        return noviembre0;
    }

    public void setNoviembre0(Double noviembre0) {
        this.noviembre0 = noviembre0;
    }

    public Double getDiciembre0() {
        return diciembre0;
    }

    public void setDiciembre0(Double diciembre0) {
        this.diciembre0 = diciembre0;
    }

    public Double getEneroDelta() {
        return eneroDelta;
    }

    public void setEneroDelta(Double eneroDelta) {
        this.eneroDelta = eneroDelta;
    }

    public Double getFebreroDelta() {
        return febreroDelta;
    }

    public void setFebreroDelta(Double febreroDelta) {
        this.febreroDelta = febreroDelta;
    }

    public Double getMarzoDelta() {
        return marzoDelta;
    }

    public void setMarzoDelta(Double marzoDelta) {
        this.marzoDelta = marzoDelta;
    }

    public Double getAbrilDelta() {
        return abrilDelta;
    }

    public void setAbrilDelta(Double abrilDelta) {
        this.abrilDelta = abrilDelta;
    }

    public Double getMayoDelta() {
        return mayoDelta;
    }

    public void setMayoDelta(Double mayoDelta) {
        this.mayoDelta = mayoDelta;
    }

    public Double getJunioDelta() {
        return junioDelta;
    }

    public void setJunioDelta(Double junioDelta) {
        this.junioDelta = junioDelta;
    }

    public Double getJulioDelta() {
        return julioDelta;
    }

    public void setJulioDelta(Double julioDelta) {
        this.julioDelta = julioDelta;
    }

    public Double getAgostoDelta() {
        return agostoDelta;
    }

    public void setAgostoDelta(Double agostoDelta) {
        this.agostoDelta = agostoDelta;
    }

    public Double getSeptiembreDelta() {
        return septiembreDelta;
    }

    public void setSeptiembreDelta(Double septiembreDelta) {
        this.septiembreDelta = septiembreDelta;
    }

    public Double getOctubreDelta() {
        return octubreDelta;
    }

    public void setOctubreDelta(Double octubreDelta) {
        this.octubreDelta = octubreDelta;
    }

    public Double getNoviembreDelta() {
        return noviembreDelta;
    }

    public void setNoviembreDelta(Double noviembreDelta) {
        this.noviembreDelta = noviembreDelta;
    }

    public Double getDiciembreDelta() {
        return diciembreDelta;
    }

    public void setDiciembreDelta(Double diciembreDelta) {
        this.diciembreDelta = diciembreDelta;
    }


    public List<Key<ProductoPSA>> getKeys() {
        return keys;
    }

    public void setKeys(List<Key<ProductoPSA>> keys) {
        this.keys = keys;
    }
}
