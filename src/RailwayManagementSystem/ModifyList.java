package RailwayManagementSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import Employees.*;
import Passengers.*;
import Trains.*;
import Trips.*;

public class ModifyList {

	public ModifyList(JFrame oldFrame, Database database) {
		JFrame frame = new JFrame("Modify");
		frame.setSize(400, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(oldFrame);
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));

		JPanel panel = new JPanel(new GridLayout(9, 1, 10, 10)); // Changed to single column layout
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// Create buttons with consistent styling
		panel.add(createButton("Add Train", e -> {
			try { new AddTrain(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Edit Train", e -> {
			try { new EditTrain(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Add Employee", e -> {
			try { new AddEmployee(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Edit Employee", e -> {
			try { new EditEmployee(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Add Passenger", e -> {
			try { new AddPassenger(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Edit Passenger", e -> {
			try { new EditPassenger(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Add Trip", e -> {
			try { new AddTrip(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Edit Trip", e -> {
			try { new EditTrip(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		panel.add(createButton("Show Trip Passengers", e -> {
			try { new ShowTripPassengers(frame, database); }
			catch (SQLException ex) { handleError(frame, ex); }
		}));

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	private JButton createButton(String text, ActionListener action) {
		JButton btn = new JButton(text);
		btn.setBackground(Color.decode("#136756")); // Darker teal background
		btn.setForeground(Color.BLACK); // Black text for better contrast
		btn.setFont(new Font(null, Font.BOLD, 18));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btn.addActionListener(action);
		return btn;
	}

	private void handleError(JFrame frame, SQLException ex) {
		JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
}