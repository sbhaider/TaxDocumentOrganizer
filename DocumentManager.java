 import java.io.*;
import java.util.*;

public class DocumentManager {

    private List<TaxDocument> documents = new ArrayList<>();
    private Map<Integer, List<String>> expectedDocuments = new HashMap<>();
    private Map<String, String> formDescriptions = new HashMap<>();

    private final String[] predefinedForms = {
        "W-2 — Wage and Tax Statement",
        "1099-NEC — Non-Employee Compensation",
        "1099-MISC — Miscellaneous Income",
        "1099-K — Third-Party Payments",
        "1099-INT — Interest Income",
        "1099-DIV — Dividend Income",
        "1099-B — Brokerage Sales",
        "1099-R — Retirement Distributions",
        "1098 — Mortgage Interest",
        "1098-E — Student Loan Interest",
        "1098-T — Tuition Statement",
        "Property Tax Statement — Local Property Taxes",
        "1095-A — Marketplace Health Insurance",
        "1095-B — Health Coverage",
        "1095-C — Employer Health Coverage",
        "1099-SA — HSA Distributions",
        "8889 — HSA Contributions/Distributions",
        "5498-SA — HSA Contributions (Bank)",
        "5498 — IRA Contributions"
    };

    public DocumentManager() {
        loadFromFile();

        for (String form : predefinedForms) {
            String[] parts = form.split(" — ");
            formDescriptions.put(parts[0].trim(), parts[1].trim());
        }
    }

    public void addDocument(TaxDocument doc) {
        documents.add(doc);
        saveToFile();
    }

    public void listDocuments() {
        if (documents.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }
        for (int i = 0; i < documents.size(); i++) {
            System.out.println((i + 1) + ". " + documents.get(i));
        }
    }

    public void deleteDocument(int index) {
        if (index >= 0 && index < documents.size()) {
            documents.remove(index);
            saveToFile();
            System.out.println("Document deleted.");
        } else {
            System.out.println("Invalid number.");
        }
    }

    public void searchByYear(int year) {
        documents.stream().filter(d -> d.getTaxYear() == year).forEach(System.out::println);
    }

    public void searchByType(String type) {
        documents.stream().filter(d -> d.getDocumentType().equalsIgnoreCase(type)).forEach(System.out::println);
    }

    public void searchByKeyword(String keyword) {
        documents.stream().filter(d -> d.toString().toLowerCase().contains(keyword.toLowerCase())).forEach(System.out::println);
    }

    public void defineExpectedDocuments(int year, List<String> forms) {
        expectedDocuments.put(year, forms);
        saveToFile();
        System.out.println("Expected documents saved for " + year);
    }

    public void addCustomDescription(String code) {
        formDescriptions.put(code, "Custom Document");
    }

    public void showMissingReport(int year) {
        if (!expectedDocuments.containsKey(year)) {
            System.out.println("No expected documents defined for this year.");
            return;
        }

        List<String> expected = expectedDocuments.get(year);
        List<String> missing = new ArrayList<>(expected);

        for (TaxDocument doc : documents) {
            if (doc.getTaxYear() == year && doc.isReceived()) {
                missing.remove(doc.getDocumentType());
            }
        }

        System.out.println("\nMissing Documents for " + year + ":");
        if (missing.isEmpty()) {
            System.out.println("All expected documents have been received.");
        } else {
            for (String code : missing) {
                String desc = formDescriptions.getOrDefault(code, "Custom Document");
                System.out.println(code + " — " + desc);
            }
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter("taxdata.txt")) {

            writer.println("[DOCUMENTS]");
            for (TaxDocument doc : documents) {
                writer.println(doc.toFileString());
            }

            writer.println("[EXPECTED]");
            for (Integer year : expectedDocuments.keySet()) {
                writer.println(year + ":" + String.join(";", expectedDocuments.get(year)));
            }

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("taxdata.txt"))) {

            documents.clear();
            expectedDocuments.clear();

            String line;
            boolean readingDocs = false, readingExpected = false;

            while ((line = reader.readLine()) != null) {

                if (line.equals("[DOCUMENTS]")) { readingDocs = true; readingExpected = false; continue; }
                if (line.equals("[EXPECTED]")) { readingDocs = false; readingExpected = true; continue; }

                if (readingDocs && !line.isBlank()) {
                    documents.add(TaxDocument.fromFileString(line));
                }

                if (readingExpected && !line.isBlank()) {
                    String[] p = line.split(":");
                    int year = Integer.parseInt(p[0]);
                    expectedDocuments.put(year, new ArrayList<>(Arrays.asList(p[1].split(";"))));
                }
            }

        } catch (Exception e) {
            // Ignore if file doesn't exist yet
        }
    }

    public String[] getPredefinedForms() { return predefinedForms; }
}
