package lolimods.adds.lolicore.util.nullity;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.*;

@Documented
@TypeQualifierNickname
@TypeQualifierDefault({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Nonnull
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NothingIsNullByDefault {
}
