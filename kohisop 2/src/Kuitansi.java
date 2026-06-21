public class Kuitansi {
    private Pesanan pesanan;
    private IPaymentChannel channel;
    private IMataUang mataUang;

    public Kuitansi(Pesanan pesanan, IPaymentChannel channel, IMataUang mataUang) {
        this.pesanan = pesanan;
        this.channel = channel;
        this.mataUang = mataUang;
    }

    public void tampilkan() {
        cetakHeader();
        cetakItemMakanan();
        cetakItemMinuman();
        cetakFooter();
    }

    private double hitungTotalAkhirIDR(double idr) {
        return channel.hitungTotal(idr);
    }

    private double konversiMataUang(double idr) {
        return mataUang.konversiDariIDR(idr);
    }

    private void cetakHeader() {
        System.out.println("=======================================================================");
        System.out.println("                        KUITANSI KOHISOP                              ");
        System.out.println("=======================================================================");
    }

    private void cetakItemMinuman() {
        System.out.println("\n--- Daftar Minuman ---");
        for (OrderItem item : pesanan.getItemMinuman()) {
            MenuItem menu = item.getMenuItem();

            System.out.printf("Kode: %s | %s (IDR %.2f / porsi)\n", menu.getKode(), menu.getNama(), menu.getHarga());
            System.out.printf("Kuantitas    : %d porsi\n", item.getKuantitas());
            System.out.printf("Total pajak    : IDR %.2f\n", item.getPajak());
            System.out.printf("Subtotal    : IDR %.2f\n\n", item.getSubtotal());
        }
    }

    private void cetakItemMakanan() {
        System.out.println("\n--- Daftar Makanan ---");
        for (OrderItem item : pesanan.getItemMakanan()) {
            MenuItem menu = item.getMenuItem();

            System.out.printf("Kode: %s | %s (IDR %.2f / porsi)\n", menu.getKode(), menu.getNama(), menu.getHarga());
            System.out.printf("Kuantitas    : %d porsi\n", item.getKuantitas());
            System.out.printf("Total pajak    : IDR %.2f\n", item.getPajak());
            System.out.printf("Subtotal    : IDR %.2f\n\n", item.getSubtotal());
        }
    }

    private void cetakFooter() {
        System.out.println("=======================================================================");
        double totalDiluarPajakIDR = 0;
        for (OrderItem item : pesanan.getItemMinuman()) {
            totalDiluarPajakIDR += item.getSubtotal();
        }
        for (OrderItem item : pesanan.getItemMakanan()) {
            totalDiluarPajakIDR += item.getSubtotal();
        }

        double totalDenganPajakIDR = pesanan.getTotalDenganPajak();
        double nominalDiskonIDR = totalDenganPajakIDR * channel.getDiskon();
        double nominalAdminIDR = channel.getBiayaAdmin();

        String simbol = mataUang.getSimbol();

        double totalAwalMataUang = konversiMataUang(totalDiluarPajakIDR);

        double tagihanAkhirIDR = hitungTotalAkhirIDR(totalDenganPajakIDR);
        double totalAkhirMataUang = konversiMataUang(tagihanAkhirIDR);

        System.out.printf("Total Harga (di luar pajak)     : IDR %.2f\n", totalDiluarPajakIDR);
        System.out.printf("Total Harga (dengan pajak)      : IDR %.2f\n", totalDenganPajakIDR);
        System.out.printf("Metode Pembayaran               : %s\n", channel.getNama());
        System.out.printf("Besar Diskon                    : IDR %.2f\n", nominalDiskonIDR);
        if (nominalAdminIDR > 0) {
            System.out.printf("Biaya Tambahan (Admin)          : IDR %.2f\n", nominalAdminIDR);
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("Total Tagihan AWAL (%s)        : %s %.2f\n", simbol, simbol, totalAwalMataUang);
        System.out.printf("Total Tagihan AKHIR (%s)       : %s %.2f\n", simbol, simbol, totalAkhirMataUang);

        System.out.println("=======================================================================");
        System.out.println("             Terima kasih dan silakan datang kembali!                  ");
        System.out.println("=======================================================================");
    }
}
