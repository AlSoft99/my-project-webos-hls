package com;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("springService")
@Transactional
public class ArithmeticService {

	public Integer add(Integer operand1, Integer operand2) {
		// A simple arithmetic addition
		return operand1 + operand2;
	}

}