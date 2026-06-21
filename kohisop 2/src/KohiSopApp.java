import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class KohiSopApp {

    private Map<String, MenuItem> daftarMenu;
    private Pesanan pesanan;
    private Scanner scanner;
    private List<Membership> daftarMember;

    public KohiSopApp() {
        daftarMenu  = new LinkedHashMap<>();
        pesanan     = new Pesanan();
        scanner     = new Scanner(System.in);

        daftarMember = new ArrayList<>();
    }

    public static void main(String[] args) {
        new KohiSopApp().run();
    }

    public void run() {
        initMenu();
        tampilkanMenu();
        inputPesanan();

        if (pesanan.isBatalkan()) {
            System.out.println("Pesanan dibatalkan. Terima kasih!");
            return;
        }

        inputKuantitas();

        if (pesanan.isBatalkan()) {
            System.out.println("Pesanan dibatalkan. Terima kasih!");
            return;
        }

        if (pesanan.getItemMinuman().isEmpty() && pesanan.getItemMakanan().isEmpty()) {
            System.out.println("Tidak ada item dalam pesanan. Program selesai!");
            return;
        }

        IPaymentChannel channel = pilihChannelPembayaran();
        IMataUang mataUang      = pilihaMataUang();
        tampilkanKuitansi(channel, mataUang);
    }

    private void initMenu() {
        //Minuman
        daftarMenu.put("A1", new Minuman("A1", "Caffe Latte",                         46));
        daftarMenu.put("A2", new Minuman("A2", "Cappuccino",                           46));
        daftarMenu.put("E1", new Minuman("E1", "Caffe Americano",                      37));
        daftarMenu.put("E2", new Minuman("E2", "Caffe Mocha",                          55));
        daftarMenu.put("E3", new Minuman("E3", "Caramel Macchiato",                    59));
        daftarMenu.put("E4", new Minuman("E4", "Asian Dolce Latte",                    55));
        daftarMenu.put("E5", new Minuman("E5", "Double Shots Iced Shaken Espresso",   50));
        daftarMenu.put("B1", new Minuman("B1", "Freshly Brewed Coffee",               23));
        daftarMenu.put("B2", new Minuman("B2", "Vanilla Sweet Cream Cold Brew",       50));
        daftarMenu.put("B3", new Minuman("B3", "Cold Brew",                           44));
        // Makanan
        daftarMenu.put("M1", new Makanan("M1", "Petemania Pizza",                    112));
        daftarMenu.put("M2", new Makanan("M2", "Mie Rebus Super Mario",               35));
        daftarMenu.put("M3", new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial",     72));
        daftarMenu.put("M4", new Makanan("M4", "Soto Kambing Iga Guling",            124));
        daftarMenu.put("S1", new Makanan("S1", "Singkong Bakar A La Carte",           37));
        daftarMenu.put("S2", new Makanan("S2", "Ubi Cilembu Bakar Arang",             58));
        daftarMenu.put("S3", new Makanan("S3", "Tempe Mendoan",                       18));
        daftarMenu.put("S4", new Makanan("S4", "Tahu Bakso Extra Telur",              28));
    }

    private void tampilkanMenu() {
        System.out.println("=======================================================================");
        System.out.println("                          MENU KOHISOP                                ");
        System.out.println("=======================================================================");
        System.out.printf("%-5s  %-38s  %s%n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println("-----------------------------------------------------------------------");
        for (MenuItem item : daftarMenu.values()) {
            if (item instanceof Minuman)
                System.out.printf("%-5s  %-38s  %.0f%n", item.getKode(), item.getNama(), item.getHarga());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-5s  %-38s  %s%n", "Kode", "Menu Makanan", "Harga (Rp)");
        System.out.println("-----------------------------------------------------------------------");
        for (MenuItem item : daftarMenu.values()) {
            if (item instanceof Makanan)
                System.out.printf("%-5s  %-38s  %.0f%n", item.getKode(), item.getNama(), item.getHarga());
        }
        System.out.println("=======================================================================");
    }

    private void inputPesanan() {
        System.out.println("\nMasukkan kode pesanan (Enter tanpa kode = selesai, CC = batal):");

        while (true) {
            System.out.print("Masukkan kode: ");
            String kode = scanner.nextLine().trim().toUpperCase();

            if (kode.equals("CC")) {
                pesanan.batalkan();
                return;
            }

            if (kode.isEmpty()) {
                if (pesanan.getItemMinuman().isEmpty() && pesanan.getItemMakanan().isEmpty()) {
                    System.out.println("Belum ada item yang dipesan. Silakan masukkan kode pesanan.");
                    continue;
                }
                break;
            }

            if (!isValidKode(kode)) {
                System.out.println("Error: Kode '" + kode + "' tidak valid! Silakan lihat tabel menu.");
                continue;
            }

            MenuItem menu = daftarMenu.get(kode);

            if (menu instanceof Minuman) {
                try {
                    pesanan.tambahMinuman(new OrderItem(menu, 1));
                    System.out.println("[+] " + menu.getNama() + " berhasil ditambahkan.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (menu instanceof Makanan) {
                try {
                    pesanan.tambahMakanan(new OrderItem(menu, 1));
                    System.out.println("[+] " + menu.getNama() + " ditambahkan.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if (pesanan.getItemMinuman().size() == 5 && pesanan.getItemMakanan().size() == 5) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("PERHATIAN: Keranjang Anda sudah kepenuhan! (5 Minuman & 5 Makanan)");
                System.out.println("Sistem akan otomatis melanjutkan pesanan...");
                break;
            }
        }
    }

    private void inputKuantitas() {
        System.out.println("\n=== INPUT KUANTITAS ===");
        tampilkanDaftarPesanan();
        System.out.println("(Enter = default 1 | 0 atau S = skip/batal item | CC = batal semua)\n");

        //Minuman
        if (!pesanan.getItemMinuman().isEmpty()) {
            System.out.println("--- Minuman (maks 3 porsi per jenis) ---");
            List<OrderItem> toRemove = new ArrayList<>();
            for (OrderItem item : pesanan.getItemMinuman()) {
                while (true) {
                    System.out.print("  Kuantitas " + item.getMenuItem().getNama() + " [1-3]: ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("CC")) {
                        pesanan.batalkan();
                        return;
                    }
                    if (input.isEmpty()){
                        item.setKuantitas(1);
                        break;
                    }
                    if (input.equalsIgnoreCase("S") || input.equals("0")) {
                        toRemove.add(item);
                        System.out.println("  -> " + item.getMenuItem().getNama() + " dibatalkan.");
                        break;
                    }

                    try {
                        int qty = Integer.parseInt(input);
                        item.setKuantitas(qty);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("  ERROR: Input harus berupa angka bulat!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("  ERROR: " + e.getMessage());
                    }
                }
            }
            toRemove.forEach(i -> pesanan.hapusItem(i.getMenuItem().getKode()));
        }

        //Makanan
        if (!pesanan.getItemMakanan().isEmpty()) {
            System.out.println("\n--- Makanan (maks 2 porsi per jenis) ---");
            List<OrderItem> toRemove = new ArrayList<>();
            for (OrderItem item : pesanan.getItemMakanan()) {
                while (true) {
                    System.out.print("  Kuantitas " + item.getMenuItem().getNama() + " [1-2]: ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("CC")) {
                        pesanan.batalkan();
                        return;
                    }
                    if (input.isEmpty()) {
                        item.setKuantitas(1);
                        break;
                    }
                    if (input.equalsIgnoreCase("S") || input.equals("0")) {
                        toRemove.add(item);
                        System.out.println("  -> " + item.getMenuItem().getNama() + " dibatalkan.");
                        break;
                    }
                    try {
                        int qty = Integer.parseInt(input);
                        item.setKuantitas(qty);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("  ERROR: Input harus berupa angka bulat!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("  ERROR: " + e.getMessage());
                    }
                }
            }
            toRemove.forEach(i -> pesanan.hapusItem(i.getMenuItem().getKode()));
        }
    }

    private void tampilkanDaftarPesanan() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-5s  %-38s  %s%n", "Kode", "Minuman", "Kuantitas");
        System.out.println("-----------------------------------------------------------------------");
        if (pesanan.getItemMinuman().isEmpty()) System.out.println("  (tidak ada)");
        else for (OrderItem i : pesanan.getItemMinuman())
            System.out.printf("%-5s  %-38s  %d%n", i.getMenuItem().getKode(), i.getMenuItem().getNama(), i.getKuantitas());

        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-5s  %-38s  %s%n", "Kode", "Makanan", "Kuantitas");
        System.out.println("-----------------------------------------------------------------------");
        if (pesanan.getItemMakanan().isEmpty()) System.out.println("  (tidak ada)");
        else for (OrderItem i : pesanan.getItemMakanan())
            System.out.printf("%-5s  %-38s  %d%n", i.getMenuItem().getKode(), i.getMenuItem().getNama(), i.getKuantitas());
        System.out.println("-----------------------------------------------------------------------");
    }

    private IPaymentChannel pilihChannelPembayaran() {
        System.out.println("\n=== PILIH CHANNEL PEMBAYARAN ===");
        System.out.println("1. Tunai   - Tidak ada diskon");
        System.out.println("2. QRIS    - Diskon 5%");
        System.out.println("3. eMoney  - Diskon 7%, biaya admin Rp 20");

        while (true) {
            System.out.print("Pilih (1/2/3): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    return new Tunai();

                case "2":
                    System.out.print("Masukkan saldo QRIS (IDR): ");
                    try {
                        double saldo = Double.parseDouble(scanner.nextLine().trim());
                        QRIS qris = new QRIS(saldo);
                        double totalAkhir = qris.hitungTotal(pesanan.getTotalDenganPajak());
                        if (!qris.validasiSaldo(totalAkhir)) {
                            System.out.printf("Error: Saldo tidak cukup! Dibutuhkan: %.2f IDR%n", totalAkhir);
                        } else return qris;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Input tidak valid!");
                    }
                    break;

                case "3":
                    System.out.print("Masukkan saldo eMoney (IDR): ");
                    try {
                        double saldo = Double.parseDouble(scanner.nextLine().trim());
                        EMoney emoney = new EMoney(saldo);
                        double totalAkhir = emoney.hitungTotal(pesanan.getTotalDenganPajak());
                        if (!emoney.validasiSaldo(totalAkhir)) {
                            System.out.printf("Error: Saldo tidak cukup! Dibutuhkan: %.2f IDR%n", totalAkhir);
                        } else return emoney;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Input tidak valid!");
                    }
                    break;

                default:
                    System.out.println("Error: Pilihan tidak valid! Masukkan 1, 2, atau 3.");
            }
        }
    }

    private IMataUang pilihaMataUang() {
        System.out.println("\n=== PILIH MATA UANG ===");
        System.out.println("1. IDR  (Rupiah)");
        System.out.println("2. USD  (1 USD = 15 IDR)");
        System.out.println("3. JPY  (10 JPY = 1 IDR)");
        System.out.println("4. MYR  (1 MYR = 4 IDR)");
        System.out.println("5. EUR  (1 EUR = 14 IDR)");

        while (true) {
            System.out.print("Pilih mata uang (1-5): ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return new IDR();
                case "2": return new USD();
                case "3": return new JPY();
                case "4": return new MYR();
                case "5": return new EUR();
                default:  System.out.println("Error: Pilihan tidak valid! Masukkan 1-5.");
            }
        }
    }

    private void tampilkanKuitansi(IPaymentChannel channel, IMataUang mataUang) {
        Kuitansi kuitansi = new Kuitansi(pesanan, channel, mataUang);
        kuitansi.tampilkan();
    }

    private boolean isValidKode(String kode) {
        return daftarMenu.containsKey(kode);
    }
}