package com.example.abdullahaljubaer.frc_offline.Results;

/**
 * Created by ABDULLAH AL JUBAER on 09-04-18.
 */

public class ResultProducer {

    private String strSt = "St = ";
    private String strInter = "Interpretation = ";
    private String strUf = "Uf = ";
    private String strCs = "Cs = ";
    private String strCi = "Ci = ";
    private String strLs = "Ls = ";
    private String nRes;
    private String strFrLeft = "N(kg/ha)=";
    private String strFrMiddleT = "";
    private String strFrMiddleB= "";
    private String strFrRight = "X(";
    private String comp;
    private String frFinal = "Req. ";


    public ResultProducer(double st, String inter,
                          double uf, double cs, double ci,
                          double ls, double val,
                          String comp
                          /*,
                          double strFrLeft, double strFrMiddleT,
                          double strFrMiddleB, double strFrRight*/) {

        setStrSt(st);
        setStrInter(inter);
        setStrUf(uf);
        setStrCs(cs);
        setStrCi(ci);
        setStrLs(ls);
        setNRes(val);

        setStrFrLeft(uf);
        setStrFrMiddleT(ci);
        setStrFrMiddleB(cs);
        setStrFrRight(ls, st);

        this.comp = comp;

    }

    public String getStrUf() {
        return strUf;
    }

    public void setStrUf(double strUf) {
        this.strUf += String.valueOf( strUf );
    }

    public String getStrSt() {
        return strSt;
    }

    public void setStrSt(double st) {
        this.strSt += String.valueOf(st);
    }

    public String getStrInter() {
        return strInter;
    }

    public void setStrInter(String interpretation) {
        this.strInter += String.valueOf(interpretation);
    }

    public String getStrCs() {
        return strCs;
    }

    public void setStrCs(double cs) {
        this.strCs += String.valueOf(cs);
    }

    public String getStrCi() {
        return strCi;
    }

    public void setStrCi(double ci) {
        this.strCi += String.valueOf(ci);
    }

    public String getStrLs() {
        return strLs;
    }

    public void setStrLs(double ls) {
        this.strLs += String.valueOf(ls);
    }

    public String getStrFrLeft() {
        return strFrLeft;
    }

    public void setStrFrLeft(double frLeft) {
        this.strFrLeft += String.valueOf( frLeft ) + " - ";
    }

    public String getStrFrMiddleT() {
        return strFrMiddleT;
    }

    public void setStrFrMiddleT(double frMiddleT) {
        this.strFrMiddleT += String.valueOf(frMiddleT);
    }

    public String getStrFrMiddleB() {
        return strFrMiddleB;
    }

    public void setStrFrMiddleB(double frMiddleB) {
        this.strFrMiddleB += String.valueOf(frMiddleB);
    }

    public String getStrFrRight() {
        return strFrRight;
    }

    private void setStrFrRight(double ls, double st) {
        this.strFrRight += String.format("%.3f-%.3f)", st, ls);
    }

    public String getNRes() {
        return nRes;
    }

    public void setNRes(double n) {
        this.nRes = String.format("= %.3f", n);
    }

    public String getFrFinal() {
        return frFinal;
    }

    public void setFrFinal(String frn, double val, double comp, double res) {
        this.frFinal = String.format("Req. %s = %.3f X (100.0/%.1f) \n%10s= %.3f",
                frn, val, comp, " ", res);
    }

    public String getComp() {
        return comp;
    }

    public void setComp (String c){
        this.comp = c;
    }
}
