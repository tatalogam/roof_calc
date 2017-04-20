package com.tatalogam.roof_calc.bean;

import com.tatalogam.roof_calc.Constant;
import com.tatalogam.roof_calc.model.ProductModel;

public class CalculationBean {
    private String calctype;

    private Integer roofType;
    private Double buffer;
    private Integer length;
    private Integer width;
    private Integer degree;

    private Integer jumlahAnakan;
    private Integer anakan1Length;
    private Integer anakan1Width;
    private Integer anakan2Length;
    private Integer anakan2Width;
    private Integer anakan3Length;
    private Integer anakan3Width;

    private ProductModel pm; //genteng
    private Double coeff;

    //results variable
    private Integer area;
    private Integer pitch;
    private Integer pitchWaste;
    private Integer roofqty;
    private Double ridge;
    private Integer ts7575;
    private Integer ts7580;
    private Integer tr3245;
    private Integer screw_truss;
    private Integer screw_reng;
    private Integer bracket_l;
    private Integer dynabolt;

    public CalculationBean() {
        //default calcbean - roof pelana, length 1, width 1, deg 15, first roof tile
        this.calctype = "steelframe"; // default calctype, quick fix for SONY devices
        this.roofType = 1;
        this.buffer = Constant.buffer[0];
        this.length = 1;
        this.width = 1;
        this.degree = 15;
        this.jumlahAnakan = 0;
        this.coeff=1.0; //in case metalroof doesn't have coeff
    }

    public String getCalctype() {
        return calctype;
    }

    public void setCalctype(String calctype) {
        this.calctype=calctype;
    }

    public void setWidth(int val) {
        if (this.width == null) {
            this.width = 0;
        }
        this.width = val;
    }

    public void setLength(int val) {
        if (this.length == null) {
            this.length = 0;
        }
        this.length = val;
    }

    public void addAnakanLength(boolean longClick, int anakan) {
        switch (anakan) {
            case 1 :
                if (this.anakan1Length== null) {
                    this.anakan1Length = 0;
                }
                if (!longClick) {
                    this.anakan1Length += 1;
                } else {
                    this.anakan1Length += 5;
                }
                break;
            case 2 :
                if (this.anakan2Length== null) {
                    this.anakan2Length = 0;
                }
                if (!longClick) {
                    this.anakan2Length += 1;
                } else {
                    this.anakan2Length += 5;
                }
                break;
            case 3 :
                if (this.anakan3Length== null) {
                    this.anakan3Length = 0;
                }
                if (!longClick) {
                    this.anakan3Length += 1;
                } else {
                    this.anakan3Length += 5;
                }
                break;
        }
    }

    public void decAnakanLength(boolean longClick, int anakan) {
        switch (anakan) {
            case 1 :
                if (this.anakan1Length== null) {
                    this.anakan1Length = 0;
                }
                if (this.anakan1Length <= 0) {
                    this.anakan1Length = 0;
                    return;
                }
                if (!longClick) {
                    this.anakan1Length -= 1;
                } else {
                    this.anakan1Length -= 5;
                }
                break;
            case 2 :
                if (this.anakan2Length== null) {
                    this.anakan2Length = 0;
                }
                if (this.anakan2Length <= 0) {
                    this.anakan2Length = 0;
                    return;
                }
                if (!longClick) {
                    this.anakan2Length -= 1;
                } else {
                    this.anakan2Length -= 5;
                }
                break;
            case 3 :
                if (this.anakan3Length== null) {
                    this.anakan3Length = 0;
                }
                if (this.anakan3Length <= 0) {
                    this.anakan3Length = 0;
                    return;
                }
                if (!longClick) {
                    this.anakan3Length -= 1;
                } else {
                    this.anakan3Length -= 5;
                }
                break;
        }
    }

    public void addAnakanWidth(boolean longClick, int anakan) {
        switch (anakan) {
            case 1 :
                if (this.anakan1Width== null) {
                    this.anakan1Width= 0;
                }
                if (!longClick) {
                    this.anakan1Width+= 1;
                } else {
                    this.anakan1Width += 5;
                }
                break;
            case 2 :
                if (this.anakan2Width == null) {
                    this.anakan2Width = 0;
                }
                if (!longClick) {
                    this.anakan2Width += 1;
                } else {
                    this.anakan2Width += 5;
                }
                break;
            case 3 :
                if (this.anakan3Width== null) {
                    this.anakan3Width= 0;
                }
                if (!longClick) {
                    this.anakan3Width+= 1;
                } else {
                    this.anakan3Width+= 5;
                }
                break;
        }

    }

