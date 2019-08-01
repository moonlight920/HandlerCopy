import java.io.*;

public class CreateDimens {
    public static void main(String[] args) {
        StringBuilder sw400 = new StringBuilder();
        //添加xml开始的标签
        String xmlStart = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n";
        sw400.append(xmlStart);

        for (int i = 1; i <= 100; i++) {
            String start = "    <dimen name=\"eink_sp_" + i + "\">";
            String end = "sp</dimen>";
            sw400.append(start).append(changeSP(i)).append(end).append("\n");
        }
        for (int i = 1; i <= 1872; i++) {
            String start = "    <dimen name=\"eink_x_" + i + "\">";
            String end = "px</dimen>";
            sw400.append(start).append(changeY(i)).append(end).append("\n");
        }
        for (int i = 1; i <= 1404; i++) {
            String start = "    <dimen name=\"eink_y_" + i + "\">";
            String end = "px</dimen>";
            sw400.append(start).append(changeY(i)).append(end).append("\n");
        }

        //添加xml的尾标签
        sw400.append("</resources>");
        String sw400file = "./dimens.xml";
        writeFile(sw400file, sw400.toString());
    }

    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            File f = new File(file);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.close();
    }

    private static int changeSP(int i) {
        return (int)((float) i / 2.6);
    }

    private static int changeX(int i) {
        return (int) ((float) i * 0.64);
    }

    private static int changeY(int i) {
        return (int) ((float) i * 0.59);
    }
}
