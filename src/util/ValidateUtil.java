package util;

public class ValidateUtil {

    // Tus validaciones originales
    public static final String DNI = "\\d{8}";
    public static final String RUC = "\\d{11}";
    public static final String PLACA_AUTO = "[A-Z]{3}[0-9]{3}";
    public static final String TEXTO_30 = "[A-Za-z áéióúÁÉÍÓÚñÑüÜ]{1,30}";
    public static final String TEXTO_40 = "[A-Za-z áéióúÁÉÍÓÚñÑüÜ]{1,40}";
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String DATE_YYYY_MM_DD ="((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
    
    // Validaciones añadidas para los campos de Curso
    public static final String TEXTO_NUM_100 = "[A-Za-z0-9 áéióúÁÉÍÓÚñÑüÜ.,:-]{1,100}"; 
    public static final String NUM_DECIMAL = "^\\d+(\\.\\d{1,2})?$"; 
    public static final String NUM_ENTERO = "^\\d{1,4}$"; 
}