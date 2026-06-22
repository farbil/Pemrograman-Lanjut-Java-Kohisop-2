import java.util.PriorityQueue;
import java.util.Comparator;

public class DapurMakanan {
    // Membuat antrean yang mengurutkan berdasarkan harga Makanan (Tertinggi ke Terendah)
    private PriorityQueue<OrderItem> antreanMakanan;

    public DapurMakanan() {
        antreanMakanan = new PriorityQueue<>(new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem item1, OrderItem item2) {
                // Membandingkan harga. Jika ingin yang paling murah dulu diproses, balik urutannya.
                return Double.compare(item2.getMenuItem().getHarga(), item1.getMenuItem().getHarga());
            }
        });
    }

    // Memasukkan pesanan ke dapur
    public void tambahKeDapur(OrderItem pesanan) {
        antreanMakanan.add(pesanan);
    }

    // Memproses dan mengeluarkan pesanan dari antrean dapur
    public OrderItem prosesMakanan() {
        return antreanMakanan.poll(); // poll() akan mengambil dan menghapus elemen dengan prioritas tertinggi
    }
}