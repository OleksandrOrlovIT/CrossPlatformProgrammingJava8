package orlov641p.khai.edu.com.controller.lab6idl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.awt.*;

public class ClientIDL extends JFrame {
    private JTextField clientIdField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField secondNameField;
    private JTextArea consoleTextArea;

    static ServerIDL serverIDL;

    public ClientIDL() {
        setTitle("Client Application");
        setSize(2000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientIDL::new);

        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "Hello";
            serverIDL = ServerIDLHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtained a handle on server object: " + serverIDL);

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }

    private void initializeComponents() {
        JLabel clientIdLabel = new JLabel("Client ID:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel secondNameLabel = new JLabel("Second Name:");

        clientIdField = new JTextField();
        lastNameField = new JTextField();
        firstNameField = new JTextField();
        secondNameField = new JTextField();
        consoleTextArea = new JTextArea();

        clientIdField.setColumns(25);
        clientIdField.setFont(clientIdField.getFont().deriveFont(14f));

        lastNameField.setColumns(25);
        lastNameField.setFont(lastNameField.getFont().deriveFont(14f));

        firstNameField.setColumns(25);
        firstNameField.setFont(firstNameField.getFont().deriveFont(14f));

        secondNameField.setColumns(25);
        secondNameField.setFont(secondNameField.getFont().deriveFont(14f));

        consoleTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        consoleTextArea.setFont(consoleTextArea.getFont().deriveFont(18f));

        JButton findAllButton = new JButton("Find All");
        JButton getByIdButton = new JButton("Get by Id");
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update by Id");
        JButton deleteByIdButton = new JButton("Delete by Id");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(clientIdLabel);
        panel.add(clientIdField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(secondNameLabel);
        panel.add(secondNameField);
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
        consoleTextArea.setText(serverIDL.sendClientMessage("FIND_ALL"));

    }

    private void getById() {
        String clientId = clientIdField.getText();

        consoleTextArea.setText(serverIDL.sendClientMessage("GET_BY_ID/" + clientId));

    }

    private void deleteById() {
        String clientId = clientIdField.getText();


        consoleTextArea.setText(serverIDL.sendClientMessage("DELETE_BY_ID/" + clientId));

    }

    private String getAddOrUpdateRequest(String operation) {
        String clientId = clientIdField.getText();
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();

        return operation + "/" + clientId + "/" + lastName + "/" + firstName + "/" + secondName;
    }

    private void add() {
        consoleTextArea.setText(serverIDL.sendClientMessage(getAddOrUpdateRequest("ADD")));
    }

    private void update() {
        consoleTextArea.setText(serverIDL.sendClientMessage(getAddOrUpdateRequest("UPDATE")));
    }
}