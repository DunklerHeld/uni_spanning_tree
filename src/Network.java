import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Network {

    public static void main(String[] args) {

        HashMap<Character, Node> nodes = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<String> readNodes = new ArrayList<>();
        ArrayList<String> readEdges = new ArrayList<>();

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }


        File f = chooser.getSelectedFile();
        String fileName = f.getName();


        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.replace(" ", "");


                char start = line.charAt(0);

                if (start == '/')   continue;

                if (start == '\n')  continue;


                if (line.charAt(1) == '=') {
                    readNodes.add(line);
                    continue;
                }

                if (line.charAt(1) == '-') {
                    readEdges.add(line);
                    continue;
                }

            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //init network
        for (String s : readNodes) {
            char name = s.charAt(0);
            int id = Integer.parseInt(s.substring(2, s.lastIndexOf(';')));

            Node n = new Node(name, id);
            nodes.put(name, n);
        }

        for (String s : readEdges) {
            int amountNodes = s.substring(0, s.lastIndexOf(':') +1).length() / 2;
            ArrayList<Character> nodeNames = new ArrayList<>();
            for (int i = 0; i < amountNodes; i++) {
                nodeNames.add(s.charAt(i*2));
            }

            String weightString = s.substring(s.lastIndexOf(':') +1, s.lastIndexOf(';'));
            int weight = Integer.parseInt(weightString);

            Edge edge = new Edge(weight);

            for (char c : nodeNames) {
                Node n = nodes.get(c);
                edge.addNode(n);
                n.addEdge(edge);
            }

        }

        //start simulation
        boolean cont;
        int iteration = 0;

        do {
            cont = false;
            iteration++;

            for (char c : nodes.keySet()) {
                nodes.get(c).sendMessages();
            }

            for (char c : nodes.keySet()) {
                cont |= nodes.get(c).computeMessages();
            }

            //output results
            System.out.println("Network status after " + iteration + " iterations");
            for (char c : nodes.keySet()) {
                System.out.println(nodes.get(c).getOutputString() + " - " + nodes.get(c).getLengthToRoot());
            }
            System.out.println();
            System.out.println();

        } while (cont);




    }

}
