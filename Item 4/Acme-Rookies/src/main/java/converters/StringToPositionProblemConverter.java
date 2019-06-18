
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PositionProblemRepository;
import domain.PositionProblem;

@Component
@Transactional
public class StringToPositionProblemConverter implements Converter<String, PositionProblem> {

	@Autowired
	private PositionProblemRepository	positionProblemRepository;


	@Override
	public PositionProblem convert(final String text) {
		PositionProblem result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.positionProblemRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}