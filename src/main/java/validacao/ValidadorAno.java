package validacao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorAno implements ConstraintValidator<ValidaAno, Object> {    
    int anoAtual;


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println("Resultado: " + value.toString() + "<= " + anoAtual);
        return (int) value <= anoAtual;
    }

    @Override
    public void initialize(ValidaAno constraintAnnotation) {
        Calendar cal = GregorianCalendar.getInstance();
	anoAtual = cal.get(Calendar.YEAR);
        System.out.println("Ano atual: " + anoAtual);
    }

 
}
