import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WikiHTML {

    String searchterm;
    boolean foundFinalLink;
    public String endURL;
    public ArrayList<String> pathway = new ArrayList();

    public WikiHTML() {
        String url = "https://en.wikipedia.org/wiki/Lionel_Messi";
        endURL = "https://en.wikipedia.org/wiki/Argentina_national_football_team";
        findMyWiki(url, 0);

        for (int i= pathway.size()-1; i>= 0; i--){
            System.out.println("pathway " + i + " is " + pathway.get(i));
        }
    }

    public boolean findMyWiki(String originalLink, int depth) {

        ArrayList<String> childLinks = new ArrayList<>();
//        ArrayList<String> pathway = new ArrayList<>();
        int xResult = 0;
        int yResult = 0;
        int endQuotationResult = 0;

        System.out.println("this is the depth: " + depth + " this is the original link: " + originalLink);
        //base case
        if (originalLink.equals(endURL)) {
            System.out.println("found final link " + depth);

            return true;
        } else if (depth == 2) {

            return false;
        } else { //recursive case
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
                        // System.out.println("Link ending is: " + halfString);
                        // System.out.println(halfString);
//                        if (!line.contains(":")) {
//                            childLinks.add(halfString);
//                        }


//                findLink();
                        //  System.out.println(line);
                        if (!originalLink.equals(halfString) && !halfString.contains(":")) {
                            halfString = "https://en.wikipedia.org" + halfString;

                            if (findMyWiki(halfString, depth + 1)) { // if ( call that recursion ) while you're doing that recursion you find the right link,
                                // return true because you find the right link
                                System.out.println("found it!!");
                                pathway.add(halfString);
                                return true;
                            }
//                            if (!line.contains(":")){
//                                xResult = line.indexOf(":") + 1;
//                                endQuotationResult = line.indexOf(":", xResult + 1);
//                                halfString = line.substring(xResult, endQuotationResult);
//                                halfString = "https://en.wikipedia.org" + halfString;
//                                childLinks.add(halfString);
//
//                            }
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

    public void recursionSteps(String originalLink, int depth) {
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


    }


    public static void main(String[] args) {
        WikiHTML myApp = new WikiHTML();


    }
}
