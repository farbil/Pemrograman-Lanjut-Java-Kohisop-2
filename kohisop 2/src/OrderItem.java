public class OrderItem {
    private MenuItem menuItem;
    private int kuantitas;
    private int maxKuantitas;

    public OrderItem(MenuItem menuItem, int kuantitas){
        this.menuItem = menuItem;
        this.maxKuantitas = (menuItem instanceof Minuman) ? 3 : 2;

        if(validateKuantitas(kuantitas)) {
            this.kuantitas = kuantitas;
        } else {
            throw new IllegalArgumentException("Kuantitas tidak valid! Maksimal pesanan adalah " + maxKuantitas + " porsi.");
        }
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int qty) {
        if(validateKuantitas(qty)) {
            this.kuantitas = qty;
        } else {
            throw new IllegalArgumentException("Kuantitas tidak valid! Maksimal pesanan adalah " + maxKuantitas + " porsi." );
        }
    }

    public double getSubtotal() {
        return menuItem.getHarga() * kuantitas;
    }

    public double getPajak() {
        return menuItem.hitungPajak() * kuantitas;
    }

    public double getTotalDenganPajak() {
        return getSubtotal() + getPajak();
    }

    private boolean validateKuantitas(int qty) {
        return qty >= 1 && qty <= maxKuantitas;
    }
}
