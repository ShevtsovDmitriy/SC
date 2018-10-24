package entities.dictionary;

/* Тип накладной */
public enum InvoiceType {
    PURCHASE ("Приход"),
    SALES ("Продажа"),
    CONSUMPTION ("Списание в ремонт");

    private String code;
    InvoiceType(String code){
        this.code = code;
    }
    public String getName(){ return code;}

}
