import java.util.Stack;

public class DapurMinuman {
    private Stack<OrderItem> antreanMinuman;

    public DapurMinuman() {
        antreanMinuman = new Stack<>();
    }

    public void tambahKeDapur(OrderItem pesanan) {
        antreanMinuman.push(pesanan);
    }

    public OrderItem prosesMinuman() {
        if (!antreanMinuman.isEmpty()) {
            return antreanMinuman.pop();
        }
        return null;
    }
}