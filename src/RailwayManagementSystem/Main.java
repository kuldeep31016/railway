package RailwayManagementSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import Trips.BookTrip;
import Trips.Trip;
import Trips.TripsDatabase;

public class Main {

	private static JFrame frame;
	private static JPanel table;
	private static GridLayout gridLayout;
	private static Database database;

	// Color scheme constants
	private static final Color BACKGROUND_COLOR = Color.decode("#EBFFD8");
	private static final Color DARK_ROW_COLOR = Color.decode("#012030");
	private static final Color LIGHT_ROW_COLOR = Color.WHITE;
	private static final Color DARK_TEXT_COLOR = Color.decode("#012030");
	private static final Color LIGHT_TEXT_COLOR = Color.WHITE;
	private static final Color BUTTON_COLOR = Color.decode("#45C4B0");  // Light teal
	private static final Color HIGHLIGHT_COLOR = Color.decode("#136756");

	public static void main(String[] args) throws SQLException {
		database = new Database();

		frame = new JFrame("Railway Management System");
		frame.setSize(1050, 650);
		frame.getContentPane().setBackground(BACKGROUND_COLOR);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout(20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 40, 30, 40));

		// Title
		JLabel title = new JLabel("Welcome to Railway Management System");
		title.setForeground(DARK_TEXT_COLOR);
		title.setFont(new Font(null, Font.BOLD, 35));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, BorderLayout.NORTH);

		// Table setup
		gridLayout = new GridLayout(6, 1);
		table = new JPanel(gridLayout);
		table.setBackground(BACKGROUND_COLOR);

		ArrayList<Trip> trips = TripsDatabase.getAllTrips(database);
		refreshTable(trips);

		JScrollPane sp = new JScrollPane(table);
		panel.add(sp, BorderLayout.CENTER);

		// Modify Button - Black text on light teal
		JButton modify = new JButton("Modify");
		modify.setForeground(Color.BLACK);  // Text color changed to BLACK
		modify.setBackground(BUTTON_COLOR); // Background: #45C4B0 (light teal)
		modify.setFont(new Font(null, Font.BOLD, 22));
		modify.setFocusPainted(false);
		modify.addActionListener(e -> new ModifyList(frame, database));
		panel.add(modify, BorderLayout.SOUTH);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public static void refreshTable(ArrayList<Trip> trips) {
		table.removeAll();
		int rows = Math.max(trips.size() + 1, 6);
		gridLayout.setRows(rows);
		table.add(createTableRow(0, null));
		for (int i = 0; i < trips.size(); i++) {
			table.add(createTableRow(i + 1, trips.get(i)));
		}
		table.revalidate();
		table.repaint();
	}

	private static JPanel createTableRow(int index, Trip trip) {
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		boolean isHeader = index == 0;
		boolean isEvenRow = index % 2 == 0;

		Color bgColor = isHeader ? HIGHLIGHT_COLOR :
				(isEvenRow ? DARK_ROW_COLOR : LIGHT_ROW_COLOR);
		Color textColor = isHeader ? LIGHT_TEXT_COLOR :
				(isEvenRow ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);

		row.setBackground(bgColor);
		row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

		if (trip != null) {
			row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			row.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						new BookTrip(frame, database, trip);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage());
					}
				}
			});
		}

		String[] values = getRowValues(trip);
		int[] widths = {100, 100, 100, 150, 65, 65, 70, 100};
		for (int i = 0; i < values.length; i++) {
			row.add(createLabel(values[i], widths[i], textColor));
		}

		return row;
	}

	private static String[] getRowValues(Trip trip) {
		if (trip == null) {
			return new String[]{"Train", "From", "To", "Date", "Dept", "Arr", "Price", "Status"};
		}
		return new String[]{
				trip.getTrain().getDescription(),
				trip.getStart(),
				trip.getDestination(),
				trip.getDate(),
				trip.getDepartureTime(),
				trip.getArrivalTime(),
				trip.getPrice() + " $",
				trip.getTrain().getCapacity() > trip.getBookedSeats() ? "Available" : "Booked"
		};
	}

	private static JLabel createLabel(String text, int width, Color color) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(width, 20));
		label.setFont(new Font(null, Font.PLAIN, 20));
		label.setForeground(color);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}