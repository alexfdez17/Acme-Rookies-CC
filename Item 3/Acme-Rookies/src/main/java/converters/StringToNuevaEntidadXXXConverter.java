
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.NuevaEntidadXXXRepository;
import domain.NuevaEntidadXXX;

@Component
@Transactional
public class StringToNuevaEntidadXXXConverter implements Converter<String, NuevaEntidadXXX> {

	@Autowired
	private NuevaEntidadXXXRepository	nuevaEntidadXXXRepository;


	@Override
	public NuevaEntidadXXX convert(final String text) {
		NuevaEntidadXXX result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.nuevaEntidadXXXRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
