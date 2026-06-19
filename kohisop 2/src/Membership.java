import java.util.Random;

public class Membership {
    private String kodeMember;
    private String nama;
    private int poin;

    public Membership(String nama) {
        this.nama = nama;
        this.poin = 0;
        this.kodeMember = membuatKodeAcak();
    }

    private String membuatKodeAcak() {
        String karakterTersedia = "ABCDEF0123456789";
        StringBuilder kode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int indexAcak = random.nextInt(karakterTersedia.length());
            kode.append(karakterTersedia.charAt(indexAcak));
        }
        return kode.toString();
    }

    public void tambahPoin(double totalBelanjaIDR) {
        int tambahanPoin = (int) (totalBelanjaIDR / 10);

        if (isBebasPajak()) {
            tambahanPoin = tambahanPoin * 2;
        }

        this.poin += tambahanPoin;
    }

    public boolean isBebasPajak() {
        return kodeMember.contains("A");
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getNama() {
        return nama;
    }

    public int getPoin() {
        return poin;
    }

    public void kurangiPoin(int jumlah) {
        this.poin -= jumlah;
    }
}
