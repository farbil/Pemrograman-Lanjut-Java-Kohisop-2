public interface IPaymentChannel {
    String getNama();
    double getDiskon();
    double getBiayaAdmin();
    double hitungTotal(double subtotal);
}


class Tunai implements IPaymentChannel {

    @Override
    public String getNama() {
        return "Tunai";
    }

    @Override
    public double getDiskon() {
        return 0.0;
    }

    @Override
    public double getBiayaAdmin() {
        return 0.0;
    }

    @Override
    public double hitungTotal(double subtotal) {
        return subtotal - (subtotal * getDiskon()) + getBiayaAdmin();
    }
}


class QRIS implements IPaymentChannel {
    private double saldo;

    public QRIS(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double amount) {
        this.saldo = amount;
    }

    @Override
    public String getNama() {
        return "QRIS";
    }

    @Override
    public double getDiskon() {
        return 0.05;
    }

    @Override
    public double getBiayaAdmin() {
        return 0;
    }

    @Override
    public double hitungTotal(double subtotal) {
        return subtotal - (subtotal * getDiskon()) + getBiayaAdmin();
    }

    public boolean validasiSaldo(double total) {
        return saldo >= total;
    }
}


class EMoney implements IPaymentChannel {
    private double saldo;

    public EMoney(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double amount) {
        this.saldo = amount;
    }

    @Override
    public String getNama() {
        return "EMoney";
    }

    @Override
    public double getDiskon() {
        return 0.07;
    }

    @Override
    public double getBiayaAdmin() {
        return 20;
    }

    @Override
    public double hitungTotal(double subtotal) {
        return subtotal - (subtotal * getDiskon()) + getBiayaAdmin();
    }

    public boolean validasiSaldo(double total) {
        return saldo >= total;
    }
}