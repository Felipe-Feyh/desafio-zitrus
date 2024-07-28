package org.healthcare.dao;

import org.healthcare.utils.Sexo;

public class JavaBeans {
    private int cdProcedimento;
    private int nrIdade;
    private Sexo sexo;

    public JavaBeans() {}

    public int getCdProcedimento() {
        return cdProcedimento;
    }

    public void setCdProcedimento(int cdProcedimento) {
        this.cdProcedimento = cdProcedimento;
    }

    public int getNrIdade() {
        return nrIdade;
    }

    public void setNrIdade(int nrIdade) {
        this.nrIdade = nrIdade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
}
