import java.util.*;

public class CinemaBookingSystem {

    static HashMap<String, String> morningShow = new HashMap<>();
    static HashMap<String, String> eveningShow = new HashMap<>();
    static HashMap<String, String> nightShow = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    // Initialize seats A1–C5
    public static void initializeSeats(HashMap<String, String> show) {
        char[] rows = {'A', 'B', 'C'};
        for (char row : rows) {
            for (int col = 1; col <= 5; col++) {
                String seat = row + Integer.toString(col);
                show.put(seat, "Available");
            }
        }
    }

    // Display seat layout
    public static void displaySeats(HashMap<String, String> show) {
        System.out.println("\n===== Seat Layout =====");
        for (char row = 'A'; row <= 'C'; row++) {
            for (int col = 1; col <= 5; col++) {
                String seat = row + "" + col;
                String status = show.get(seat);
                System.out.printf("%s(%s)\t", seat, status);
            }
            System.out.println();
        }
    }

    // Seat price
    public static int getSeatPrice(String seat) {
        char row = seat.charAt(0);
        switch (row) {
            case 'A': return 200;
            case 'B': return 150;
            case 'C': return 120;
            default: return 0;
        }
    }

    // ✅ PAYMENT (Cash removed)
    public static String paymentOption(int totalAmount) {
        System.out.println("\n===== PAYMENT OPTIONS =====");
        System.out.println("1. UPI");
        System.out.println("2. Debit/Credit Card");
        System.out.print("Select Payment Method: ");
        int option = sc.nextInt();

        String method;
        switch (option) {
            case 1: method = "UPI"; break;
            case 2: method = "Card"; break;
            default:
                System.out.println("❌ Invalid option! Defaulting to UPI.");
                method = "UPI";
        }

        System.out.println("\nProcessing payment of ₹" + totalAmount + " via " + method + "...");
        System.out.println("✅ Payment Successful!");

        return method;
    }

    // Book multiple seats
    public static void bookMultipleSeats(HashMap<String, String> show, String showName) {
        displaySeats(show);

        System.out.print("\nEnter seats to book (Ex: A1 A2 B3): ");
        sc.nextLine();
        String input = sc.nextLine().toUpperCase();

        String[] seats = input.split(" ");
        int total = 0;
        ArrayList<String> bookedSeats = new ArrayList<>();

        System.out.println("\n===== SELECTED SEAT COSTS =====");

        for (String seat : seats) {
            if (!show.containsKey(seat)) {
                System.out.println("❌ Invalid seat: " + seat);
                continue;
            }

            if (show.get(seat).equals("Booked")) {
                System.out.println("❌ Seat already booked: " + seat);
                continue;
            }

            int price = getSeatPrice(seat);
            System.out.println(seat + " = ₹" + price);

            show.put(seat, "Booked");
            total += price;
            bookedSeats.add(seat);
        }

        if (bookedSeats.isEmpty()) {
            System.out.println("❌ No valid seats booked!");
            return;
        }

        System.out.println("\n✅ TOTAL AMOUNT BEFORE PAYMENT: ₹" + total);

        // Payment
        String paymentMethod = paymentOption(total);

        // Receipt
        generateReceipt(showName, bookedSeats, total, paymentMethod);
    }

    // Cancel booking
    public static void cancelSeat(HashMap<String, String> show) {
        displaySeats(show);
        System.out.print("\nEnter seat to cancel: ");
        String seat = sc.next().toUpperCase();

        if (!show.containsKey(seat)) {
            System.out.println("❌ Invalid seat!");
            return;
        }

        if (show.get(seat).equals("Available")) {
            System.out.println("❌ Seat is not booked!");
            return;
        }

        show.put(seat, "Available");
        System.out.println("✅ Booking canceled for " + seat);
    }

    // Receipt
    public static void generateReceipt(String showName, ArrayList<String> seats,
                                       int total, String paymentMethod) {
        System.out.println("\n===== 🎟️ BOOKING RECEIPT =====");
        System.out.println("Show Time: " + showName);
        System.out.println("Seats Booked: " + seats);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Total Paid: ₹" + total);
        System.out.println("Thank You For Booking!");
        System.out.println("==================================");
    }

    // Select show
    public static HashMap<String, String> chooseShow() {
        System.out.println("\nSelect Show:");
        System.out.println("1. Morning Show");
        System.out.println("2. Evening Show");
        System.out.println("3. Night Show");
        System.out.print("Enter choice: ");
        int ch = sc.nextInt();

        switch (ch) {
            case 1: return morningShow;
            case 2: return eveningShow;
            case 3: return nightShow;
            default: return morningShow;
        }
    }

    public static String showName(int c) {
        switch (c) {
            case 1: return "Morning Show";
            case 2: return "Evening Show";
            case 3: return "Night Show";
            default: return "Unknown Show";
        }
    }

    public static void main(String[] args) {

        initializeSeats(morningShow);
        initializeSeats(eveningShow);
        initializeSeats(nightShow);

        int choice;

        do {
            System.out.println("\n===== CINEMA BOOKING SYSTEM =====");
            System.out.println("1. View Seats");
            System.out.println("2. Book Seats");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    HashMap<String, String> show = chooseShow();
                    displaySeats(show);
                    break;
                }
                case 2: {
                    System.out.println("\nSelect Show to Book:");
                    System.out.println("1. Morning Show");
                    System.out.println("2. Evening Show");
                    System.out.println("3. Night Show");
                    int showChoice = sc.nextInt();

                    HashMap<String, String> show =
                            (showChoice == 1) ? morningShow :
                            (showChoice == 2) ? eveningShow : nightShow;

                    bookMultipleSeats(show, showName(showChoice));
                    break;
                }
                case 3: {
                    HashMap<String, String> show = chooseShow();
                    cancelSeat(show);
                    break;
                }
                case 4:
                    System.out.println("✅ Exiting... Thank you!");
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        } while (choice != 4);
    }
}
