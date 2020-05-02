package com.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NotNull
public class IntermediateResult {
    @Id
    @GeneratedValue
    private long id;
    private int TOS = 0;
    private int TES = 0;
    private int LPS = 0;
    private int OFS = 0;
    private int TAS = 0;
    //ВНУТРИ СВОЕЙ СФЕРЫ TOV - % теоретика внутри своей сферы; TEV - % техника внтури своей сферы; LPV - % лидера внутри своей сферы; OFV - % общественника внутри своей сферы; TAV - % творца внутри своей сферы
    private int TOV = 0;
    private int TEV = 0;
    private int LPV = 0;
    private int OFV = 0;
    private int TAV = 0;
    @Transient
    private int max = 0;

    public IntermediateResult() {
    }

    public IntermediateResult(int TOS, int TES, int LPS, int OFS, int TAS, int TOV, int TEV, int LPV, int OFV, int TAV, int max) {
        this.TOS = TOS;
        this.TES = TES;
        this.LPS = LPS;
        this.OFS = OFS;
        this.TAS = TAS;
        this.TOV = TOV;
        this.TEV = TEV;
        this.LPV = LPV;
        this.OFV = OFV;
        this.TAV = TAV;
        this.max = max;
    }

    public int getTOS() {
        return TOS;
    }

    public void setTOS(int TOS) {
        this.TOS = TOS;
    }

    public int getTES() {
        return TES;
    }

    public void setTES(int TES) {
        this.TES = TES;
    }

    public int getLPS() {
        return LPS;
    }

    public void setLPS(int LPS) {
        this.LPS = LPS;
    }

    public int getOFS() {
        return OFS;
    }

    public void setOFS(int OFS) {
        this.OFS = OFS;
    }

    public int getTAS() {
        return TAS;
    }

    public void setTAS(int TAS) {
        this.TAS = TAS;
    }

    public int getTOV() {
        return TOV;
    }

    public void setTOV(int TOV) {
        this.TOV = TOV;
    }

    public int getTEV() {
        return TEV;
    }

    public void setTEV(int TEV) {
        this.TEV = TEV;
    }

    public int getLPV() {
        return LPV;
    }

    public void setLPV(int LPV) {
        this.LPV = LPV;
    }

    public int getOFV() {
        return OFV;
    }

    public void setOFV(int OFV) {
        this.OFV = OFV;
    }

    public int getTAV() {
        return TAV;
    }

    public void setTAV(int TAV) {
        this.TAV = TAV;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