    public void decAnakanWidth(boolean longClick, int anakan) {
        switch (anakan) {
            case 1 :
                if (this.anakan1Width== null) {
                    this.anakan1Width= 0;
                }
                if (this.anakan1Width<= 0) {
                    this.anakan1Width= 0;
                    return;
                }
                if (!longClick) {
                    this.anakan1Width-= 1;
                } else {
                    this.anakan1Width-= 5;
                }
                break;
            case 2 :
                if (this.anakan2Width== null) {
                    this.anakan2Width= 0;
                }
                if (this.anakan2Width<= 0) {
                    this.anakan2Width= 0;
                    return;
                }
                if (!longClick) {
                    this.anakan2Width-= 1;
                } else {
                    this.anakan2Width-= 5;
                }
                break;
            case 3 :
                if (this.anakan3Width== null) {
                    this.anakan3Width= 0;
                }
                if (this.anakan3Width<= 0) {
                    this.anakan3Width= 0;
                    return;
                }
                if (!longClick) {
                    this.anakan3Width-= 1;
                } else {
                    this.anakan3Width-= 5;
                }
                break;
        }
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getRoofType() {
        return roofType;
    }

    public void setRoofType(Integer roofType) {
        this.roofType = roofType;
    }

    public Double getBuffer() {
        return buffer;
    }

    public void setBuffer(Double buffer) {
        this.buffer = buffer;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getJumlahAnakan() {
        return jumlahAnakan;
    }

    public void setJumlahAnakan(Integer jumlahAnakan) {
        this.jumlahAnakan = jumlahAnakan;
    }

    public Integer getAnakan1Length() {
        return anakan1Length;
    }

    public void setAnakan1Length(Integer anakan1Length) {
        this.anakan1Length = anakan1Length;
    }

    public Integer getAnakan1Width() {
        return anakan1Width;
    }

    public void setAnakan1Width(Integer anakan1Width) {
        this.anakan1Width = anakan1Width;
    }

    public Integer getAnakan2Length() {
        return anakan2Length;
    }

    public void setAnakan2Length(Integer anakan2Length) {
        this.anakan2Length = anakan2Length;
    }

    public Integer getAnakan2Width() {
        return anakan2Width;
    }

    public void setAnakan2Width(Integer anakan2Width) {
        this.anakan2Width = anakan2Width;
    }

    public Integer getAnakan3Length() {
        return anakan3Length;
    }

    public void setAnakan3Length(Integer anakan3Length) {
        this.anakan3Length = anakan3Length;
    }

    public Integer getAnakan3Width() {
        return anakan3Width;
    }

    public void setAnakan3Width(Integer anakan3Width) {
        this.anakan3Width = anakan3Width;
    }

    public ProductModel getPm() {
        return pm;
    }

    public void setPm(ProductModel pm) {
        this.pm = pm;
    }

    public Double getCoeff() {
        return coeff;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPitch() {
        return pitch;
    }

    public void setPitch(Integer pitch) {
        this.pitch = pitch;
    }

    public Integer getPitchWaste() {
        return pitchWaste;
    }

    public void setPitchWaste(Integer pitchWaste) {
        this.pitchWaste = pitchWaste;
    }

    public Integer getRoofqty() {
        return roofqty;
    }

    public void setRoofqty(Integer roofqty) {
        this.roofqty = roofqty;
    }

    public Double getRidge() {
        return ridge;
    }

    public void setRidge(Double ridge) {
        this.ridge = ridge;
    }

    public Integer getTs7575() {
        return ts7575;
    }

    public void setTs7575(Integer ts7575) {
        this.ts7575 = ts7575;
    }

    public Integer getTs7580() {
        return ts7580;
    }

    public void setTs7580(Integer ts7580) {
        this.ts7580 = ts7580;
    }

    public Integer getTr3245() {
        return tr3245;
    }

    public void setTr3245(Integer tr3245) {
        this.tr3245 = tr3245;
    }

    public Integer getScrew_truss() {
        return screw_truss;
    }

    public void setScrew_truss(Integer screw_truss) {
        this.screw_truss = screw_truss;
    }

    public Integer getScrew_reng() {
        return screw_reng;
    }

    public void setScrew_reng(Integer screw_reng) {
        this.screw_reng = screw_reng;
    }

    public Integer getBracket_l() {
        return bracket_l;
    }

    public void setBracket_l(Integer bracket_l) {
        this.bracket_l = bracket_l;
    }

    public Integer getDynabolt() {
        return dynabolt;
    }

    public void setDynabolt(Integer dynabolt) {
        this.dynabolt = dynabolt;
    }
}
