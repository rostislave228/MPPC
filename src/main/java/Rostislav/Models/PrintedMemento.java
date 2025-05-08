package Rostislav.Models;

public class PrintedMemento {
    private final PrintedProductForm lastState;

    public PrintedMemento(PrintedProductForm orderState) {
        this.lastState = new PrintedProductForm(orderState);
    }

    public PrintedProductForm getState() {
        return lastState;
    }
}
