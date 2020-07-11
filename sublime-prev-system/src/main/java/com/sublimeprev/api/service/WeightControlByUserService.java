package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.dto.req.WeightControlPageReq;
import com.sublimeprev.api.model.User_;
import com.sublimeprev.api.model.WeightControl;
import com.sublimeprev.api.model.WeightControl_;
import com.sublimeprev.api.repository.WeightControlRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WeightControlByUserService {

	private final WeightControlRepository repository;

	public Page<WeightControl> findAll(Long userId, WeightControlPageReq query) {
		if (query.getSort() == null || query.getSort().length == 0)
			query.setSort(new String[] { "date:desc" });
		Specification<WeightControl> user = (root, qr, builder) -> builder.equal(root.get(WeightControl_.user).get(User_.id), userId);
		Specification<WeightControl> period = null;
		if (query.getYear() != null) {
			if (query.getMonth() == null) {
				period = (root, qr, builder) -> builder.between(root.get(WeightControl_.date),
						LocalDate.of(query.getYear(), 1, 1), LocalDate.of(query.getYear(), 12, 31));
			} else {
				YearMonth yearMonth = YearMonth.of(query.getYear(), query.getMonth());
				period = (root, qr, builder) -> builder.between(root.get(WeightControl_.date), yearMonth.atDay(1),
						yearMonth.atEndOfMonth());
			}
		}
		Specification<WeightControl> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<WeightControl> filters = SearchUtils.specByFilter(query.getFilter(), "createdAt:localDatetime", "value");
		Page<WeightControl> teste = this.repository.findAll(user.and(deleted).and(filters).and(period), query.toPageRequest());
		return teste;
	}
}
