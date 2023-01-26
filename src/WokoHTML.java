import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

    public class WokoHTML implements ActionListener {
        private JFrame mainFrame;
        private JLabel headerLabel;
        private JLabel statusLabel;
        private JPanel controlPanel;
        private JPanel panel1;
        private JPanel panel2;
        private JTextField urlTextField;
        private JTextField searchBar;
        private Button goButton;
        private JTextArea results;
        private JScrollPane scroll;
        private JMenuBar mb;
        private JMenu file, edit, help;
        private JMenuItem cut, copy, paste, selectAll;
        private JTextArea ta;
        private int WIDTH = 800;
        private int HEIGHT = 700;
        private JTextArea urlBox, search;
        private JLabel resultSection;


        public WokoHTML() {
            prepareGUI();
        }

        public static void main(String[] args) {
            WokoHTML swingControlDemo = new WokoHTML();
            swingControlDemo.showEventDemo();
        }

        private void prepareGUI() {
            mainFrame = new JFrame("Java SWING Examples");
            mainFrame.setSize(WIDTH, HEIGHT);
            mainFrame.setLayout(new GridLayout(2, 1));

            urlBox = new JTextArea();
            urlBox.setBounds(10, 10, 100, 10);

            search = new JTextArea();
            search.setBounds(10, 10, 100, 100);

            resultSection = new JLabel("results", JLabel.CENTER);
            resultSection.setSize(350, 100);

            cut = new JMenuItem("cut");
            copy = new JMenuItem("copy");
            paste = new JMenuItem("paste");
            selectAll = new JMenuItem("selectAll");
            cut.addActionListener(this);
            copy.addActionListener(this);
            paste.addActionListener(this);
            selectAll.addActionListener(this);

            mb = new JMenuBar();
            file = new JMenu("File");
            edit = new JMenu("Edit");
            help = new JMenu("Help");
            edit.add(cut);
            edit.add(copy);
            edit.add(paste);
            edit.add(selectAll);
            mb.add(file);
            mb.add(edit);
            mb.add(help);

            ta = new JTextArea();
            ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
            mainFrame.add(mb);
            // mainFrame.add(ta);
            mainFrame.setJMenuBar(mb);

            panel1 = new JPanel();
            panel1.setLayout(new GridLayout(1, 2));
            panel2 = new JPanel();
            panel2.setLayout(new GridLayout(2, 1));
            urlTextField = new JTextField();
            searchBar = new JTextField();
            goButton = new Button("This is a button.");
            goButton.setActionCommand("go");
            goButton.addActionListener(new ButtonClickListener());

            panel2.add(urlTextField);
            panel2.add(searchBar);
            panel1.add(panel2);
            panel1.add(goButton);
            results = new JTextArea("Hello");
            scroll = new JScrollPane(results);

            mainFrame.add(panel1/*, BorderLayout.NORTH*/);
//        mainFrame.add(results/*, BorderLayout.CENTER*/);
            mainFrame.add(scroll);


            headerLabel = new JLabel("", JLabel.CENTER);
            statusLabel = new JLabel("", JLabel.CENTER);
            statusLabel.setSize(350, 100);

            mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            GridLayout experimentLayout = new GridLayout(0, 2);


            new JButton("Button 1");
            new JButton("Button 2");
            new JButton("Button 3");
            new JButton("Long-Named Button 4");
            new JButton("5");

            controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout());

            // mainFrame.add(headerLabel);
            //  mainFrame.add(controlPanel);
            //mainFrame.add(statusLabel);
            mainFrame.setVisible(true);

        }

        private void showEventDemo() {
            headerLabel.setText("Control in action: Button");

            JButton okButton = new JButton("OK");
            JButton submitButton = new JButton("Submit");
            JButton cancelButton = new JButton("Cancel");

            okButton.setActionCommand("OK");
            submitButton.setActionCommand("Submit");
            cancelButton.setActionCommand("Cancel");

            okButton.addActionListener(new ButtonClickListener());
            submitButton.addActionListener(new ButtonClickListener());
            cancelButton.addActionListener(new ButtonClickListener());

            controlPanel.add(okButton);
            controlPanel.add(submitButton);
            controlPanel.add(cancelButton);

            mainFrame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cut)
                ta.cut();
            if (e.getSource() == paste)
                ta.paste();
            if (e.getSource() == copy)
                ta.copy();
            if (e.getSource() == selectAll)
                ta.selectAll();
        }

        private class ButtonClickListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();

                if (command.equals("OK")) {
                    statusLabel.setText("Ok Button clicked.");
                } else if (command.equals("Submit")) {
                    statusLabel.setText("Submit Button clicked.");
                } else if (command.equals("go")) {
                    // results.append("i have clicked go");
                    HtmlRead();

                } else {

                    statusLabel.setText("Cancel Button clicked.");
                }
            }
        }


        public void HtmlRead() {
            int a = 0;
            int b = 0;

            try {
                System.out.println();
                System.out.print("hello \n");
//            URL url = new URL("https://www.milton.edu/");
                URL url = new URL(urlTextField.getText());
             //   URL url = new URL("https://www.milton.edu/");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {

                    if (line.contains("https")) {
                        a = line.indexOf("https");
                        System.out.println(a);
                        String halfString;
                        halfString = line.substring(a, line.length());
                        if (halfString.contains("\"")) {
                            b = line.indexOf("\"", a);
                            System.out.println(b);
                        } else if (halfString.contains("'")) {
                            b = line.indexOf("'", a);
                            System.out.println(b);
                        } else if (halfString.contains("-")) {
                            b = line.indexOf("-", a);
                            System.out.println(b);
                        }


                        System.out.println(line);
                        String Links = line.substring(a, b);
                        System.out.println("******" + Links);
                        if (Links.contains(searchBar.getText())) {
                            results.append(Links);
                            results.append("\n");
                        }



                    }
                }
                reader.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }


