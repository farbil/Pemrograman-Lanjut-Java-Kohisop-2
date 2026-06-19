public class Minuman extends MenuItem {
    public Minuman (String kode, String nama, double harga) {
        super(kode, nama, harga);
    }

    public String getKategori() {
        return "Minuman";
    }

    @Override
    protected double hitungPajak() {
        double hrg = getHarga();
        if(hrg < 50) {
            return 0.0;
        } else if (hrg >= 50 && hrg <= 55){
            return hrg * 0.08;
        } else {
            return hrg * 0.11;
        }
    }
}