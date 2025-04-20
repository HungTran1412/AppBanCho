package dev.mhung.ltmobile.petapplication.response;

import java.util.List;

public class ProductApiRespone {
    private List<ProductResponse> product;
    private int skip, limit, total;

    public List<ProductResponse> getProduct() {
        return product;
    }

    public void setProduct(List<ProductResponse> product) {
        this.product = product;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
