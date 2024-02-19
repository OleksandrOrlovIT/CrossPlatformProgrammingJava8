package orlov641p.khai.edu.com.controller.lab5iiop;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class OrderIIOP extends JFrame {
    private JTextField orderIdField;
    private JTextField creditCardIdField;
    private JTextField deliveryAddressField;
    private JTextField orderDateField;
    private JTextField deliveryDateField;
    private JTextArea consoleTextArea;

    Context ic;
    Object objref;
    ServerInterface serverInterface;

    public OrderIIOP() {
        setTitle("Order Application");
        setSize(2000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        setVisible(true);

        try {
            ic = new InitialContext();

            objref = ic.lookup("ServerIIOP");
            System.out.println("OrderIIOP: Obtained a ref. to ServerIIOP.");

            serverInterface = (ServerInterface) PortableRemoteObject.narrow(objref, ServerInterface.class);

        } catch (Exception e) {
            System.err.println("Exception " + e + "Caught");
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        JLabel orderIdLabel = new JLabel("Order ID:");
        JLabel creditCardIdLabel = new JLabel("Credit Card ID:");
        JLabel deliveryAddressLabel = new JLabel("Delivery Address:");
        JLabel orderDateLabel = new JLabel("Order Date (YYYY-MM-DD HH:mm):");
        JLabel deliveryDateLabel = new JLabel("Delivery Date (YYYY-MM-DD HH:mm):");

        orderIdField = new JTextField();
        creditCardIdField = new JTextField();
        deliveryAddressField = new JTextField();
        orderDateField = new JTextField();
        deliveryDateField = new JTextField();
        consoleTextArea = new JTextArea();

        orderIdField.setColumns(25);
        orderIdField.setFont(orderIdField.getFont().deriveFont(14f));

        creditCardIdField.setColumns(25);
        creditCardIdField.setFont(creditCardIdField.getFont().deriveFont(14f));

        deliveryAddressField.setColumns(25);
        deliveryAddressField.setFont(deliveryAddressField.getFont().deriveFont(14f));

        orderDateField.setColumns(25);
        orderDateField.setFont(orderDateField.getFont().deriveFont(14f));

        deliveryDateField.setColumns(25);
        deliveryDateField.setFont(deliveryDateField.getFont().deriveFont(14f));

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

        panel.add(orderIdLabel);
        panel.add(orderIdField);
        panel.add(creditCardIdLabel);
        panel.add(creditCardIdField);
        panel.add(deliveryAddressLabel);
        panel.add(deliveryAddressField);
        panel.add(orderDateLabel);
        panel.add(orderDateField);
        panel.add(deliveryDateLabel);
        panel.add(deliveryDateField);
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
            consoleTextArea.setText(serverInterface.sendOrderMessage("FIND_ALL"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void getById() {
        String orderId = orderIdField.getText();

        try {
            consoleTextArea.setText(serverInterface.sendOrderMessage( "GET_BY_ID/" + orderId));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteById() {
        String orderId = orderIdField.getText();

        try {
            consoleTextArea.setText(serverInterface.sendOrderMessage("DELETE_BY_ID/" + orderId));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAddOrUpdateRequest(String operation) {
        String orderId = orderIdField.getText();
        String creditCardId = creditCardIdField.getText();
        String deliveryAddress = deliveryAddressField.getText();
        String orderDate = orderDateField.getText();
        String deliveryDate = deliveryDateField.getText();

        return operation + "/" + orderId + "/" + creditCardId + "/" + deliveryAddress + "/" + orderDate + "/" + deliveryDate;
    }

    private void add() {
        try {
            consoleTextArea.setText(serverInterface.sendOrderMessage(getAddOrUpdateRequest("ADD")));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void update() {
        try {
            consoleTextArea.setText(serverInterface.sendOrderMessage(getAddOrUpdateRequest("UPDATE")));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrderIIOP::new);
    }
}