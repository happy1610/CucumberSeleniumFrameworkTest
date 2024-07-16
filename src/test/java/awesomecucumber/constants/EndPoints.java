package awesomecucumber.constants;

public enum EndPoints {

    BASEPAGE("/"),
    PRODUCTS("/products"),
    ACCOUNT("/account");

    public final String url;

    EndPoints(String url) {
        this.url = url;
    }
}
