 import java.util.*;

public class Main {

    private static String normalizeType(String input) {
        input = input.trim().toUpperCase();

        if (input.equals("W2") || input.equals("W-2") || input.equals("W_2")) return "W-2";
        if (input.equals("1095A") || input.equals("1095-A")) return "1095-A";
        if (input.equals("1095B") || input.equals("1095-B")) return "1095-B";
        if (input.equals("1095C") || input.equals("1095-C")) return "1095-C";
        if (input.equals("1099INT") || input.equals("1099-INT")) return "1099-INT";
        if (input.equals("1099DIV") || input.equals("1099-DIV")) return "1099-DIV";
        if (input.equals("1099NEC") || input.equals("1099-NEC")) return "1099-NEC";
        if (input.equals("1099MISC") || input.equals("1099-MISC")) return "1099-MISC";

        return input;
    }

    private static int getValidatedMenuChoice(Scanner scanner) {
        int choice = -1;
        while (choice < 1 || choice > 7) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 7) {
                    System.out.print("Invalid choice. Enter a number between 1 and 7: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number between 1 and 7: ");
            }
        }
        return choice;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DocumentManager manager = new DocumentManager();

        while (true) {
            System.out.println("\n=== TAX DOCUMENT ORGANIZER ===");
            System.out.println("1. Define Expected Documents for a Tax Year");
            System.out.println("2. Add Document");
            System.out.println("3. List All Documents");
            System.out.println("4. Search Documents");
            System.out.println("5. Delete Document");
            System.out.println("6. Generate Missing Document Report");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidatedMenuChoice(scanner);

            switch (choice) {

                case 1:
                    System.out.print("Enter tax year: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    List<String> selected = new ArrayList<>();
                    String[] forms = manager.getPredefinedForms();

                    System.out.println("\nSelect expected documents:");
                    for (int i = 0; i < forms.length; i++) {
                        System.out.println((i + 1) + ". " + forms[i]);
                    }
                    System.out.print("Enter numbers separated by commas: ");
                    String[] picks = scanner.nextLine().split(",");

                    for (String p : picks) {
                        int index = Integer.parseInt(p.trim()) - 1;
                        if (index >= 0 && index < forms.length) {
                            selected.add(forms[index].split(" — ")[0]);
                        }
                    }

                    System.out.print("Add custom documents? (Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("Y")) {
                        while (true) {
                            System.out.print("Enter custom type (or DONE): ");
                            String custom = scanner.nextLine();
                            if (custom.equalsIgnoreCase("DONE")) break;
                            String code = normalizeType(custom);
                            selected.add(code);
                            manager.addCustomDescription(code);
                        }
                    }

                    manager.defineExpectedDocuments(year, selected);
                    break;

                case 2:
                    System.out.print("Enter document type: ");
                    String type = normalizeType(scanner.nextLine());

                    System.out.print("Enter tax year: ");
                    int addYear = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter source: ");
                    String source = scanner.nextLine();

                    System.out.print("Have you received this document? (Y/N): ");
                    boolean received = scanner.nextLine().equalsIgnoreCase("Y");

                    manager.addDocument(new TaxDocument(type, addYear, source, received));
                    System.out.println("Document added.");
                    break;

                case 3:
                    manager.listDocuments();
                    break;

                case 4:
                    System.out.println("\nSearch Options:");
                    System.out.println("1. Search by Year");
                    System.out.println("2. Search by Type");
                    System.out.println("3. Search by Keyword");
                    System.out.print("Choose: ");
                    int s = Integer.parseInt(scanner.nextLine());

                    if (s == 1) {
                        System.out.print("Enter year: ");
                        manager.searchByYear(Integer.parseInt(scanner.nextLine()));
                    } else if (s == 2) {
                        System.out.print("Enter type: ");
                        manager.searchByType(normalizeType(scanner.nextLine()));
                    } else {
                        System.out.print("Enter keyword: ");
                        manager.searchByKeyword(scanner.nextLine());
                    }
                    break;

                case 5:
                    manager.listDocuments();
                    System.out.print("Enter document number to delete: ");
                    int delIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    manager.deleteDocument(delIndex);
                    break;

                case 6:
                    System.out.print("Enter tax year: ");
                    int missingYear = Integer.parseInt(scanner.nextLine());
                    manager.showMissingReport(missingYear);
                    break;

                case 7:
                    System.out.println("Goodbye!");
                    return;
                }
        }
    }
}