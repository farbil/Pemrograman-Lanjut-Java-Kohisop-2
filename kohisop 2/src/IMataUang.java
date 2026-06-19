public interface IMataUang {
    String getSimbol();
    double getNilaiTukarIDR();
    double konversiDariIDR(double idr);
}

class IDR implements IMataUang {
    @Override
    public String getSimbol() {
        return "IDR";
    }

    @Override
    public double getNilaiTukarIDR() {
        return 1;
    }

    @Override
    public double konversiDariIDR(double idr) {
        return idr;
    }
}

class USD implements IMataUang {
    private double rate = 15;

    @Override
    public String getSimbol() {
        return "USD";
    }

    @Override
    public double getNilaiTukarIDR() {
        return rate;
    }

    @Override
    public double konversiDariIDR(double idr) {
        return idr / rate;
    }
}

class JPY implements IMataUang {
    private double rate = 0.1;

    @Override
    public String getSimbol() {
        return "JPY";
    }

    @Override
    public double getNilaiTukarIDR() {
        return rate;
    }

    @Override
    public double konversiDariIDR(double idr) {
        return idr / rate ;
    }
}

class MYR implements IMataUang {
    private double rate = 4;

    @Override
    public String getSimbol() {
        return "MYR";
    }

    @Override
    public double getNilaiTukarIDR() {
        return rate;
    }

    @Override
    public double konversiDariIDR(double idr) {
        return idr / rate;
    }
}

class EUR implements IMataUang {
    private double rate = 14;

    @Override
    public String getSimbol() {
        return "EUR";
    }

    @Override
    public double getNilaiTukarIDR() {
        return rate;
    }

    @Override
    public double konversiDariIDR(double idr) {
        return idr / rate;
    }
}