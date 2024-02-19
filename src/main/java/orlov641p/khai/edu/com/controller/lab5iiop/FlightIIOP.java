package orlov641p.khai.edu.com.controller.lab5iiop;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class FlightIIOP extends JFrame{
    private JTextField flightIdField;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField flightDateField;
    private JTextField numberOfSeatsField;
    private JTextArea consoleTextArea;

    Context ic;
    Object objref;
    ServerInterface serverInterface;

    public FlightIIOP() {
        setTitle("Flight Application");
        setSize(2000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        setVisible(true);

        try {
            ic = new InitialContext();

            objref = ic.lookup("ServerIIOP");
            System.out.println("FlightIIOP: Obtained a ref. to ServerIIOP.");

            serverInterface = (ServerInterface) PortableRemoteObject.narrow(objref, ServerInterface.class);

        } catch (Exception e) {
            System.err.println("Exception " + e + "Caught");
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        JLabel flightIdLabel = new JLabel("Flight ID:");
        JLabel originLabel = new JLabel("Origin:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel flightDateLabel = new JLabel("Order Date (YYYY-MM-DD HH:mm)");
        JLabel availableSeatsLabel = new JLabel("Number of seats");

        flightIdField = new JTextField();
        originField = new JTextField();
        destinationField = new JTextField();
        flightDateField = new JTextField();
        numberOfSeatsField = new JTextField();
        consoleTextArea = new JTextArea();

        flightIdField.setColumns(25);
        flightIdField.setFont(flightIdField.getFont().deriveFont(14f));

        originField.setColumns(25);
        originField.setFont(originField.getFont().deriveFont(14f));

        destinationField.setColumns(25);
        destinationField.setFont(destinationField.getFont().deriveFont(14f));

        flightDateField.setColumns(25);
        flightDateField.setFont(flightDateField.getFont().deriveFont(14f));

        numberOfSeatsField.setColumns(25);
        numberOfSeatsField.setFont(numberOfSeatsField.getFont().deriveFont(14f));

        consoleTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        consoleTextArea.setFont(consoleTextArea.getFont().deriveFont(18f));

        JButton findAllButton = new JButton("Find All");
        JButton getByIdButton = new JButton("Get by Id");
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update by id");
        JButton deleteByIdButton = new JButton("Delete by Id");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        panel.add(flightIdLabel);
        panel.add(flightIdField);
        panel.add(originLabel);
        panel.add(originField);
        panel.add(destinationLabel);
        panel.add(destinationField);
        panel.add(flightDateLabel);
        panel.add(flightDateField);
        panel.add(availableSeatsLabel);
        panel.add(numberOfSeatsField);
        panel.add(findAllButton);
        panel.add(getByIdButton);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteByIdButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.WEST);

        findAllButton.addActionListener(e -> findAll());

        getByIdButton.addActionListener(e -> getById());

        addButton.addActionListener(e -> add());

        updateButton.addActionListener(e -> update());

        deleteByIdButton.addActionListener(e -> deleteById());
    }

    private void findAll() {
        try {
            consoleTextArea.setText(serverInterface.sendFlightMessage( "FIND_ALL"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void getById() {
        String flightId = flightIdField.getText();

        try {
            consoleTextArea.setText(serverInterface.sendFlightMessage( "GET_BY_ID/" + flightId));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteById() {
        String flightId = flightIdField.getText();

        try {
            consoleTextArea.setText(serverInterface.sendFlightMessage("DELETE_BY_ID/" + flightId));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAddOrUpdateRequest(String operation){
        String flightId = flightIdField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        String date = flightDateField.getText();
        String numberOfSeats = numberOfSeatsField.getText();

        return operation + "/" + flightId + "/" + origin + "/" + destination + "/" + date + "/" + numberOfSeats;
    }

    private void add() {
        try {
            consoleTextArea.setText(serverInterface.sendFlightMessage(getAddOrUpdateRequest("ADD")));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(){
        try {
            consoleTextArea.setText(serverInterface.sendFlightMessage(getAddOrUpdateRequest("UPDATE")));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlightIIOP::new);
    }
}