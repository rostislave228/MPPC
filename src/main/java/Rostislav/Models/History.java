package Rostislav.Models;

import java.util.Stack;

import org.springframework.stereotype.Component;

@Component
public class History {
    private final Stack<PrintedMemento> history = new Stack<>();

    public void saveState(PrintedProductForm order) {
        history.push(new PrintedMemento(order));
    }

    public PrintedProductForm restoreState() {
        if (!history.isEmpty()) {
            return history.pop().getState();
        }
        return null;
    }
}
