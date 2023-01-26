import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WikiHTML {


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
    private JTextArea ta;
    private int WIDTH = 800;
    private int HEIGHT = 700;
    private JTextArea urlBox, search;
    private JLabel resultSection;

    public JTextField endURL;
    public ArrayList<String> pathway = new ArrayList();

    public WikiHTML() {
        //https://en.wikipedia.org/wiki/Lionel_Messi

        prepareGUI();
        showEventDemo();
        String url = urlTextField.getText();
        //"https://en.wikipedia.org/wiki/Lionel_Messi";
        String endURL = searchBar.getText();
        System.out.println("endURL: " + endURL);
        System.out.println("search thing is: " + searchBar.getText());

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


        ta = new JTextArea();
        ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);


        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 1));
        urlTextField = new JTextField();
        searchBar = new JTextField("searchBar");
        goButton = new Button("Go button.");
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

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            if (command.equals("go")) {
                // results.append("i have clicked go");
                findMyWiki(urlTextField.getText(), 0);

                for (int i = pathway.size() - 1; i >= 0; i--) {
                    System.out.println("pathway " + i + " is " + pathway.get(i));
                }
            }
        }
    }




    public boolean findMyWiki(String originalLink, int depth) {

        ArrayList<String> childLinks = new ArrayList<>();
//        ArrayList<String> pathway = new ArrayList<>();
        int xResult = 0;
        int yResult = 0;
        int endQuotationResult = 0;

        System.out.println("this is the depth: " + depth + " this is the original link: " + originalLink);
        System.out.println("this is the endLink: " + searchBar.getText());
        //base case
        if (originalLink.equals(searchBar.getText())) {
            System.out.println("found final link " + depth);
            return true;
        } else if (depth == 2) {

            return false;
        }


        else { //recursive case
            try {
                String finalString;
                //   System.out.print("hello \n");
                URL url = new URL(originalLink);
                //URL url2 = new URL("https://www.milton.edu/");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );
                String line;
                //    System.out.println(reader.readLine());
                while ((line = reader.readLine()) != null) {
                    //    System.out.println(line);
                    if (line.contains("\"/wiki/")) {
                        xResult = line.indexOf("\"/wiki/") + 1;
                        endQuotationResult = line.indexOf("\"", xResult + 1);
                        //System.out.println(xResult + " " + endQuotationResult);
                        //System.out.println("this is the line: " + line);
                        String halfString;
                        halfString = line.substring(xResult, endQuotationResult);

                        if (!originalLink.equals(halfString) && !halfString.contains(":")) {
                            halfString = "https://en.wikipedia.org" + halfString;

                            if (findMyWiki(halfString, depth + 1)) { // if ( call that recursion ) while you're doing that recursion you find the right link,
                                // return true because you find the right link
                                System.out.println("found it!!");
                                pathway.add(halfString);
                                return true;
                            }

                            //half string should not equal main page or messi page or include colons


                        }
                    }

                }
                reader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("your url is wrong");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("you have no internet");
            }

        }
        return false;
    }

  /* public void recursionSteps(String originalLink, int depth) {
        System.out.println(depth + "   " + originalLink);
        if (originalLink.contains(searchterm) | depth == 2) {
            if (originalLink.contains(searchterm)) {
                System.out.println("I found it!");
                foundFinalLink = true;
                //ResultsArea.append(depth);
            }


            if (depth == 2) {
                System.out.println("Not going to search through all that");
            }
        } else {
            recursionSteps(originalLink, depth + 1);

        }


    }*/


    public static void main(String[] args) {
        WikiHTML myApp = new WikiHTML();

    }
}
