 public class TaxDocument {
    private String documentType;
    private int taxYear;
    private String source;
    private boolean received;

    public TaxDocument(String documentType, int taxYear, String source, boolean received) {
        this.documentType = documentType.toUpperCase();
        this.taxYear = taxYear;
        this.source = source;
        this.received = received;
    }

    public String getDocumentType() { return documentType; }
    public int getTaxYear() { return taxYear; }
    public String getSource() { return source; }
    public boolean isReceived() { return received; }

    @Override
    public String toString() {
        return documentType + " | Year: " + taxYear + " | Source: " + source + " | Received: " + received;
    }

    public String toFileString() {
        return documentType + "," + taxYear + "," + source + "," + received;
    }

    public static TaxDocument fromFileString(String line) {
        String[] p = line.split(",");
        return new TaxDocument(p[0], Integer.parseInt(p[1]), p[2], Boolean.parseBoolean(p[3]));
    }
}
