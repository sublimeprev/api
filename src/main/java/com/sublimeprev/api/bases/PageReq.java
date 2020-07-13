package com.sublimeprev.api.bases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageReq {

	private int page;
	private int size;
	private String[] sort;
	private String filter;
	private boolean deleted;

	public PageRequest toPageRequest() {
		if (size == 0)
			size = Integer.MAX_VALUE;
		return PageRequest.of(page, size, sortBy());
	}

	private Sort sortBy() {
		Sort sortObj = Sort.unsorted();
		if (sort != null && sort.length > 0) {
			List<Order> orders = new ArrayList<>();
			for (int i = 0; i < sort.length; i++) {
				if (sort[i].contains(":"))
					orders.add(new Order(Direction.fromString(sort[i].split(":")[1]), sort[i].split(":")[0]));
				else
					orders.add(new Order(Direction.ASC, sort[i]));
			}
			sortObj = Sort.by(orders);
		}
		return sortObj;
	}

	public void addSortItem(String col, String order) {
		List<String> arrlist = this.sort == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(this.sort));
		arrlist.add(String.format("%s:%s", col, order));
		this.sort = arrlist.toArray(new String[arrlist.size()]);
	}
}
