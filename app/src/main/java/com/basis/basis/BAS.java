package com.basis.basis;

public class BAS {
    private String BasNumber;
    private String Cliente;

    public BAS(String basNumber, String cliente) {
        BasNumber = basNumber;
        Cliente = cliente;
    }

    public String getBasNumber() {
        return BasNumber;
    }

    public void setBasNumber(String basNumber) {
        BasNumber = basNumber;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }
}
