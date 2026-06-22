import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Pesanan {
    private List<OrderItem> listOrderItem = new ArrayList<>();
    private boolean pesananDibatalkan = false;

    public void tambahMinuman(OrderItem item) {
        List<OrderItem> daftarMinumanSaatIni = getItemMinuman();

        if (isFull(daftarMinumanSaatIni)) {
            throw new IllegalArgumentException("Kategori Minuman sudah penuh (Maksimal 5 jenis).");
        }

        if (findItem(daftarMinumanSaatIni, item.getMenuItem().getKode()) != null) {
            throw new IllegalArgumentException(item.getMenuItem().getNama() + " sudah pernah ditambahkan!");
        }

        listOrderItem.add(item);
    }

    public void tambahMakanan(OrderItem item) {
        List<OrderItem> daftarMakananSaatIni = getItemMakanan();

        if (isFull(daftarMakananSaatIni)) {
            throw new IllegalArgumentException("Kategori Makanan sudah penuh (Maksimal 5 jenis).");
        }

        if (findItem(daftarMakananSaatIni, item.getMenuItem().getKode()) != null) {
            throw new IllegalArgumentException(item.getMenuItem().getNama() + " sudah pernah ditambahkan!");
        }

        listOrderItem.add(item);
    }

    public void hapusItem(String kode) {
        listOrderItem.removeIf(item -> item.getMenuItem().getKode().equalsIgnoreCase(kode));
    }

    public List<OrderItem> getItemMinuman() {
        List<OrderItem> minuman = new ArrayList<>();
        for(OrderItem item : listOrderItem) {
            if(item.getMenuItem() instanceof Minuman) {
                minuman.add(item);
            }
        }

        minuman.sort(
                Comparator.comparingDouble(
                        i -> i.getMenuItem().getHarga()
                )
        );

        return minuman;
    }

    public List<OrderItem> getItemMakanan() {
        List<OrderItem> makanan = new ArrayList<>();
        for(OrderItem item : listOrderItem) {
            if(item.getMenuItem() instanceof Makanan) {
                makanan.add(item);
            }
        }

        makanan.sort(
                Comparator.comparingDouble(
                        i -> i.getMenuItem().getHarga()
                )
        );

        return makanan;
    }

    public double getTotalDenganPajak(Membership member) {
        double total = 0;
        for(OrderItem item : listOrderItem) {
            total += item.getTotalDenganPajak(member);
        }
        return total;
    }

    public void batalkan() {
        this.pesananDibatalkan = true;
    }

    public boolean isBatalkan() {
        return pesananDibatalkan;
    }

    private boolean isFull(List<OrderItem> list) {
        return list.size() >=5;
    }

    private OrderItem findItem(List<OrderItem> list, String kode) {
        for (OrderItem item : list) {
            if (item.getMenuItem().getKode().equalsIgnoreCase(kode)) {
                return item;
            }
        }
        return null;
    }
}
