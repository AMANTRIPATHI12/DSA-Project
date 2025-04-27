import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class StudentRecordManager extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, rollField, courseField, marksField;

    private static final String FILE_NAME = "students.txt";

    public StudentRecordManager() {
        setTitle("Student Record Manager");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel - Input Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Roll No:"));
        rollField = new JTextField();
        formPanel.add(rollField);

        formPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        formPanel.add(courseField);

        formPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        formPanel.add(marksField);

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");

        formPanel.add(addButton);
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        // Center Panel - Table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Roll No", "Course", "Marks"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel - Buttons
        JPanel buttonPanel = new JPanel();

        JButton deleteButton = new JButton("Delete Selected");
        JButton loadButton = new JButton("Load Students");

        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        loadButton.addActionListener(e -> loadStudents());
        updateButton.addActionListener(e -> updateStudent());

        // Load students on startup
        loadStudents();
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String course = courseField.getText().trim();
        String marks = marksField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || course.isEmpty() || marks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{name, roll, course, marks});
        saveStudents();
        clearFields();
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String course = courseField.getText().trim();
            String marks = marksField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || course.isEmpty() || marks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setValueAt(name, selectedRow, 0);
            tableModel.setValueAt(roll, selectedRow, 1);
            tableModel.setValueAt(course, selectedRow, 2);
            tableModel.setValueAt(marks, selectedRow, 3);

            saveStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            // No saved data yet, it's okay
        }
    }

    private void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                pw.println(
                    tableModel.getValueAt(i, 0) + "," +
                    tableModel.getValueAt(i, 1) + "," +
                    tableModel.getValueAt(i, 2) + "," +
                    tableModel.getValueAt(i, 3)
                );
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving students.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        courseField.setText("");
        marksField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentRecordManager().setVisible(true);
        });
    }
}
