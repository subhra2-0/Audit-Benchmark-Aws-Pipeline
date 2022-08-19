package com.cts.benchmark.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cts.benchmark.exception.FeignProxyException;
import com.cts.benchmark.feignclient.AuthClient;
import com.cts.benchmark.pojo.AuthResponse;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
/**
   This class is implementing {@link TokenService}.
 * The method of this class is used in other classes to validate token received.
 * 
 * @see AuthClient
 *
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

	/**
	 * Interface interacting with Auth microservice
	 */
	@Autowired
	private AuthClient authClient;
	
	/**
	 * @param token
	 * @return boolean this method will check the token validity by communicating
	 *         with auth client.
	 */
	public Boolean checkTokenValidity(String token)  {
		log.info("checklist-token validity starts:");
    	log.debug("token", token);

		try {
			log.debug("checklist-token validity success");
			AuthResponse authResponse = authClient.getValidity(token).getBody();
			if(authResponse==null) throw new FeignProxyException();
			
			log.info("checklist-token validity ends");
			
			return authResponse.isValid();
		} catch (FeignProxyException fe) {
			
			log.debug("checklist-token validity fail");
			log.error("feign exception",fe);
			log.info("checklist-token validity ends");
			
			return false;
		}
		
	}

}
