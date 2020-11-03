public enum TypesOfLoa {

    HORIZONTAL("HORIZONTAL"), VERTICAL("VERTICAL"), LEADING_DIAGONAL("LEADING_DIAGONAL"), COUNTER_DIAGONAL("COUNTER_DIAGONAL");
    private String type;
    TypesOfLoa(String str) {
        this.type = str;
    }
}
