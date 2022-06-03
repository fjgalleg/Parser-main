import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//Created by Xavier Thomas and Francisco Gallegos

public class Principal {
    public static void main(String[] args) throws Exception {
        String p = "C:\\Users\\user\\Desktop\\Parser-main\\Test";
        final Path path = Paths.get(p);
        final Path txt = path.resolve("Test");
        final Path csv = path.resolve("Test.csv");
        String space = "\n";

        try (
                final Stream<String> lines = Files.lines(txt);
                final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, StandardOpenOption.CREATE_NEW))) {
            lines.map((line) -> line.split("\\s")).map((line) -> Stream.of(line).collect(Collectors.joining(","))).forEach(pw::println);
        }//end try
    }//end main


    public static File findUsingIOApi(String sdir) {
        File dir = new File(sdir);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile))
                    .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

            if (opFile.isPresent()) {
                return opFile.get();
            }//end 2nd if statement
        }//end if statement

        return null;
    }//end findUsingIOApi

    public static Path findUsingNIOApi(String sdir) throws IOException {

        Path dir = Paths.get(sdir);
        if (Files.isDirectory(dir)) {
            Optional<Path> opPath = Files.list(dir)
                    .filter(p -> !Files.isDirectory(p))
                    .sorted((p1, p2) -> Long.valueOf(p2.toFile().lastModified())
                            .compareTo(p1.toFile().lastModified()))
                    .findFirst();

            if (opPath.isPresent()) {
                return opPath.get();
            }//end second if statement
        }//end if statement
        else {
            System.out.println("findUsingNIOApi failed");
        }
        System.out.println("findUsingNIOApi complete");
        return null;
    }//end finUsingNIOApi


}//end Principal