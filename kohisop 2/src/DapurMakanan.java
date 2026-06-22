import java.util.PriorityQueue;
import java.util.Comparator;

public class DapurMakanan {
    private PriorityQueue<OrderItem> antreanMakanan;

    public DapurMakanan() {
        antreanMakanan = new PriorityQueue<>(new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem item1, OrderItem item2) {
                return Double.compare(item2.getMenuItem().getHarga(), item1.getMenuItem().getHarga());
            }
        });
    }

    public void tambahKeDapur(OrderItem pesanan) {
        antreanMakanan.add(pesanan);
    }

    public OrderItem prosesMakanan() {
        return antreanMakanan.poll();
    }
}