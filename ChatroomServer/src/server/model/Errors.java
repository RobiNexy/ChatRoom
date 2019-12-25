package server.model;

public enum Errors {
    NAME_BEEN_TAKEN("NAME_BEEN_TAKEN"),UNSIGN_UP_USER("UNSIGN_UP_USER");
    private String errorMassage;
    private Errors(String errorMassage){
        this.errorMassage=errorMassage;
    }

    public String getErrorMassage() {
        return errorMassage;
    }
}
