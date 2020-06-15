package com.example.demo.service;

import com.example.demo.domain.IntermediateResult;
import com.example.demo.domain.Test;
import org.springframework.stereotype.Service;

import java.util.stream.DoubleStream;

@Service
public class TestIntermediateResult {

    private Test item;

    public TestIntermediateResult() {
    }

    public void setItem(Test item) {
        this.item = item;
    }


    public IntermediateResult compute() {

        // TE: техник-исполнитель; ТО: теоретик-исследователь; LP: лидер-преподаватель;OF: общественник-филантроп; TА: творец-артист
        int TE = 0;
        int TO = 0;
        int LP = 0;
        int OF = 0;
        int TA = 0;
        int SUM;
        //СРЕДИ ВСЕХ СФЕР TOS - % теоретика среди всех сфер; TES - % техника от всех сфер; LPS - % лидера от всех сфер; OFS - % общественника среди всех сфер; TAS - % творца среди всех сфер
        double TOS = 0;
        double TES = 0;
        double LPS = 0;
        double OFS = 0;
        double TAS = 0;
        //ВНУТРИ СВОЕЙ СФЕРЫ TOV - % теоретика внутри своей сферы; TEV - % техника внтури своей сферы; LPV - % лидера внутри своей сферы; OFV - % общественника внутри своей сферы; TAV - % творца внутри своей сферы
        double TOV = 0;
        double TEV = 0;
        double LPV = 0;
        double OFV = 0;
        double TAV = 0;

        //Максимум среди всех сфер
        double MAX = 0;


//1 ВОПРОС: Тех и Общ
        if (item.getVal1() == -2 | item.getVal1() == -1) {
            TE = TE + Math.abs(item.getVal1()) + 1;
        }
        if (item.getVal1() == 1 | item.getVal1() == 2) {
            OF = OF + Math.abs(item.getVal1()) + 1;
        }

//2 ВОПРОС: Общ и Теор
        if (item.getB() == -2 | item.getB() == -1) {
            OF = OF + Math.abs(item.getB()) + 1;
        }
        if (item.getB() == 1 | item.getB() == 2) {
            TO = TO + Math.abs(item.getB()) + 1;
        }

//3 ВОПРОС: Теор и Лид; И ТО, И ТО
        if (item.getD() == -2 | item.getD() == -1) {
            TO = TO + Math.abs(item.getD()) + 1;
        }
        if (item.getD() == 2 | item.getD() == 3) {
            LP = LP + Math.abs(item.getD());
        }
        if (item.getD() == 1) {
            TO = TO + 2;
            LP = LP + 2;
        }

//4 ВОПРОС: Общественник и Творец; И ТО, И ТО
        if (item.getE() == -2 | item.getE() == -1) {
            OF = OF + Math.abs(item.getE()) + 1;
            //console.log("Общественник3 " + OF)
        }
        if (item.getE() == 2 | item.getE() == 3) {
            TA = TA + Math.abs(item.getE());
            //console.log("Творец1 " + TA)
        }
        if (item.getE() == 1) {
            OF = OF + 2;
            TA = TA + 2;
            //console.log("Общ3 " + OF + " " + "Творец1 " + TA)
        }

//5 ВОПРОС: Техник и Теоретик
        if (item.getF() == -2 | item.getF() == -1) {
            TE = TE + Math.abs(item.getF()) + 1;
            //console.log("Техник2 " + TE)
        }
        if (item.getF() == 1 | item.getF() == 2) {
            TO = TO + Math.abs(item.getF()) + 1;
            //console.log("Теоретик3 " + TO)
        }

//6 ВОПРОС: Творец и Техник
        if (item.getG() == -2 | item.getG() == -1) {
            TA = TA + Math.abs(item.getG()) + 1;
            //console.log("Творец2 " + TA)
        }
        if (item.getG() == 1 | item.getG() == 2) {
            TE = TE + Math.abs(item.getG()) + 1;
            //console.log("Техник3 " + TE)
        }

//7 ВОПРОС: Техник и Общественник;  И ТО, И ТО
        if (item.getH() == -2 | item.getH() == -1) {
            TE = TE + Math.abs(item.getH()) + 1;
            //console.log("Техник4 " + TE)
        }
        if (item.getH() == 2 | item.getH() == 3) {
            OF = OF + Math.abs(item.getH());
            //console.log("Общественник4 " + OF)
        }
        if (item.getH() == 1) {
            TE = TE + 2;
            OF = OF + 2;
            //console.log("Техник4 " + TE + " " + "Общ4 " + OF)
        }

//8 ВОПРОС: Лидер и Техник
        if (item.getK() == -2 | item.getK() == -1) {
            LP = LP + Math.abs(item.getK()) + 1;
            //console.log("Лидер2 " + LP)
        }
        if (item.getK() == 1 | item.getK() == 2) {
            TE = TE + Math.abs(item.getK()) + 1;
            //console.log("Техник5 " + TE)
        }

//9 ВОПРОС: Техник и Лидер
        if (item.getL() == -2 | item.getL() == -1) {
            TE = TE + Math.abs(item.getL()) + 1;
            //console.log("Техник6 " + TE)
        }
        if (item.getL() == 1 | item.getL() == 2) {
            LP = LP + Math.abs(item.getL()) + 1;
            //console.log("Лидер3 " + LP)
        }

//10 ВОПРОС: Творец и Теоретик
        if (item.getM() == -2 | item.getM() == -1) {
            TA = TA + Math.abs(item.getM()) + 1;
            //console.log("Творец3 " + TA)
        }
        if (item.getM() == 1 | item.getM() == 2) {
            TO = TO + Math.abs(item.getM()) + 1;
            //console.log("Теоретик4 " + TO)
        }

//11 ВОПРОС: Теоретик и Техник
        if (item.getN() == -2 | item.getN() == -1) {
            TO = TO + Math.abs(item.getN()) + 1;
            //console.log("Теоретик5 " + TO)
        }
        if (item.getN() == 1 | item.getN() == 2) {
            TE = TE + Math.abs(item.getN()) + 1;
            //console.log("Техник7 " + TE)
        }

//12 ВОПРОС: Техник и Творец
        if (item.getO() == -2 | item.getO() == -1) {
            TE = TE + Math.abs(item.getO()) + 1;
            //console.log("Техник8 " + TE)
        }
        if (item.getO() == 1 | item.getO() == 2) {
            TA = TA + Math.abs(item.getO()) + 1;
            //console.log("Творец4 " + TA)
        }

//13 ВОПРОС: Творец и Лидер; И ТО, И ТО
        if (item.getP() == -2 | item.getP() == -1) {
            TA = TA + Math.abs(item.getP()) + 1;
            //console.log("Творец5 " + TA)
        }
        if (item.getP() == 2 | item.getP() == 3) {
            LP = LP + Math.abs(item.getP());
            //console.log("Лидер4 " + LP)
        }
        if (item.getP() == 1) {
            TA = TA + 2;
            LP = LP + 2;
            //console.log("Творец5 " + TA + " " + "Лидер4 " + LP)
        }

//14 ВОПРОС: Лидер и Теоретик
        if (item.getR() == -2 | item.getR() == -1) {
            LP = LP + Math.abs(item.getR()) + 1;
            //console.log("Лидер5 " + LP)
        }
        if (item.getR() == 1 | item.getR() == 2) {
            TO = TO + Math.abs(item.getR()) + 1;
            //console.log("Теоретик6 " + TO)
        }

//15 ВОПРОС: Общественник и Лидер; И ТО, И ТО
        if (item.getS() == -2 | item.getS() == -1) {
            OF = OF + Math.abs(item.getS()) + 1;
            //console.log("Общественник5 " + OF)
        }
        if (item.getS() == 2 | item.getS() == 3) {
            LP = LP + Math.abs(item.getS());
            //console.log("Лидер6 " + LP)
        }
        if (item.getS() == 1) {
            OF = OF + 2;
            LP = LP + 2;
            //console.log("Общ5 " + OF + " " + "Лидер6 " + LP)
        }

//16 ВОПРОС: Теоретик и Общественник
        if (item.getT() == -2 | item.getT() == -1) {
            TO = TO + Math.abs(item.getT()) + 1;
            //console.log("Теоретик7 " + TO)
        }
        if (item.getT() == 1 | item.getT() == 2) {
            OF = OF + Math.abs(item.getT()) + 1;
            //console.log("Общест6 " + OF)
        }

//17 ВОПРОС: Общественник и Лидер; И ТО, И ТО
        if (item.getX() == -2 | item.getX() == -1) {
            OF = OF + Math.abs(item.getX()) + 1;
            //console.log("Общественник7 " + OF)
        }
        if (item.getX() == 2 | item.getX() == 3) {
            LP = LP + Math.abs(item.getX());
            //console.log("Лидер7 " + LP)
        }
        if (item.getX() == 1) {
            OF = OF + 2;
            LP = LP + 2;
            //console.log("Общ7 " + OF + " " + "Лидер7 " + LP)
        }

//18 ВОПРОС: Лидер и Теоретик
        if (item.getY() == -2 | item.getY() == -1) {
            LP = LP + Math.abs(item.getY()) + 1;
            //console.log("Лидер8 " + LP)
        }
        if (item.getY() == 1 | item.getY() == 2) {
            TO = TO + Math.abs(item.getY()) + 1;
            //console.log("Теоретик8 " + TO)
        }

//19 ВОПРОС: Техник и Общественник
        if (item.getZ() == -2 | item.getZ() == -1) {
            TE = TE + Math.abs(item.getZ()) + 1;
            //console.log("Техник9 " + TE)
        }
        if (item.getZ() == 1 | item.getZ() == 2) {
            OF = OF + Math.abs(item.getZ()) + 1;
            // console.log("Общест8 " + OF)
        }

//20 ВОПРОС: Общественник и Творец
        if (item.getQ() == -2 | item.getQ() == -1) {
            OF = OF + Math.abs(item.getQ()) + 1;
            //console.log("Общест9 " + OF)
        }
        if (item.getQ() == 1 | item.getQ() == 2) {
            TA = TA + Math.abs(item.getQ()) + 1;
            //console.log("Творец6 " + TA)
        }

//21 ВОПРОС: Теоретик и Лидер
        if (item.getW() == -2 | item.getW() == -1) {
            TO = TO + Math.abs(item.getW()) + 1;
            //console.log("Теоретик9 " + TO)
        }
        if (item.getW() == 1 | item.getW() == 2) {
            LP = LP + Math.abs(item.getW()) + 1;
            //console.log("Лидер9 " + LP)
        }

//22 ВОПРОС: Лидер и Творец
        if (item.getU() == -2 | item.getU() == -1) {
            LP = LP + Math.abs(item.getU()) + 1;
            //console.log("Лидер10 " + LP)
        }
        if (item.getU() == 1 | item.getU() == 2) {
            TA = TA + Math.abs(item.getU()) + 1;
            //console.log("Творец7 " + TA)
        }

//23 ВОПРОС: Творец и Теоретик
        if (item.getI() == -2 | item.getI() == -1) {
            TA = TA + Math.abs(item.getI()) + 1;
            //console.log("Творец8 " + TA)
        }
        if (item.getI() == 1 | item.getI() == 2) {
            TO = TO + Math.abs(item.getI()) + 1;
            //console.log("Теоретик10 " + TO)
        }

        SUM = TO + TE + LP + OF + TA;

        TOS = ((double) (TO) / SUM * 100);
        TES = ((double) (TE) / SUM * 100);
        LPS = ((double) (LP) / SUM * 100);
        OFS = ((double) (OF) / SUM * 100);
        TAS = ((double) (TA) / SUM * 100);

        TOV = ((double) (TO) / 30 * 100);
        TEV = ((double) (TE) / 27 * 100);
        LPV = ((double) (LP) / 30 * 100);
        OFV = ((double) (OF) / 27 * 100);
        TAV = ((double) (TA) / 24 * 100);

        MAX = DoubleStream.of(TOS, TES, LPS, OFS, TAS)
                .max().getAsDouble();

        return new IntermediateResult((int) Math.round(TOS), (int) Math.round(TES), (int) Math.round(LPS), (int) Math.round(OFS), (int) Math.round(TAS),
                (int) Math.round(TOV), (int) Math.round(TEV), (int) Math.round(LPV), (int) Math.round(OFV), (int) Math.round(TAV),
                (int) Math.round(MAX));
    }
}
