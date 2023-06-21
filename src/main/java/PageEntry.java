public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        if (o == null) {
            return -1;
        }
        if (count == o.getCount()) {
            if (pdfName.equals(o.getPdfName())) {
                return Integer.compare(page, o.getPage());
            } else {
                return pdfName.compareTo(o.getPdfName());
            }
        }
        return Integer.compare(o.getCount(), count);
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }

}
