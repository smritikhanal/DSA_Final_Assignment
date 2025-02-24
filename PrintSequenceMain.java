import java.util.Scanner;

class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

class PrintSequenceController {
    private int n;
    private NumberPrinter printer;
    private int count = 1;
    private boolean zeroTurn = true;

    public PrintSequenceController(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    public synchronized void printZero() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!zeroTurn) {
                wait();
            }
            printer.printZero();
            zeroTurn = false;
            notifyAll();
        }
    }

    public synchronized void printEven() throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (zeroTurn || count % 2 != 0) {
                wait();
            }
            printer.printEven(i);
            count++;
            zeroTurn = true;
            notifyAll();
        }
    }

    public synchronized void printOdd() throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while (zeroTurn || count % 2 == 0) {
                wait();
            }
            printer.printOdd(i);
            count++;
            zeroTurn = true;
            notifyAll();
        }
    }
}

public class PrintSequenceMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = scanner.nextInt();
        scanner.close();

        NumberPrinter printer = new NumberPrinter();
        PrintSequenceController controller = new PrintSequenceController(n, printer);

        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        zeroThread.start();
        evenThread.start();
        oddThread.start();
        
        try {
            zeroThread.join();
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
