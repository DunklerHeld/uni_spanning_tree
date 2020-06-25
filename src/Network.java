import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Network {

    public static void simulate(HashMap<Character, Node> nodes) {
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

            outputNetworkState(nodes, iteration);

        } while (cont);
    }

    static boolean outputToFile = false;
    static BufferedWriter outputFileWriter;
    public static void outputNetworkState(HashMap<Character, Node> nodes, int currentIteration) {
        //output results
        try {
            output("Network status after " + currentIteration + " iterations");
            for (char c : nodes.keySet()) {
                output(nodes.get(c).getOutputString() + " - " + nodes.get(c).getLengthToRoot());
            }
            output("\n");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }

    public static void output(String out) throws IOException {

        if (outputToFile) {
            outputFileWriter.write(out);
            outputFileWriter.newLine();
        }
        else {
            System.out.println(out);
        }

    }

    public static void setOutputMethod(String outFile) throws IOException {

        if (outFile.equals(""))
            return;

        File f = new File(outFile);
        if (!f.isDirectory() && f.getParentFile().exists()) {
            outputToFile = true;
            outputFileWriter = new BufferedWriter(new FileWriter(f));
        }

    }

    public static HashMap<Character, Node> createNetwork(String[] nodes, String[] edges) {
        HashMap<Character, Node> network = new HashMap<>();
        
        //init network
        for (String s : nodes) {
            char name = s.charAt(0);
            int id = Integer.parseInt(s.substring(2, s.lastIndexOf(';')));

            Node n = new Node(name, id);
            network.put(name, n);
        }

        for (String s : edges) {
            int amountNodes = s.substring(0, s.lastIndexOf(':') +1).length() / 2;
            ArrayList<Character> nodeNames = new ArrayList<>();
            for (int i = 0; i < amountNodes; i++) {
                nodeNames.add(s.charAt(i*2));
            }

            String weightString = s.substring(s.lastIndexOf(':') +1, s.lastIndexOf(';'));
            int weight = Integer.parseInt(weightString);

            Edge edge = new Edge(weight);

            for (char c : nodeNames) {
                Node n = network.get(c);
                edge.addNode(n);
                n.addEdge(edge);
            }

        }

        return network;
    }

    public static File getInputFile(String arg) throws FileNotFoundException {

        if (arg.equals("")) {
            //open file chooser
            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                System.exit(0);
            }


            return chooser.getSelectedFile();
        }
        else {
            //use argument as file
            File f = new File(arg);
            if (!f.exists()) {
                throw new FileNotFoundException("A file you entered doesn't exist");
            }
            return f;
        }
    }

    public static String[][] readInputFile(File f) throws IOException {

        ArrayList<String> readNodes = new ArrayList<>();
        ArrayList<String> readEdges = new ArrayList<>();

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

        String[][] ret = new String[2][];
        ret[0] = readNodes.toArray(new String[0]);
        ret[1] = readEdges.toArray(new String[0]);

        return ret;

    }



    public static void main(String[] args) {

        try {

            setOutputMethod(args.length>1 ? args[1] : "");

            File f = getInputFile(args.length>0 ? args[0] : "");
            String[][] res = readInputFile(f);
            String[] nodes = res[0];
            String[] edges = res[1];

            HashMap<Character, Node> network = createNetwork(nodes, edges);

            simulate(network);

            if (outputToFile)
                outputFileWriter.close();

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }


    }

}
