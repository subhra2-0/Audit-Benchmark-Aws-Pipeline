package com.cts.benchmark;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cts.benchmark.pojo.CustomErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.benchmark.feignclient.AuthClient;
import com.cts.benchmark.pojo.AuditBenchmark;
import com.cts.benchmark.service.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class is handling all the end points for returning benchmark of acceptable no of NO's for a particular
 * audit type to  Audit Severity microservice. 

 * @see AuthClient is used to verify the token.
 * @see tokenService is to check token with auth microservice
 * @see env is to acess values from properties file

 *
 */
@RestController
@Slf4j
public class BenchmarkController {
	@Autowired
	AuthClient authClient;
		
	@Autowired
	TokenService tokenService;
	
	/**
	 * 
	 * @param token - used to verfiy the token with auth service
	 * @return response entity which is either List of questions or error caused in application
	 */
	@GetMapping("/AuditBenchmark")
	public ResponseEntity<?> getBenchmarkMap(@RequestHeader("Authorization") String token){
		
		List<AuditBenchmark> auditBenchmarkList = new ArrayList<AuditBenchmark>();
		ResponseEntity<?> responseEntity=null;
		auditBenchmarkList.add(new AuditBenchmark("Internal", 3));
		auditBenchmarkList.add(new AuditBenchmark("SOX", 1));
		try {
			if (tokenService.checkTokenValidity(token)){
				responseEntity = new ResponseEntity<List<AuditBenchmark>>(auditBenchmarkList, HttpStatus.OK);
				return responseEntity;
			}else{
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(new CustomErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN, "wrong cred", "wrong cred"), HttpStatus.FORBIDDEN);
		} 

	}
}