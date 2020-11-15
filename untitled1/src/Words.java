public class Words {
    String es,pl;

    public Words(String es, String pl) {
        this.es = es;
        this.pl = pl;
    }

    public Words() {
    }

    public String getEs() {
        return es;
    }

    public String getPl() {
        return pl;
    }

    @Override
    public String toString() {
        return this.es +"  "+ this.pl;
    }
}
