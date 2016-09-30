
package validacao;

/**
 *
 * @author Administrador
 */
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target( {ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorAno.class)
@Documented
public @interface ValidaAno {
    String message() default "O ano da questão tem que ser menor ou igual ao ano atual!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
