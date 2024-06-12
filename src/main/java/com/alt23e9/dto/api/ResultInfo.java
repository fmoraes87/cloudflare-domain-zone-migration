package com.alt23e9.dto.api;

import lombok.Data;

@Data
public class ResultInfo{
	private int perPage;
	private int totalCount;
	private int count;
	private int page;
}