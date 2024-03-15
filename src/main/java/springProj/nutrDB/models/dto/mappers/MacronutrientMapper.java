package springProj.nutrDB.models.dto.mappers;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// Is this the right annotation? I know that it works.
@Component
public class MacronutrientMapper {
    public BigDecimal asBigDecimal(short source) {
        return BigDecimal.valueOf(source, 1);
    }

    // we save it as short in database
    public short asShort(BigDecimal source) {
        return source.movePointRight(1).shortValue();
    }

}
