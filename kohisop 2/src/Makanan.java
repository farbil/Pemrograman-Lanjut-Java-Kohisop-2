public class Makanan extends MenuItem {
    public Makanan(String kode, String nama, double harga) {
        super(kode, nama, harga);
    }

    @Override
    public String getKategori() {
        return "Makanan";
    }

    @Override
    protected double hitungPajak() {
        double hrg = getHarga();
        if(hrg > 50) {
            return hrg * 0.08;
        } else {
            return hrg * 0.11;
        }
    }
}

