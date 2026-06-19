public abstract class MenuItem {
    private String kode;
    private String nama;
    private double harga;

    public MenuItem(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public abstract String getKategori();

    protected abstract double hitungPajak();

}
    