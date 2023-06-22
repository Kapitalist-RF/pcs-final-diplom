import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> wordsPageEntryMap = new HashMap<>();
    private List<String> stopList = new ArrayList<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("stop-ru.txt")))) {
            while (reader.ready()) {
                stopList.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File pdf : pdfsDir.listFiles()) {
            PdfDocument doc = new PdfDocument(new PdfReader(pdf));
            int pageCount = doc.getNumberOfPages();
            for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
                PdfPage page = doc.getPage(pageNumber);
                String text = PdfTextExtractor.getTextFromPage(page);
                String[] words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>();
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (String uniqueWord : freqs.keySet()) {
                    Set<String> uniqueWordCheck = wordsPageEntryMap.keySet();
                    if (uniqueWordCheck.contains(uniqueWord)) {
                        wordsPageEntryMap.get(uniqueWord).add(new PageEntry(pdf.getName(), pageNumber, freqs.get(uniqueWord)));
                    } else {
                        List<PageEntry> resultPageEntryList = new ArrayList<>();
                        resultPageEntryList.add(new PageEntry(pdf.getName(), pageNumber, freqs.get(uniqueWord)));
                        wordsPageEntryMap.put(uniqueWord, resultPageEntryList);
                    }
                }
            }

        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> list = ((wordsPageEntryMap.containsKey(word.toLowerCase()) && !stopList.contains(word.toLowerCase()))
                ? wordsPageEntryMap.get(word.toLowerCase()) : Collections.emptyList());
        Collections.sort(list);
        return list;
    }
}
