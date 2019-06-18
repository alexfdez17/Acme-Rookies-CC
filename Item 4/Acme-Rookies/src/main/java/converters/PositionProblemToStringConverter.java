
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PositionProblem;

@Component
@Transactional
public class PositionProblemToStringConverter implements Converter<PositionProblem, String> {

	@Override
	public String convert(final PositionProblem positionProblem) {
		String result;

		if (positionProblem == null)
			result = null;
		else
			result = String.valueOf(positionProblem.getId());

		return result;
	}

}
