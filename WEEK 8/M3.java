
import java.util.*;

public class M3 {
    public static void main(String[] args) {
        Show show = new Show("7 PM");

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking b1 = show.bookSeats(asha, Arrays.asList(
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5")));

        show.bookSeats(ravi, Arrays.asList(new RegularSeat("A2")));
        show.bookSeats(ravi, Arrays.asList(new ReclinerSeat("R1")));

        if (b1 != null) {
            b1.cancel();
        }

        show.bookSeats(neha, Arrays.asList(new RegularSeat("A2")));
    }
}

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Seat {
    private final String seatId;

    public Seat(String seatId) {
        if (seatId == null || seatId.trim().isEmpty()) {
            throw new IllegalArgumentException("Seat ID is required.");
        }

        this.seatId = seatId;
    }

    public String getSeatId() {
        return seatId;
    }

    public abstract double getPrice();
}

class RegularSeat extends Seat {
    public RegularSeat(String id) {
        super(id);
    }

    public double getPrice() {
        return 150;
    }
}

class PremiumSeat extends Seat {
    public PremiumSeat(String id) {
        super(id);
    }

    public double getPrice() {
        return 250;
    }
}

class ReclinerSeat extends Seat {
    public ReclinerSeat(String id) {
        super(id);
    }

    public double getPrice() {
        return 400;
    }
}

class Show {
    private final String time;
    private final Set<String> bookedSeats = new HashSet<>();
    private boolean started;

    public Show(String time) {
        this.time = time;
    }

    public Booking bookSeats(Customer customer, List<Seat> seats) {
        if (started) {
            System.out.println("Cannot book: show has already started.");
            return null;
        }

        if (customer == null || seats == null
                || seats.isEmpty() || seats.size() > 6) {
            System.out.println("Booking must contain 1 to 6 valid seats.");
            return null;
        }

        Set<String> requested = new HashSet<>();

        for (Seat seat : seats) {
            if (seat == null) {
                System.out.println("Cannot book: invalid seat.");
                return null;
            }

            if (bookedSeats.contains(seat.getSeatId())
                    || !requested.add(seat.getSeatId())) {
                System.out.println("Seat " + seat.getSeatId()
                        + " is already booked for this show.");
                return null;
            }
        }

        bookedSeats.addAll(requested);
        Booking booking = new Booking(customer, this, seats);

        StringJoiner ids = new StringJoiner(", ");
        for (Seat seat : seats) {
            ids.add(seat.getSeatId());
        }

        System.out.printf("Booking confirmed for %s: %s. Total: ₹%.2f.%n",
                customer.getName(), ids, booking.getTotal());

        return booking;
    }

    public void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            bookedSeats.remove(seat.getSeatId());
        }
    }

    public boolean hasStarted() {
        return started;
    }

    public void startShow() {
        started = true;
    }
}

class Booking {
    private final Customer customer;
    private final Show show;
    private final List<Seat> seats;
    private boolean cancelled;

    public Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    public double getTotal() {
        double total = 0;

        for (Seat seat : seats) {
            total += seat.getPrice();
        }

        return total;
    }

    public void cancel() {
        if (cancelled) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        if (show.hasStarted()) {
            System.out.println("Cannot cancel: show has already started.");
            return;
        }

        show.releaseSeats(seats);
        cancelled = true;

        StringJoiner ids = new StringJoiner(", ");
        for (Seat seat : seats) {
            ids.add(seat.getSeatId());
        }

        System.out.println(customer.getName() + "'s booking cancelled.");
        System.out.println("Seats " + ids + " released.");
    }
}