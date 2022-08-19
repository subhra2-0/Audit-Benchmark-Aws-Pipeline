package com.cts.benchmark.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * This POJO class is used to handle audit type and acceptable no of NO allowed in particular benchmark
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditBenchmark {

	private String auditType;
	private Integer accNoAnswers;
	
}
