package com.fampay.test.ui.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Column {
	private String data;
	private String name;
	private Boolean searchable;
	private Boolean orderable;
	private Search search;
}