
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.NuevaEntidadXXX;

@Component
@Transactional
public class NuevaEntidadXXXToStringConverter implements Converter<NuevaEntidadXXX, String> {

	@Override
	public String convert(final NuevaEntidadXXX nuevaEntidadXXX) {
		String result;

		if (nuevaEntidadXXX == null)
			result = null;
		else
			result = String.valueOf(nuevaEntidadXXX.getId());

		return result;
	}

}
